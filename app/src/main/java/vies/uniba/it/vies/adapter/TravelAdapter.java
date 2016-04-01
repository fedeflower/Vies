package vies.uniba.it.vies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.model.Travel;

/**
 * Created by Daniele on 27/03/2016.
 */
public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private List<Travel> travels;

    private OnItemClickListener listener;

    public TravelAdapter(List<Travel> travels, OnItemClickListener listener) {
        this.travels = travels;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView travelName;
        public TextView travelDate;
        public TextView travelLocationName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            travelName = (TextView) itemView.findViewById(R.id.name);
            travelDate = (TextView) itemView.findViewById(R.id.date);
            travelLocationName = (TextView) itemView.findViewById(R.id.locationName);

        }

        public void bind(final Travel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.travel_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TravelAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Travel travel = travels.get(position);

        // Set item views based on the data model
        TextView travelName = viewHolder.travelName;
        travelName.setText(travel.getName());
        TextView travelDate = viewHolder.travelDate;
        travelDate.setText(travel.getFullDate());
        TextView travelLocationName = viewHolder.travelLocationName;
        travelLocationName.setText(travel.getLocation().getName());

        viewHolder.bind(travel, listener);
    }


    @Override
    public int getItemCount() {
        return travels.size();
    }

}