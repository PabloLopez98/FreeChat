package pablo.myexample.freechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartAChat extends AppCompatActivity implements CreateChatListAdapter.OnUserListener {

    private CreateChatListAdapter createChatListAdapter;
    private EditText input;
    private TextView displayChosen;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<User> arrayList;
    private ArrayList<User> chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_achat);
        setTitle("Start A Chat");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserRooms");

        input = findViewById(R.id.inputUserToSearch);
        displayChosen = findViewById(R.id.displayChosen);

        arrayList = new ArrayList<>();
        chosen = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.chosenforchatrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createChatListAdapter = new CreateChatListAdapter(this, arrayList, this);
        recyclerView.setAdapter(createChatListAdapter);
    }

    public void SearchUser(View view) {
        if (input.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input a username", Toast.LENGTH_SHORT).show();
        }
        else {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUserName().contains(input.getText().toString())){
                        User userChosenForChat = new User(user.getUserId(), user.getProfileUrl(), user.getUserName(), user.getNickName());
                        arrayList.add(userChosenForChat);
                    }
                }
                createChatListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        }
    }

    boolean allowed = true;

    @Override
    public void onUserListener(int position){

        User chosenUser = arrayList.get(position);

        for(int i = 0; i < chosen.size(); i++) {
            if(chosen.get(i).getUserId().matches(chosenUser.getUserId())){
                allowed = false;
                break;
            }
        }

        if(allowed) {
            chosen.add(chosenUser);
            String forChosenName = chosenUser.getUserName() + ", ";
            String forChosen = displayChosen.getText().toString();
            String completeText = forChosen + forChosenName;
            displayChosen.setText(completeText);
            allowed = true;
        }
    }

}
