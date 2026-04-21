package com.example.carpooling.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpooling.R;
import com.example.carpooling.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookings;
    private OnBookingActionListener listener;

    public interface OnBookingActionListener {
        void onAccept(Booking booking);
        void onReject(Booking booking);
    }

    public BookingAdapter(List<Booking> bookings, OnBookingActionListener listener) {
        this.bookings = bookings;
        this.listener = listener;
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

        String status = booking.getStatus() != null ? booking.getStatus() : "pending";
        holder.tvStatus.setText("Statut: " + status);

        if ("pending".equals(status)) {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0"));
            holder.tvStatus.setTextColor(Color.parseColor("#F57C00"));
            holder.layoutActions.setVisibility(View.VISIBLE);
        } else if ("accepted".equals(status)) {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));
            holder.layoutActions.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.tvStatus.setTextColor(Color.parseColor("#C62828"));
            holder.layoutActions.setVisibility(View.GONE);
        }

        holder.btnAccept.setOnClickListener(v -> {
            if (listener != null) listener.onAccept(booking);
        });

        holder.btnReject.setOnClickListener(v -> {
            if (listener != null) listener.onReject(booking);
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvPassengerName, tvPassengerPhone, tvRideDetails, tvRideDateTime, tvStatus;
        Button btnAccept, btnReject;
        LinearLayout layoutActions;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassengerName = itemView.findViewById(R.id.tvPassengerName);
            tvPassengerPhone = itemView.findViewById(R.id.tvPassengerPhone);
            tvRideDetails = itemView.findViewById(R.id.tvRideDetails);
            tvRideDateTime = itemView.findViewById(R.id.tvRideDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
            layoutActions = itemView.findViewById(R.id.layoutActions);
        }
    }
}
