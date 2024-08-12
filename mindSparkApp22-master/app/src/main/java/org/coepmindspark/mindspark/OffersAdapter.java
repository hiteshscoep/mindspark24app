package org.coepmindspark.mindspark;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class OffersAdapter  extends RecyclerView.Adapter<OffersAdapter.MyViewHolder>{
    Context context ;
    List<org.coepmindspark.mindspark.OffersModel> offersModelList;

    public OffersAdapter(Context context, List<org.coepmindspark.mindspark.OffersModel> offersModelList) {
        this.context = context;
        this.offersModelList = offersModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offers_item,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(offersModelList.get(position).getTitle());
        holder.des.setText(offersModelList.get(position).getDes());
        String url = offersModelList.get(position).getUrl();
        Glide.with(context).load(offersModelList.get(position).getImg()).transform(new CenterCrop(), new RoundedCorners(25)).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.img);
        holder.avail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, des, avail;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.offer_title);
            img = itemView.findViewById(R.id.img_offer);
            des = itemView.findViewById(R.id.offer_des);
            avail = itemView.findViewById(R.id.avail_offer);

        }
    }
}
