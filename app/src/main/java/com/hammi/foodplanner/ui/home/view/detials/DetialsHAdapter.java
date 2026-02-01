package com.hammi.foodplanner.ui.home.view.detials;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.IngredientItem;
import java.util.List;

public class DetialsHAdapter extends RecyclerView.Adapter<DetialsHAdapter.ViewHolder> {
    private List<IngredientItem> ingredients;

    public DetialsHAdapter(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detials_h_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientItem item = ingredients.get(position);
        holder.tvName.setText(item.name);
        holder.tvMeasure.setText(item.measure);

        Glide.with(holder.itemView.getContext())
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() { return ingredients == null ? 0 : ingredients.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvMeasure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.Detials_imageView);
            tvName = itemView.findViewById(R.id.Detials_txtIngredientName);
            tvMeasure = itemView.findViewById(R.id.Detials_txtIngredientMeasure);
        }
    }
}


