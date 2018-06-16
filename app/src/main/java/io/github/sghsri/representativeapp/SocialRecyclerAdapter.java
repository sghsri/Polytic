package io.github.sghsri.representativeapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class SocialRecyclerAdapter extends RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder> {
    private List<Representative.SocialMedia> socialMedia;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView fabSocial;
        public ViewHolder(View v) {
            super(v);
            fabSocial = (ImageView) v.findViewById(R.id.socialim);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SocialRecyclerAdapter(List<Representative.SocialMedia> myDataset, Context context) {
        System.out.println(context);
        socialMedia = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SocialRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.socialrow, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.fabSocial.setBackgroundResource(getDrawable(socialMedia.get(position).type.toLowerCase()));

                //context.getResources().getDrawable(context.getResources().getIdentifier(socialMedia.get(position).type.toLowerCase(),"drawable",context.getPackageName())));
    }

    private int getDrawable(String name){
        switch (name){
            case "facebook": return R.drawable.facebook;
            case "twitter": return R.drawable.twitter;
            case "googleplus": return R.drawable.google;
            case "youtube": return R.drawable.youtube;
            case "instagram": return R.drawable.instagram;
            default: return 0;
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return socialMedia.size();
    }
}