package pablo.myexample.freechat;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuInflater;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ChatPreviewAdapter chatPreviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<ChatPreviewCardObject> arrayList = new ArrayList<>();

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

        ChatPreviewCardObject chatPreviewCardObject = new ChatPreviewCardObject(bitmap, "username", "This is the preview of the message it is not a real message!", "date", "time", "1");
        ChatPreviewCardObject chatPreviewCardObject2 = new ChatPreviewCardObject(bitmap, "username2", "preview2", "date2", "time2", "2");

        arrayList.add(chatPreviewCardObject);
        arrayList.add(chatPreviewCardObject2);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatPreviewAdapter = new ChatPreviewAdapter(this, arrayList);
        recyclerView.setAdapter(chatPreviewAdapter);

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
        } else if (id == R.id.profile) {
        } else if (id == R.id.startAChat) {
        }
        DrawerLayout drawer = findViewById(R.id.coordinator_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void searchChats(MenuItem item){
        Toast.makeText(this, "Search",Toast.LENGTH_SHORT).show();
    }
}