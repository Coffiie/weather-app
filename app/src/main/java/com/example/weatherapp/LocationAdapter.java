package com.example.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    // ctrl + i
    Context context;
    List<LatLng> locationList;
    DatabaseHelper helper;
    LatLng location;

    public LocationAdapter(Context context, List<LatLng> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View itemView = inflator.inflate(R.layout.recycler_view_single_layout_file, parent, false);
        LocationViewHolder holder = new LocationViewHolder(itemView);
        helper = new DatabaseHelper(context);
        return holder;
    }

    private void openMapActivity(double lat, double lon) {
        Intent intent = new Intent(context, MapsDetailsActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        locationList.get(position);
        final Long[] result = new Long[1];
        result[0] = Long.valueOf(position);
        holder.longTV.setText(String.format("%.2f", locationList.get(position).longitude));
        holder.latTV.setText(String.format("%.2f", locationList.get(position).latitude));
        holder.locIndexTV.setText(String.valueOf(position + 1));
        holder.tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity(locationList.get(result[0].intValue()).latitude, locationList.get(result[0].intValue()).longitude);
            }
        });

        holder.setLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager manager = new SharedPreferenceManager(context);
                manager.writeLocationToStorage(new OfflineStorageModel(new LatLng(locationList.get(result[0].intValue()).latitude, locationList.get(result[0].intValue()).longitude)));
                Toast toast = Toast.makeText(context, "Location has been set", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                view.setBackgroundColor(Color.parseColor("#46BC4B"));
                toast.show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Delete");
                dialog.setMessage("This will delete your record, are you sure?");
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.delete(locationList.get(result[0].intValue()).latitude);
                        Toast toast = Toast.makeText(context, "Deleted!", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.parseColor("#FFFFFF"));
                        view.setBackgroundColor(Color.parseColor("#46BC4B"));
                        toast.show();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView latTV, longTV, locIndexTV;
        LinearLayout tile;
        ImageButton deleteButton;
        Button setLocationbtn;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            latTV = itemView.findViewById(R.id.latTV);
            longTV = itemView.findViewById(R.id.lonTV);
            deleteButton = itemView.findViewById(R.id.deleteIconButton);
            locIndexTV = itemView.findViewById(R.id.locIndexTV);
            tile = itemView.findViewById(R.id.tile);
            setLocationbtn = itemView.findViewById(R.id.setLocationBtn);
        }
    }
}
