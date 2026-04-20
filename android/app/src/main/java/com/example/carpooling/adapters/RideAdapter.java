package com.example.carpooling.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.R;
import com.example.carpooling.models.Ride;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private List<Ride> rides;
    private OnRideClickListener listener;
    private boolean isDriver;

    public interface OnRideClickListener {
        void onBookClick(Ride ride);
    }

    public RideAdapter(List<Ride> rides, boolean isDriver, OnRideClickListener listener) {
        this.rides = rides;
        this.isDriver = isDriver;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ride, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        Ride ride = rides.get(position);
        holder.tvDriverName.setText(ride.getUserName());
        holder.tvPrice.setText(ride.getPrix() + " DT");
        holder.tvRoute.setText(ride.getDepart() + " ➔ " + ride.getArrivee());
        holder.tvDateTime.setText(ride.getDateHeure());
        holder.tvPlaces.setText(ride.getPlaces() + " places disponibles");

        if (isDriver) {
            holder.btnBook.setVisibility(View.GONE);
        } else {
            holder.btnBook.setVisibility(View.VISIBLE);
            holder.btnBook.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBookClick(ride);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriverName, tvPrice, tvRoute, tvDateTime, tvPlaces;
        Button btnBook;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverName = itemView.findViewById(R.id.tvDriverName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvPlaces = itemView.findViewById(R.id.tvPlaces);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
