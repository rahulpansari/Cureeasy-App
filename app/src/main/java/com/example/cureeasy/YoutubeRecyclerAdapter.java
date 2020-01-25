package com.example.cureeasy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.util.ArrayList;

public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<RecycleHolder> {
Context c;
    ArrayList<YoutubeGET.Items> a;
    public YoutubeRecyclerAdapter(Context context, ArrayList<YoutubeGET.Items> array)
{a=array;
    c=context;
}
    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(c).inflate(R.layout.youtube_recycler,null,false);

        return new RecycleHolder(v,c,a);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder recyclerHolder, int i) {

            try {
                recyclerHolder.youtubetext.setText(a.get(i).getSnippet().getTitle());
                Glide.with(c).asDrawable().load(a.get(i).getSnippet().getThumbnails().getDef().getUrl()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(recyclerHolder.youtubeimage);
            }
            catch (NullPointerException e)
            {

            }
    }

    @Override
    public int getItemCount() {
        return a.size();
    }
}
