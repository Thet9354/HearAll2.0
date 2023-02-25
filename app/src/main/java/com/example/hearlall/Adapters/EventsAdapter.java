package com.example.hearlall.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hearlall.EventDetailsActivity;
import com.example.hearlall.Model.Events;
import com.example.hearlall.R;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CardViewHolder>{

    private ArrayList<Events> eventsArrayList;
    private Context mContext;

    public EventsAdapter(Context mContext, ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private EventsAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(EventsAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public EventsAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.row_events, parent, false);

        return new EventsAdapter.CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.CardViewHolder holder, int position) {

        holder.txtView_eventTitle.setText(eventsArrayList.get(position).getEventTitle());
        holder.txtView_host.setText(eventsArrayList.get(position).getEventHost());
        holder.txtView_hostDesc.setText(eventsArrayList.get(position).getHostDesc());

        holder.imgView_event.setImageResource(eventsArrayList.get(position).getEventImage());
        holder.imgView_host.setImageResource(eventsArrayList.get(position).getHostImage());

        holder.cv_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailsActivity.class);
                int pos = holder.getAdapterPosition();
                intent.putExtra("Event Title", eventsArrayList.get(pos).getEventTitle());
                intent.putExtra("Host Name", eventsArrayList.get(pos).getEventHost());
                intent.putExtra("Host Desc", eventsArrayList.get(pos).getHostDesc());
                intent.putExtra("Event Participants", eventsArrayList.get(pos).getEventParticipants());
                intent.putExtra("Event Link", eventsArrayList.get(pos).getEventLink());

                intent.putExtra("Event Image", eventsArrayList.get(pos).getEventImage());
                intent.putExtra("Host Image", eventsArrayList.get(pos).getHostImage());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_events;
        private RelativeLayout rel_events;
        private ImageView imgView_event, imgView_host;
        private TextView txtView_eventTitle, txtView_host, txtView_hostDesc, txtView_eventParticipants, txtView_remindMe;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            //CardView
            cv_events = itemView.findViewById(R.id.cv_events);

            //RelativeLayout
            rel_events = itemView.findViewById(R.id.rel_events);

            //ImageView
            imgView_event = itemView.findViewById(R.id.imgView_event);
            imgView_host = itemView.findViewById(R.id.imgView_host);

            //TextView
            txtView_eventTitle = itemView.findViewById(R.id.txtView_eventTitle);
            txtView_host = itemView.findViewById(R.id.txtView_host);
            txtView_hostDesc = itemView.findViewById(R.id.txtView_hostDesc);
            txtView_eventParticipants = itemView.findViewById(R.id.txtView_eventParticipants);
            txtView_remindMe = itemView.findViewById(R.id.txtView_remindMe);
        }
    }
}
