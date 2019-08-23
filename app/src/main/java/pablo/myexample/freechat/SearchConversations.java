package pablo.myexample.freechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.ArrayList;

public class SearchConversations extends AppCompatActivity {

    ChatPreviewAdapter chatPreviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_conversations);

        ArrayList<ChatPreviewCardObject> arrayList = new ArrayList<>();
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        ChatPreviewCardObject chatPreviewCardObject = new ChatPreviewCardObject(bitmap, "username", "This is the preview of the message it is not a real message!", "date", "time", "1");
        ChatPreviewCardObject chatPreviewCardObject2 = new ChatPreviewCardObject(bitmap, "username2", "preview2", "date2", "time2", "2");
        arrayList.add(chatPreviewCardObject);
        arrayList.add(chatPreviewCardObject2);
        RecyclerView recyclerView = findViewById(R.id.chosenforchatrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatPreviewAdapter = new ChatPreviewAdapter(this, arrayList);
        recyclerView.setAdapter(chatPreviewAdapter);

    }
}
