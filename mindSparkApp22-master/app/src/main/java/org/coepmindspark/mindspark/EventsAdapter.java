package org.coepmindspark.mindspark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{
    Context context ;
    List<org.coepmindspark.mindspark.Events> eData;
    org.coepmindspark.mindspark.EventItemClickListener eventItemClickListener;


    public EventsAdapter(Context context, List<org.coepmindspark.mindspark.Events> mData, org.coepmindspark.mindspark.EventItemClickListener listener) {
        this.context = context;
        this.eData = mData;
        eventItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_item,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.Title.setText(eData.get(i).getTitle());
        Glide.with(context).load(eData.get(i).getThumbnail()).transform(new CenterCrop(), new RoundedCorners(25)).placeholder(R.drawable.placeholder_bg).error(R.drawable.eventerror).into(myViewHolder.ImgEvent);
    }

    @Override
    public int getItemCount() {
        return eData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView Title;
        private final ImageView ImgEvent;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            Title = itemView.findViewById(R.id.item_event_title);
            ImgEvent = itemView.findViewById(R.id.item_event_img);

            itemView.setOnClickListener(v -> eventItemClickListener.onEventClick(eData.get(getAdapterPosition()),ImgEvent));

        }
    }
}
