package com.example.enseirb_neudecknicolas_satomidavid;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.Run;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.DatabankHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RunItemAdapter extends RecyclerView.Adapter<RunItemAdapter.ViewHolder>{

    private List<Run> runListData;

    Run mRecentlyDeletedItem;
    int mRecentlyDeletedItemPosition;

    ViewGroup parent;

    DatabankHandler db;

    // RecyclerView recyclerView;
    public RunItemAdapter(List<Run> runListData, DatabankHandler db) {
        this.runListData = runListData;
        this.db = db;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.run_row_iten, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.Date.setText(runListData.get(position).getDateString());
        holder.username.setText(runListData.get(position).getUser().getFirst_name() + " " + runListData.get(position).getUser().getLast_name());
        holder.length.setText(runListData.get(position).getLengthString());
        holder.time.setText(runListData.get(position).getSecondsString());
        holder.averagespeed.setText(runListData.get(position).getAverageSpeedString());
        holder.topspeed.setText(runListData.get(position).getTopSpeedString());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mRecentlyDeletedItem = runListData.get(position);
                mRecentlyDeletedItemPosition = position;
                runListData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, runListData.size());
                db.removeSingleRun(mRecentlyDeletedItem.getDate(), mRecentlyDeletedItem.getUser());
                showUndoSnackbar();
                return false;
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), ShowRunActivity.class);
                intent.putExtra("json", runListData.get(position).getLocationList().toString());
                parent.getContext().startActivity(intent);
            }
        });

    }

    private void showUndoSnackbar() {
        Snackbar snackbar = Snackbar.make(parent, "Run from " + mRecentlyDeletedItem.getUser().getFirst_name() + " deleted",Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runListData.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
                notifyItemInserted(mRecentlyDeletedItemPosition);
                notifyItemRangeChanged(mRecentlyDeletedItemPosition, runListData.size());
                db.addRun(mRecentlyDeletedItem);
            }
        });
        snackbar.show();
    }

    @Override
    public int getItemCount() {
        return runListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Date;
        public TextView username;
        public TextView length;
        public TextView time;
        public TextView averagespeed;
        public TextView topspeed;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Date = itemView.findViewById(R.id.Date);
            this.username = itemView.findViewById(R.id.username);
            this.length = itemView.findViewById(R.id.length);
            this.time = itemView.findViewById(R.id.time);
            this.averagespeed = itemView.findViewById(R.id.averagespeed);
            this.topspeed = itemView.findViewById(R.id.topspeed);
            this.linearLayout = itemView.findViewById(R.id.run_row_item_layout);
        }
    }

}
