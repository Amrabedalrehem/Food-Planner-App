package com.hammi.foodplanner.ui.view.categorries;
import android.util.Log;
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
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.ui.view.homeFragmentDirections;

import java.util.List;

public class homeCategoriesAdapter extends RecyclerView.Adapter<homeCategoriesAdapter.ViewHolder> {
     private List<Category> categories;
    private static final String TAG = "RecyclerView";
    boolean isHome;

    public homeCategoriesAdapter(  List<Category> categories, boolean isHome) {
         this.categories = categories;
        this.isHome = isHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_categories, parent, false);
        Log.i(TAG, "oncreate");

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Category currentCategory = categories.get(position);

        holder.txtTitle.setText(currentCategory.getStrCategory());
        Glide.with(holder.itemView)
                .load(currentCategory.getStrCategoryThumb())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorplaceholder)
                .into(holder.imageView);

        holder.layout.setOnClickListener(v -> {
            homeFragmentDirections.ActionHomeFfragmentToFilterby action =
                    homeFragmentDirections.actionHomeFfragmentToFilterby(
                            "Categories",
                            "Category",
                            currentCategory.getStrCategory()
                    );
            Navigation.findNavController(v).navigate(action);
        });





    }
    @Override
    public int getItemCount() {
        if (isHome) {
            return Math.min(categories.size(), 4);
        }
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
         public ImageView imageView;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = v.findViewById(R.id.title_card_categories);
             imageView = v.findViewById(R.id.image_card_categories);
        }
    }
}