package com.example.openevents.recyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openevents.R;
import com.example.openevents.business.Event;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Event> eventsArrayList;
    private MyOnClickListener listener;

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public ListAdapter(Context context, ArrayList<Event> eventsArrayList) {
        this.context = context;
        this.eventsArrayList = eventsArrayList;

    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.eventName.setText(eventsArrayList.get(position).getName());
        holder.startDate.setText(eventsArrayList.get(position).getEventStart_date());
        holder.location.setText(eventsArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView eventName;
        private TextView startDate;
        private TextView location;

        private LinearLayout linearLayout;
        private MyOnClickListener listener;

        public ViewHolder(@NonNull View itemView, MyOnClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            eventName = itemView.findViewById(R.id.itemName);
            startDate = itemView.findViewById(R.id.start_date);
            location = itemView.findViewById(R.id.location);

            linearLayout = itemView.findViewById(R.id.layout_id);


        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.myOnClick(view, getBindingAdapterPosition());
            }
        }

    }

}
