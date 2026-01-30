package com.hammi.foodplanner.ui.view.Countries;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Area;
import com.hammi.foodplanner.data.models.remote.Countries;

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
        } else {
            holder.tvInitials.setText("not Found");
        }

         Area area = new Area(name);
        Glide.with(holder.ivFlag.getContext())
                .load(area.getFlagUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivFlag);

        holder.layout.setOnClickListener(v -> {
            countriesFragmentDirections.ActionCountriesFragmentToFilterby action =
                    countriesFragmentDirections.actionCountriesFragmentToFilterby(
                            "Countries",
                            "Country",
                            currentCountry.getStrArea()
                    );
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvInitials;
        public TextView tvSubtitle;
        public View layout;
        public ImageView ivFlag;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvTitle = v.findViewById(R.id.tv_title);
            tvInitials = v.findViewById(R.id.tv_initials);
            tvSubtitle = v.findViewById(R.id.tv_subtitle);
            ivFlag = v.findViewById(R.id.iv_flag);
        }
    }
}
