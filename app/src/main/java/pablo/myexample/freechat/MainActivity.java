package pablo.myexample.freechat;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ChatPreviewAdapter.onChatListener {

    private ChatPreviewAdapter chatPreviewAdapter;
    private ArrayList<ChatPreviewCardObject> arrayList;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private NavigationView navigationView;
    private ImageView profileImage;
    private TextView profileName, profileNickName;
    private String theUrl;
    private DatabaseReference databaseReference;
    private ChatPreviewCardObject chatPreviewCardObject;
    private ArrayList<String> roomIdArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomIdArray = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserRooms").child(userId);
        navigationView = findViewById(R.id.nav_view);
        profileImage = navigationView.getHeaderView(0).findViewById(R.id.imageViewHeader);
        profileName = navigationView.getHeaderView(0).findViewById(R.id.nameViewHeader);
        profileNickName = navigationView.getHeaderView(0).findViewById(R.id.nickNameViewHeader);
        //setup profile info
        setUpProfileDrawerHeader();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //populate the recyclerview with the current users chats
        arrayList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    if (dataSnapshot1.hasChild("LastMessage")) {

                            MessageObject messageObject = dataSnapshot1.child("LastMessage").getValue(MessageObject.class);

                            String message = messageObject.getMessageText();
                            String date = messageObject.getMessageDate();
                            String time = messageObject.getMessageTime();

                            String url = dataSnapshot1.child("previewUrl").getValue(String.class);
                            String name = dataSnapshot1.child("previewName").getValue(String.class);

                            chatPreviewCardObject = new ChatPreviewCardObject(name, message, date, time, " ", url);

                            //get room id to enter chat room
                            String roomId = dataSnapshot1.child("previewRoomId").getValue(String.class);
                            roomIdArray.add(roomId);

                            arrayList.add(chatPreviewCardObject);

                    }
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                chatPreviewAdapter = new ChatPreviewAdapter(getApplicationContext(), arrayList, MainActivity.this);
                recyclerView.setAdapter(chatPreviewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setUpProfileDrawerHeader() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserRooms").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                theUrl = user.getProfileUrl();
                Picasso.with(getApplicationContext()).load(theUrl).fit().into(profileImage);
                profileNickName.setText(user.getNickName());
                profileName.setText(user.getUserName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else if (id == R.id.startAChat) {
            Intent intent = new Intent(this, StartAChat.class);
            intent.putExtra("name", profileName.getText().toString());
            intent.putExtra("id", firebaseAuth.getCurrentUser().getUid());
            intent.putExtra("url", theUrl);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 /*   public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void toMethod(MenuItem item) {
        Intent intent = new Intent(this, SearchConversations.class);
        startActivity(intent);
    }*/

    @Override
    public void onChatClick(int position) {
        Intent intent = new Intent(this, Conversation.class);
        intent.putExtra("roomId", roomIdArray.get(position));
        startActivity(intent);
    }

    public void toCreateChatRoom(View view){
        Intent intent = new Intent(this, StartAChat.class);
        intent.putExtra("name", profileName.getText().toString());
        intent.putExtra("id", firebaseAuth.getCurrentUser().getUid());
        intent.putExtra("url", theUrl);
        startActivity(intent);
    }
}