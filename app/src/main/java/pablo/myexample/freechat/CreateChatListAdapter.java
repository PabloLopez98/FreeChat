package pablo.myexample.freechat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class CreateChatListAdapter extends RecyclerView.Adapter<CreateChatListAdapter.ViewHolder>{

    private ArrayList<User> mData;
    private LayoutInflater mInflater;

    CreateChatListAdapter(Context context, ArrayList<User> data) {
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
        holder.name.setText(mData.get(position).getUserName());
        holder.nickname.setText(mData.get(position).getNickName());
        holder.id.setText(mData.get(position).getUserId());
        holder.url.setText(mData.get(position).getProfileUrl());
        Picasso.with(holder.itemView.getContext()).load(mData.get(position).getProfileUrl()).fit().into(holder.imageView);
        Log.i("inspectthis",mData.get(position).getProfileUrl());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, nickname, id, url;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userchosenname);
            nickname = itemView.findViewById(R.id.userchosennickname);
            id = itemView.findViewById(R.id.userChosenId);
            url = itemView.findViewById(R.id.userChosenUrl);
            imageView = itemView.findViewById(R.id.userChosenProfileImage);
        }
    }
}
