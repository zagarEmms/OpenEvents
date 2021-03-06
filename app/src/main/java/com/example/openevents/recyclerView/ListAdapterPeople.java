package com.example.openevents.recyclerView;

import android.content.Context;
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
import com.example.openevents.business.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapterPeople extends RecyclerView.Adapter<ListAdapterPeople.ViewHolder> {

    private Context context;
    private ArrayList<User> peopleArrayList;
    private MyOnClickListener listenerPeople;

    public void setListenerPeople(MyOnClickListener listenerPeople) {
        this.listenerPeople = listenerPeople;
    }

    public ListAdapterPeople(Context context, ArrayList<User> peopleArrayList) {
        this.context = context;
        this.peopleArrayList = peopleArrayList;

    }

    @NonNull
    @Override
    public ListAdapterPeople.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item, parent, false);
        return new ViewHolder(view, listenerPeople);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterPeople.ViewHolder holder, int position) {
        holder.name.setText(peopleArrayList.get(position).getName());
        Log.i("person", ""+peopleArrayList.get(position).getName());
        holder.last_name.setText(peopleArrayList.get(position).getLastName());
        Log.i("person", peopleArrayList.get(position).getImageUrl());

        try {
            Picasso.get()
                    .load(peopleArrayList.get(position).getImageUrl())
                    .into(holder.personImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            holder.personImage.setImageResource(R.drawable.sofia);
                        }
                    });
        } catch (IllegalArgumentException iae) {
            holder.personImage.setImageResource(R.drawable.sofia);
        }
    }

    @Override
    public int getItemCount() {
        return peopleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView last_name;
        private ImageView personImage;

        private LinearLayout linearLayout;
        private MyOnClickListener listener;

        public ViewHolder(@NonNull View itemView, MyOnClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            last_name = itemView.findViewById(R.id.itemLast);
            personImage = itemView.findViewById(R.id.personImg);

            linearLayout = itemView.findViewById(R.id.layout_id);

            this.listener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.myOnClick(view, getBindingAdapterPosition());
            }
        }
    }
}
