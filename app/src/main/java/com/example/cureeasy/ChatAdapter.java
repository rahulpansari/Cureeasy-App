package com.example.cureeasy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>
{
    Context context;
    String uuid,p;
    ArrayList<Map<String,String>> mymsg;
    public ChatAdapter(Context c,ArrayList<Map<String,String>> m,String id,String p) {
        super();
        context=c;
        mymsg=m;
        this.p=p;
        uuid=id;
        Log.e("iamn",uuid+"");

    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.chat_msg_layout,parent,false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
String h=mymsg.get(position).get("from");
String y=uuid;
        Log.e("iam",uuid);
        Log.e("iam",h);
if(h.equals(y))
        {
            Log.e("iamhere","HTYsvzthR9QuN7g2a0GDaKDZAw12".equals("g8Pfu37cUUYDdxmcJuogTlQGLqk2")+"");
            holder.profile.setVisibility(View.GONE);
            holder.sender.setVisibility(View.GONE);
            holder.reciever.setVisibility(View.VISIBLE);
            holder.reciever.setText(mymsg.get(position).get("message"));
        }
else
{Log.e("oops","here");
    holder.profile.setVisibility(View.VISIBLE);
    Picasso.get().load(p).into(holder.profile);
    holder.sender.setVisibility(View.VISIBLE);
    holder.reciever.setVisibility(View.GONE);
    holder.sender.setText(mymsg.get(position).get("message"));
}

    }

    @Override
    public int getItemCount() {
        return mymsg.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder
{
    TextView sender,reciever;
    ImageView profile;
    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        sender=itemView.findViewById(R.id.chat_sender);
        reciever=itemView.findViewById(R.id.chat_reciever);
        profile=itemView.findViewById(R.id.profile);

    }
}


}
