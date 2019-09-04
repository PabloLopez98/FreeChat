package pablo.myexample.freechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<MessageObject> mData;
    private LayoutInflater mInflater;
    private FirebaseAuth firebaseAuth;
    private String myId;


    @Override
    public int getItemViewType(int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        myId = firebaseAuth.getCurrentUser().getUid();
        if (mData.get(position).getMessageId().matches(myId)) {
            return 1;
        } else {
            return 0;
        }
    }

    MessageAdapter(Context context, ArrayList<MessageObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = mInflater.inflate(R.layout.messagecardmine, parent, false);
        } else {
            view = mInflater.inflate(R.layout.messagecard, parent, false);
        }
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        holder.messageName.setText(mData.get(position).getMessageName());
        holder.messageText.setText(mData.get(position).getMessageText());
        holder.messageDate.setText(mData.get(position).getMessageDate());
        holder.messageTime.setText(mData.get(position).getMessageTime());
        holder.messageId.setText(mData.get(position).getMessageId());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageName, messageText, messageDate, messageTime, messageId;

        ViewHolder(View itemView) {
            super(itemView);
            messageName = itemView.findViewById(R.id.messageName);
            messageText = itemView.findViewById(R.id.messageText);
            messageDate = itemView.findViewById(R.id.messageDate);
            messageTime = itemView.findViewById(R.id.messageTime);
            messageId = itemView.findViewById(R.id.messageId);
        }
    }
}
