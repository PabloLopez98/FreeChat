package pablo.myexample.freechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Conversation extends AppCompatActivity {

    MessageAdapter messageAdapter;
    ArrayList<MessageObject> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();
        MessageObject messageObject = new MessageObject("Pablo","This is my message","July 31","2:11pm");
        MessageObject messageObject2 = new MessageObject("Pablo","This is my message","August 1","2:13pm");
        arrayList.add(messageObject);
        arrayList.add(messageObject2);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_conversation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, arrayList);
        recyclerView.setAdapter(messageAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.add("Pablo");
        menu.add("Fernando");
        inflater.inflate(R.menu.menuinconversation, menu);
        return true;
    }
}