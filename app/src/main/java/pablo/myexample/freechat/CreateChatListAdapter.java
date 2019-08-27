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
    private OnUserListener onUserListener;

    CreateChatListAdapter(Context context, ArrayList<User> data, OnUserListener onUserListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.onUserListener = onUserListener;
    }

    @Override
    public CreateChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_chosen_for_chat, parent, false);
        return new CreateChatListAdapter.ViewHolder(view, onUserListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, nickname, id, url;
        ImageView imageView;
        OnUserListener onUserListener;
        ViewHolder(View itemView, OnUserListener onUserListener) {
            super(itemView);
            name = itemView.findViewById(R.id.userchosenname);
            nickname = itemView.findViewById(R.id.userchosennickname);
            id = itemView.findViewById(R.id.userChosenId);
            url = itemView.findViewById(R.id.userChosenUrl);
            imageView = itemView.findViewById(R.id.userChosenProfileImage);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserListener(getAdapterPosition());
        }
    }

    public interface OnUserListener{
        void onUserListener(int position);
    }
}
