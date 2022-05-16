package com.example.openevents.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openevents.R;
import com.example.openevents.business.Event;
import com.example.openevents.business.User;

import java.util.ArrayList;

public class ListAdapterMyEvents extends RecyclerView.Adapter<ListAdapterMyEvents.ViewHolder>  {

    private Context context;
    private ArrayList<Event> eventsArrayList;
    private MyOnClickListener listenerMyEvents;

    public void setListenerMyEvents(MyOnClickListener listenerMyEvents) {
        this.listenerMyEvents = listenerMyEvents;
    }

    public ListAdapterMyEvents(Context context, ArrayList<Event> eventsArrayList) {
        this.context = context;
        this.eventsArrayList = eventsArrayList;
    }

    @NonNull
    @Override
    public ListAdapterMyEvents.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_event_item, parent, false);
        return new ViewHolder(view, listenerMyEvents);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterMyEvents.ViewHolder holder, int position) {
        holder.eventName.setText(eventsArrayList.get(position).getName());
        holder.startDate.setText(eventsArrayList.get(position).getEventStart_date());
        holder.location.setText(eventsArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
