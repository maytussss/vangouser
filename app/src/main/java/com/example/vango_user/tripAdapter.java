package com.example.vango_user;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class tripAdapter extends FirestoreRecyclerAdapter<tripList, tripAdapter.tripHolder> {

    public tripAdapter(@NonNull FirestoreRecyclerOptions<tripList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull tripHolder holder, int position, @NonNull tripList model) {
        holder.startText.setText(model.getStart());
        holder.destinationText.setText(model.getDestination());
        holder.firstTripText.setText(model.getFirstTrip());
        holder.lastTripText.setText(model.getLastTrip());
        holder.priceText.setText(String.valueOf(model.getPrice()));
    }

    @NonNull
    @Override
    public tripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.triplist_card, parent, false);
        return new tripHolder(v);
    }

    class tripHolder extends RecyclerView.ViewHolder {

        TextView startText;
        TextView destinationText;
        TextView firstTripText;
        TextView lastTripText;
        TextView priceText;

        public tripHolder(@NonNull View itemView) {
            super(itemView);
            startText = itemView.findViewById(R.id.startText);
            destinationText = itemView.findViewById(R.id.destinationText);
            firstTripText = itemView.findViewById(R.id.firstTripText);
            lastTripText = itemView.findViewById(R.id.lastTripText);
            priceText = itemView.findViewById(R.id.priceText);

        }
    }
}
