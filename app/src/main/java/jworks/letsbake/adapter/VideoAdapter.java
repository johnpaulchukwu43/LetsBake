
package jworks.letsbake.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jworks.letsbake.Interface.ListItemClickListener;
import jworks.letsbake.R;
import jworks.letsbake.model.Steps;

/**
 * Created by CHUKWU JOHNPAUL on 13/06/17.
 */

public class VideoAdapter
        extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    final private ArrayList<Steps> steps;
    public Context ctx;
    final private ListItemClickListener mOnClickListener;
    public ArrayList<Steps>videoList;

    public VideoAdapter(ListItemClickListener mListItemClickListener,Context ctx,ArrayList<Steps> steps) {
        this.steps = steps;
        this.mOnClickListener = mListItemClickListener;
        this.ctx = ctx;
        videoList = getNodeWithVideos(steps);
    }

    //Get all the items in arrayList that has a video
    public ArrayList<Steps> getNodeWithVideos(ArrayList<Steps> mSteps){
        ArrayList<Steps> containsVideo = new ArrayList<>() ;
        for(int position=0;position<mSteps.size();position++){
            if(!steps.get(position).getVideoURL().isEmpty()){
                containsVideo.add(mSteps.get(position));
            }
        }
        return containsVideo;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView video_name;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            video_name =(TextView) view.findViewById(R.id.tv_vname);


        }
        void onBind(int position) {
            if(!videoList.isEmpty()){
                    video_name.setText(videoList.get(position).getShortDescription());
            }
            else{
                return;
            }

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }


    }
}
