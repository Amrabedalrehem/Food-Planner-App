package com.hammi.foodplanner.ui.home.home.view.Ingredients;

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
import com.hammi.foodplanner.data.models.remote.Ingredients;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
List<Ingredients> ingredients;
   public IngredientsAdapter(List<Ingredients> ingredients)
    {
        this.ingredients = ingredients;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_ingredients,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTitle.setText(ingredients.get(position).getStrIngredient());
        Glide.with(holder.itemView)
                .load(ingredients.get(position).getStrThumb())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorplaceholder)
                .into(holder.imageView);

        holder.layout.setOnClickListener(v -> {
             integredientsDirections.ActionIntegredientsToFilterby action =
                    integredientsDirections.actionIntegredientsToFilterby(
                            "Ingredients",
                            "Ingredient",
                            ingredients.get(position).getStrIngredient()
                    );
            Navigation.findNavController(v).navigate(action);
        });


    }

    @Override
    public int getItemCount() {
        return   ingredients != null ?  ingredients.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ImageView imageView;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
             txtTitle = itemView.findViewById(R.id.title_card_Ingredients);
             imageView = itemView.findViewById(R.id.image_card_categories_Ingredients);
        }
    }
   }





