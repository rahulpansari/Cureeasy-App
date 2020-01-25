package com.example.cureeasy;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

public class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView youtubeimage;
    TextView youtubetext;

    ArrayList<YoutubeGET.Items> item;
    Context x;
    public RecycleHolder(@NonNull View itemView, Context context, ArrayList<YoutubeGET.Items> f) {
        super(itemView);

        youtubeimage=itemView.findViewById(R.id.youtube_image);
        youtubetext=itemView.findViewById(R.id.youtube_text);
        itemView.setOnClickListener(this);
        item=f;
        x=context;
    }

    @Override
    public void onClick(View v) {
Intent i=new Intent(x, YoutubeActivity.class);
i.putExtra("videoId",item.get(getAdapterPosition()).getSnippet().getResourceId().getVideoid());
x.startActivity(i);
    }
}
