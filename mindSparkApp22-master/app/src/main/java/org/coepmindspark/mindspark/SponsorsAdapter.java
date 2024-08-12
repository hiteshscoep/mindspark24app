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

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsAdapter.MyViewHolder>{
    Context context;
    List<org.coepmindspark.mindspark.SponsorsModel> sponsorsModelList;

    public SponsorsAdapter(Context context, List<org.coepmindspark.mindspark.SponsorsModel> sponsorsModelList) {
        this.context = context;
        this.sponsorsModelList = sponsorsModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sponsors_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(sponsorsModelList.get(position).getTitle());
        Glide.with(context).load(sponsorsModelList.get(position).getImg()).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return sponsorsModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.sponsor_text);
            img = itemView.findViewById(R.id.sponsor_img);
        }
    }
}
