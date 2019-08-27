package pablo.myexample.freechat;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        navigationView = findViewById(R.id.nav_view);
        profileImage = navigationView.getHeaderView(0).findViewById(R.id.imageViewHeader);
        profileName = navigationView.getHeaderView(0).findViewById(R.id.nameViewHeader);
        profileNickName = navigationView.getHeaderView(0).findViewById(R.id.nickNameViewHeader);

        setUpProfileDrawerHeader();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        arrayList = new ArrayList<>();

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        ChatPreviewCardObject chatPreviewCardObject = new ChatPreviewCardObject(bitmap, "username", "This is the preview of the message it is not a real message!", "date", "time", "1");
        ChatPreviewCardObject chatPreviewCardObject2 = new ChatPreviewCardObject(bitmap, "username2", "preview2", "date2", "time2", "2");

        arrayList.add(chatPreviewCardObject);
        arrayList.add(chatPreviewCardObject2);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatPreviewAdapter = new ChatPreviewAdapter(this, arrayList, this);
        recyclerView.setAdapter(chatPreviewAdapter);

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
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else if (id == R.id.startAChat) {
            Intent intent = new Intent(this, StartAChat.class);
            intent.putExtra("name",profileName.getText().toString());
            intent.putExtra("id",firebaseAuth.getCurrentUser().getUid());
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
        startActivity(intent);
    }
}