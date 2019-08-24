package pablo.myexample.freechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private ArrayList<MessageObject> mData;
    private LayoutInflater mInflater;

    MessageAdapter(Context context, ArrayList<MessageObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.messagecard, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        holder.messageName.setText(mData.get(position).getMessageName());
        holder.messageText.setText(mData.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView messageName, messageText;
        ViewHolder(View itemView) {
            super(itemView);
            messageName = itemView.findViewById(R.id.messageName);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
