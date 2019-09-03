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
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Conversation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageObject> arrayList;
    private Intent intent;
    private DatabaseReference databaseReference;
    private ArrayList<String> names;
    private ArrayList<String> ids;
    private String roomId;
    private EditText input;
    private String myName;

    //display all messages
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        input = findViewById(R.id.inputAMessage);
        names = new ArrayList<>();
        ids = new ArrayList<>();
        arrayList = new ArrayList<>();
        intent = getIntent();
        roomId = intent.getStringExtra("roomId");
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
        getNamesAndIds();
    }

    //get all users names and ids
    public void getNamesAndIds() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(roomId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RoomObject roomObject = dataSnapshot.getValue(RoomObject.class);
                names = roomObject.getListOfNames();
                ids = roomObject.getListOfIds();
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //create options menu filled with users' names
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        for (int i = 0; i < names.size(); i++) {
            menu.add(names.get(i));
        }
        inflater.inflate(R.menu.menuinconversation, menu);
        return true;
    }

    //send and listen for messages to update recyclerview
    public void sendMessages(View view) {
        String theMessage = input.getText().toString();
        if (theMessage.matches("")) {
            Toast.makeText(getApplicationContext(), "Input a message", Toast.LENGTH_SHORT).show();
        } else {
            String Date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
            String Time = mdformat.format(calendar.getTime());
            String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i).matches(myId)) {
                    myName = names.get(i);
                }
            }
            MessageObject messageObject = new MessageObject(myName, theMessage, Date, Time, myId);

            //add message to chat room
            databaseReference = FirebaseDatabase.getInstance().getReference("Chats").child(roomId).child("Messages");
            databaseReference.push().setValue(messageObject);

            //change last message for every user
            FirebaseDatabase firebaseDatabaseForUserRooms = FirebaseDatabase.getInstance();
            for (int i = 0; i < ids.size(); i++) {
                DatabaseReference databaseReferenceTwo = firebaseDatabaseForUserRooms.getReference("UserRooms").child(ids.get(i)).child(roomId).child("LastMessage");
                databaseReferenceTwo.setValue(messageObject);
            }
        }
    }

    //use databaseReference to listen and for new messages in database and update recyclerview

}