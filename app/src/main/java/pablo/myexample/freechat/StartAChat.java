package pablo.myexample.freechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;

public class StartAChat extends AppCompatActivity implements CreateChatListAdapter.OnUserListener {

    private CreateChatListAdapter createChatListAdapter;
    private EditText input;
    private TextView displayChosen;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, databaseReferenceTwo;
    private ArrayList<User> arrayList;
    private ArrayList<String> names;
    private ArrayList<String> ids;
    private ArrayList<String> urls;
    Intent intent;

    boolean allowed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_achat);
        setTitle("Start A Chat");

        intent = getIntent();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserRooms");
        databaseReferenceTwo = firebaseDatabase.getReference("Chats");

        input = findViewById(R.id.inputUserToSearch);
        displayChosen = findViewById(R.id.displayChosen);

        arrayList = new ArrayList<>();
        names = new ArrayList<>();
        ids = new ArrayList<>();
        urls = new ArrayList<>();

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

    @Override
    public void onUserListener(int position){

        User chosenUser = arrayList.get(position);

        for(int i = 0; i < ids.size(); i++) {
            if(ids.get(i).matches(chosenUser.getUserId())){
                allowed = false;
                break;
            }
        }

        if(allowed) {

            ids.add(chosenUser.getUserId());
            names.add(chosenUser.getNickName());
            urls.add(chosenUser.getProfileUrl());

            String forChosenName = chosenUser.getUserName() + ", ";
            String forChosen = displayChosen.getText().toString();
            String completeText = forChosen + forChosenName;
            displayChosen.setText(completeText);

            allowed = true;
        }
    }

    public void cancelMethod(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void createChatRoom(View view){

        //create other new account
        //get my info
        String myId = intent.getStringExtra("id");
        String myUrl = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        //add info into zero position of every arraylist
        //add date and time to message object

       /*
        String RoomId = String.valueOf(System.currentTimeMillis());

        if(urls.size() > 2){
            //will use background blue for group
            urls = new ArrayList<>();
            RoomObject roomObject = new RoomObject(names, ids, urls);
            databaseReference.child(RoomId).setValue(roomObject);
        }else{
            RoomObject roomObject = new RoomObject(names, ids, urls);
            databaseReferenceTwo.child(RoomId).setValue(roomObject);
        }

        //set value for roomObject : not in user, but for group : this keeps growing
        MessageObject messageObject = new MessageObject("Alert!","This is the beginning of the chat.","July 31","2:11pm");
        databaseReferenceTwo.child(RoomId).child("Messages").push().setValue(messageObject);

        //set value for specific users in chat : this is overriden everytime : gets last message object
        for(int i = 0; i < ids.size(); i++) {
            databaseReference.child(ids.get(i)).child(RoomId).setValue(messageObject);
        }
        */

    }

}
