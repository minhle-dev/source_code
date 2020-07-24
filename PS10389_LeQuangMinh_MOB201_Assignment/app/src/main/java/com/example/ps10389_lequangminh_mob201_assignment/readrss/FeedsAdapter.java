package com.example.ps10389_lequangminh_mob201_assignment.readrss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.WebviewActivity;
import com.example.ps10389_lequangminh_mob201_assignment.model.FeedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {
    ArrayList<FeedItem> feedItems;
    Context context;
    public LayoutInflater inflater;


    public FeedsAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custum_row_news_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("Link", current.getLink());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (feedItems == null) ? 0 : feedItems.size();
        //return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Description, Date;
        ImageView Thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title_text);
            Description = itemView.findViewById(R.id.description_text);
            Date = itemView.findViewById(R.id.date_text);
            Thumbnail = itemView.findViewById(R.id.thumb_img);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
