package com.example.carpooling.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.R;
import com.example.carpooling.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookings;

    public BookingAdapter(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.tvPassengerName.setText(booking.getPassengerName());
        holder.tvPassengerPhone.setText("Téléphone: " + booking.getPassengerPhone());
        holder.tvRideDetails.setText("Trajet: " + booking.getDepart() + " ➔ " + booking.getArrivee());
        holder.tvRideDateTime.setText("Le: " + booking.getDateHeure());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvPassengerName, tvPassengerPhone, tvRideDetails, tvRideDateTime;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassengerName = itemView.findViewById(R.id.tvPassengerName);
            tvPassengerPhone = itemView.findViewById(R.id.tvPassengerPhone);
            tvRideDetails = itemView.findViewById(R.id.tvRideDetails);
            tvRideDateTime = itemView.findViewById(R.id.tvRideDateTime);
        }
    }
}
