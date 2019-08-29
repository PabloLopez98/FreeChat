package pablo.myexample.freechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Conversation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageObject> arrayList;
    private Intent intent;
    private DatabaseReference databaseReference;
    private ArrayList<String> names, ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to get messages
        arrayList = new ArrayList<>();
        intent = getIntent();
        String roomId = intent.getStringExtra("roomId");
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(roomId).child("Messages");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MessageObject messageObject = dataSnapshot1.getValue(MessageObject.class);
                    arrayList.add(messageObject);
                }
                recyclerView = findViewById(R.id.recyclerview_conversation);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                messageAdapter = new MessageAdapter(getApplicationContext(), arrayList);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //to get names and ids
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(roomId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    int i = 0;
                    if (i == 1) {
                        ids = dataSnapshot1.getValue(ArrayList.class);
                    } else if (i == 2) {
                        names = dataSnapshot1.getValue(ArrayList.class);
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        /*
        arrayList = new ArrayList<>();
        MessageObject messageObject = new MessageObject("Pablo","This is my message","July 31","2:11pm");
        MessageObject messageObject2 = new MessageObject("Pablo","This is my message","August 1","2:13pm");
        arrayList.add(messageObject);
        arrayList.add(messageObject2);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_conversation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, arrayList);
        recyclerView.setAdapter(messageAdapter);
        */
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        for (int i = 0; i < names.size(); i++) {
            menu.add(names.get(i));
        }
        inflater.inflate(R.menu.menuinconversation, menu);
        return true;
    }
}