package pablo.myexample.freechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatPreviewAdapter extends RecyclerView.Adapter<ChatPreviewAdapter.ViewHolder>{

    private ArrayList<ChatPreviewCardObject> mData;
    private LayoutInflater mInflater;

    ChatPreviewAdapter(Context context, ArrayList<ChatPreviewCardObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.chat_preview_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getUsername());
        holder.message_preview.setText(mData.get(position).getMessage_preview());
        holder.date.setText(mData.get(position).getDate());
        holder.time.setText(mData.get(position).getTime());
        holder.notifications.setText(mData.get(position).getNotifications());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message_preview, date, time, notifications;
        ViewHolder(View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.card_name);
             message_preview = itemView.findViewById(R.id.card_preivew_message);
             date = itemView.findViewById(R.id.card_date);
             time = itemView.findViewById(R.id.card_time);
             notifications = itemView.findViewById(R.id.card_notifications);
        }
    }
}
