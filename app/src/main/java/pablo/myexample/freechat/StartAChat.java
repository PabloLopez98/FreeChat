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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private Intent intent;
    private String myId;
    private String myUrl;
    private String myname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_achat);
        setTitle("Start A Chat");

        intent = getIntent();
        myId = intent.getStringExtra("id");
        myUrl = intent.getStringExtra("url");
        myname = intent.getStringExtra("name");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserRooms");
        databaseReferenceTwo = firebaseDatabase.getReference("Chats");

        input = findViewById(R.id.inputUserToSearch);
        displayChosen = findViewById(R.id.displayChosen);

        arrayList = new ArrayList<>();
        names = new ArrayList<>();
        names.add(myname);
        ids = new ArrayList<>();
        ids.add(myId);
        urls = new ArrayList<>();
        urls.add(myUrl);

        RecyclerView recyclerView = findViewById(R.id.chosenforchatrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createChatListAdapter = new CreateChatListAdapter(this, arrayList, this);
        recyclerView.setAdapter(createChatListAdapter);
    }

    public void SearchUser(View view) {
        if (input.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input a username", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        User user = dataSnapshot1.getValue(User.class);
                        if (user.getUserName().contains(input.getText().toString())) {
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

    boolean allowed;

    @Override
    public void onUserListener(int position) {

        allowed = true;

        User chosenUser = arrayList.get(position);

        for (int i = 0; i < ids.size(); i++) {
            String mId = ids.get(i);
            if (mId.matches(chosenUser.getUserId())) {//id inside id array == new id chosen
                Log.i("one", "yes");
                allowed = false;
            } else if (chosenUser.getUserId().matches(myId)) {//id inside id array == my id
                Log.i("two", "yes");
                allowed = false;
            } else {
                allowed = true;
            }
        }

        Log.i("star", String.valueOf(allowed));

        if (allowed) {
            Log.i("three", "yes");
            ids.add(chosenUser.getUserId());
            names.add(chosenUser.getUserName());
            urls.add(chosenUser.getProfileUrl());
            String forChosenName = chosenUser.getUserName() + ", ";
            String forChosen = displayChosen.getText().toString();
            String completeText = forChosen + forChosenName;
            displayChosen.setText(completeText);
        } else {
            Log.i("four", "yes");
            allowed = true;
        }

    }

    public void cancelMethod(View view) {
        finish();
    }

    public void createChatRoom(View view) {

        if(ids.size() > 1) {

            String RoomId = String.valueOf(System.currentTimeMillis());

            String Date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
            String Time = mdformat.format(calendar.getTime());

            RoomObject roomObject = new RoomObject(names, ids, RoomId);
            databaseReferenceTwo.child(RoomId).setValue(roomObject);

            MessageObject messageObject = new MessageObject("Alert!", "This is the beginning of the chat.", Date, Time);
            databaseReferenceTwo.child(RoomId).child("Messages").push().setValue(messageObject);

            //for every user
            for (int i = 0; i < ids.size(); i++) {
                if (ids.size() == 2) {
                    if (ids.get(i).matches(myId)) {
                        databaseReference.child(ids.get(i)).child(RoomId).child("previewUrl").setValue(urls.get(1));
                        databaseReference.child(ids.get(i)).child(RoomId).child("previewName").setValue(names.get(1));
                    } else {
                        databaseReference.child(ids.get(i)).child(RoomId).child("previewUrl").setValue(urls.get(0));
                        databaseReference.child(ids.get(i)).child(RoomId).child("previewName").setValue(names.get(0));
                    }
                } else {
                    ArrayList<String> singleUrl = new ArrayList<>();
                    singleUrl.add("https://firebasestorage.googleapis.com/v0/b/freechat-36ca9.appspot.com/o/Uploads%2Fimages%2FIxEuSOv65cciAnMcvPDcQO0NOgs2%2F1566861959431.jpg?alt=media&token=d10b2d42-cccb-441e-a93b-4c4bbfd54ede");
                    databaseReference.child(ids.get(i)).child(RoomId).child("previewUrl").setValue(singleUrl.get(0));
                    databaseReference.child(ids.get(i)).child(RoomId).child("previewName").setValue(String.valueOf(names));
                }
                //add last message under each user
                databaseReference.child(ids.get(i)).child(RoomId).child("LastMessage").setValue(messageObject);
            }

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Input a recipient.", Toast.LENGTH_SHORT).show();
        }

    }

}
