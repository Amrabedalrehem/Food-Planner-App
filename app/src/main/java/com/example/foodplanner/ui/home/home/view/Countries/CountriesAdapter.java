package com.example.foodplanner.ui.home.home.view.Countries;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.Countries;

import java.util.List;
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    List<Countries> countries;

    public CountriesAdapter(List<Countries> countries) {
        this.countries = countries;
    }

     public void setList(List<Countries> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_countries, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Countries currentCountry = countries.get(position);
        String name = currentCountry.getStrArea();

         holder.tvTitle.setText(name);

         if (name != null && name.length() >= 2) {
            holder.tvInitials.setText(name.substring(0, 2).toUpperCase());
        } else{
             holder.tvInitials.setText("not Found");
        }
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvInitials;
        public TextView tvSubtitle;

        public ViewHolder(View v) {
            super(v);
             tvTitle = v.findViewById(R.id.tv_title);
            tvInitials = v.findViewById(R.id.tv_initials);
            tvSubtitle = v.findViewById(R.id.tv_subtitle);
        }
    }
}