
package jworks.letsbake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jworks.letsbake.R;
import jworks.letsbake.model.Ingredient;

/**
 * Created by CHUKWU JOHNPAUL on 13/06/17.
 */

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {


    final private ArrayList<Ingredient> ingredient;
    public IngredientAdapter(ArrayList<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient_name,ingredient_quantity,ingredient_measure;

        public ViewHolder(View view) {
            super(view);
            ingredient_name =(TextView) view.findViewById(R.id.tv_iname);
            ingredient_quantity =(TextView) view.findViewById(R.id.tv_unit_no);
            ingredient_measure =(TextView) view.findViewById(R.id.tv_unit);
        }
        void onBind(int position) {
            if(!ingredient.isEmpty()){
                ingredient_name.setText(ingredient.get(position).getIngredient());
                ingredient_quantity.setText(String.valueOf(ingredient.get(position).getQuantity()));
                ingredient_measure.setText(ingredient.get(position).getMeasure());
            }
            else{
                return;
            }

        }

    }
}
