package pablo.myexample.freechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.ArrayList;

public class StartAChat extends AppCompatActivity {

    CreateChatListAdapter createChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_achat);
        setTitle("Start A Chat");

        ArrayList<UserChosenForChat> arrayList = new ArrayList<>();
        UserChosenForChat userChosenForChat = new UserChosenForChat("Pablo Lopez", "myemail@gmail.com");
        UserChosenForChat userChosenForChat2 = new UserChosenForChat("NoName Lopez", "nonamemyemail@gmail.com");

        arrayList.add(userChosenForChat);
        arrayList.add(userChosenForChat2);

        RecyclerView recyclerView = findViewById(R.id.chosenforchatrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createChatListAdapter = new CreateChatListAdapter(this, arrayList);
        recyclerView.setAdapter(createChatListAdapter);
    }
}
