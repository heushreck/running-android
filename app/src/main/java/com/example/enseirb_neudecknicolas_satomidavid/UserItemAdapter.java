package com.example.enseirb_neudecknicolas_satomidavid;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.User;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder>{

    private User[] userListData;
    private ViewGroup parent;

    // RecyclerView recyclerView;
    public UserItemAdapter(User[] userListData) {
        this.userListData = userListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.user_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = userListData[position];
        holder.title.setText(userListData[position].getFirst_name() + " " + userListData[position].getLast_name());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("user", user);
                ( (Activity) parent.getContext()).setResult(Activity.RESULT_OK,returnIntent);
                ( (Activity) parent.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userListData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout = itemView.findViewById(R.id.user_row_item_linear_layout);
            this.title = itemView.findViewById(R.id.user_row_item_text);
        }
    }

}
