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

import java.util.List;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {
    private List<Category> categories;
    private static final String TAG = "RecyclerView";
      public AllCategoriesAdapter(  List<Category> categories) {
        this.categories = categories;
      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_all_caregories, parent, false);
        Log.i(TAG, "oncreate");

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Category currentCategory = categories.get(position);
        if (holder.txtTitle != null) {
            holder.txtTitle.setText(currentCategory.getStrCategory());
        } else {
            Log.e(TAG, "txtTitle is NULL at position: " + position);
        }
        Glide.with(holder.itemView)
                .load(currentCategory.getStrCategoryThumb())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorplaceholder)
                .into(holder.imageView);
         holder.layout.setOnClickListener(v -> {
            allcategoriesDirections.ActionAllcategoriesToFilterby
            action =
                    allcategoriesDirections.actionAllcategoriesToFilterby(
                            "Categories",
                            "Category",
                            currentCategory.getStrCategory()
                    );
            Navigation.findNavController(v).navigate(action);
        });
    }
    @Override
    public int getItemCount() {

        return categories.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ImageView imageView;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = v.findViewById(R.id.tvAllCategoryName);
            imageView = v.findViewById(R.id.imgAllCategory);
        }
    }
}