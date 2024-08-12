package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TLViewHolder> implements Filterable {
    Context mContext;
    List<org.coepmindspark.mindspark.Events> mData;
    List<org.coepmindspark.mindspark.Events> mDataFiltered;






    public TimelineAdapter(Context mContext, List<org.coepmindspark.mindspark.Events> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }


    @NonNull
    @Override
    public TLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.timeline_item, parent, false);
        return new TLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TLViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.userImgCard.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("title",mDataFiltered.get(position).getTitle());
                intent.putExtra("imgURL",mDataFiltered.get(position).getThumbnail());
                intent.putExtra("imgCover",mDataFiltered.get(position).getCoverPhoto());
                intent.putExtra("des",mDataFiltered.get(position).getDescription());
                intent.putExtra("timestamp",mDataFiltered.get(position).getTimestamp());
                intent.putExtra("regLink",mDataFiltered.get(position).getEventLink());
                intent.putExtra("x_cord",mDataFiltered.get(position).getX_cord());
                intent.putExtra("y_cord",mDataFiltered.get(position).getY_cord());
                intent.putExtra("venue",mDataFiltered.get(position).getVenue());
                intent.putExtra("price",mDataFiltered.get(position).getPrice());
                intent.putExtra("fees",mDataFiltered.get(position).getFees());
                intent.putExtra("winners",mDataFiltered.get(position).getWinners());
                mContext.startActivity(intent);
            }
        });

        holder.tv_title.setText(mDataFiltered.get(position).getTitle());

        Timestamp timeStamp = new Timestamp(mDataFiltered.get(position).getTimestamp());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm" );

        Date today = new Date();
        Timestamp ts1 = new Timestamp(today.getTime());
        long tsTime1 = ts1.getTime();
        if(mDataFiltered.get(position).getTimestamp() == 0){
            holder.tv_date.setText("Coming soon...");
        }else {
            if (tsTime1 - mDataFiltered.get(position).getTimestamp() > 0 && tsTime1 - mDataFiltered.get(position).getTimestamp() < 3600 * 1000) {
                holder.container.setBackgroundResource(R.drawable.card_bg_running);
            }
            if (mDataFiltered.get(position).getTimestamp() - tsTime1 > 0 && mDataFiltered.get(position).getTimestamp() - tsTime1 < 3600 * 500) {
                holder.container.setBackgroundResource(R.drawable.card_bg_coming);
            }
            holder.tv_date.setText(simpleDateFormat.format(timeStamp));
        }
        holder.tv_content.setText(mDataFiltered.get(position).getVenue());

        Glide.with(mContext).load(mDataFiltered.get(position).getThumbnail()).centerCrop().placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.img_user);
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if(Key.isEmpty()){
                    mDataFiltered = mData;
                }
                else{
                    List<org.coepmindspark.mindspark.Events> lstFiltered = new ArrayList<>();
                    for(org.coepmindspark.mindspark.Events row: mData){
                        if(row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }


                    mDataFiltered = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFiltered= (List<org.coepmindspark.mindspark.Events>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class TLViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title, tv_content, tv_date;
        ImageView img_user;
        RelativeLayout container;
        CardView userImgCard;


        public TLViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);
            userImgCard = itemView.findViewById(R.id.img_user_card);

        }
    }
}
