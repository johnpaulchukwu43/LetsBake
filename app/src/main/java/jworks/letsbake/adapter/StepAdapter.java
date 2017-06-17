
package jworks.letsbake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jworks.letsbake.R;
import jworks.letsbake.model.Steps;

/**
 * Created by CHUKWU JOHNPAUL on 13/06/17.
 */

public class StepAdapter
        extends RecyclerView.Adapter<StepAdapter.ViewHolder> {


    final private ArrayList<Steps> steps;
    public StepAdapter(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView step_name,step_description;

        public ViewHolder(View view) {
            super(view);
            step_name =(TextView) view.findViewById(R.id.tv_nameS);
            step_description =(TextView) view.findViewById(R.id.tv_descriptionS);
        }
        void onBind(int position) {
            if(!steps.isEmpty()){
                step_name.setText(steps.get(position).getShortDescription());
                step_description.setText(steps.get(position).getDescription());
            }
            else{
                return;
            }

        }

    }
}
