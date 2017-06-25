
package jworks.letsbake.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jworks.letsbake.Interface.ListItemClickListener;
import jworks.letsbake.R;
import jworks.letsbake.Utilities.ImageResources;
import jworks.letsbake.model.FoodItem;

/**
 * Created by CHUKWU JOHNPAUL on 13/06/17.
 */

public class FoodItemsAdapter
        extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder>  {


    final private ListItemClickListener mOnClickListener;
    ArrayList<FoodItem> recipie;
    public Context ctx;
    private boolean mTwoPane;
    public String[] Images;
    Uri uri;

    public FoodItemsAdapter(ListItemClickListener listener,ArrayList<FoodItem> recipie,Context ctx) {
        this.recipie = recipie;
        this.ctx = ctx;
        this.mOnClickListener = listener;
        Images = ImageResources.foodPic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooditem_list_content, parent, false);
        return new ViewHolder(view,this.ctx);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return recipie.size();
    }

    public void cleanUp(){
        if(getItemCount() > 0){
            recipie.clear();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView foodname,foodservings;
        ImageView foodimage;
        Button expbtn;
        Context ctx;

        public ViewHolder(View view,Context ctx) {
            super(view);
            foodservings =(TextView) view.findViewById(R.id.tv_servings);
            foodname =(TextView) view.findViewById(R.id.tv_name);
            foodimage = (ImageView)view.findViewById(R.id.iv_chef);
            expbtn = (Button)view.findViewById(R.id.btn_exp);
            this.ctx = ctx;
            /*setting the onclick listener on both button and the entire view, that way the use can
            either click on the button itself or any part of the card to go to the next activity
            */
            view.setOnClickListener(this);
            expbtn.setOnClickListener(this);
        }
        void onBind(int position) {
            if (!recipie.isEmpty()) {
                foodname.setText(recipie.get(position).getName());
                foodservings.setText(recipie.get(position).getServings());
                if(!recipie.get(position).getImage().isEmpty()){
                    Glide.with(ctx).load(recipie.get(position).getImage()).into(foodimage);
                }
                else{
                    uri = convertUri(Images[position]);
                    Glide.with(ctx).load(uri).into(foodimage);
                }

            }

        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }


        public  Uri convertUri(String url){
            Uri uri = Uri.parse(url);
            return uri;
        }
    }
}
