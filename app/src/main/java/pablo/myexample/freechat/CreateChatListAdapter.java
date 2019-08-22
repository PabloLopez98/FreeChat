package pablo.myexample.freechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateChatListAdapter extends RecyclerView.Adapter<CreateChatListAdapter.ViewHolder>{
    private ArrayList<UserChosenForChat> mData;
    private LayoutInflater mInflater;

    CreateChatListAdapter(Context context, ArrayList<UserChosenForChat> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public CreateChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_chosen_for_chat, parent, false);
        return new CreateChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreateChatListAdapter.ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.email.setText(mData.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, email;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userchosenname);
            email = itemView.findViewById(R.id.userchosenemail);
        }
    }
}
