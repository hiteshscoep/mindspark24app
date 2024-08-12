package org.coepmindspark.mindspark;

import android.content.Context;
import android.content.Intent;
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

import org.coepmindspark.mindspark.R;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder>{
    Context context;
    List<org.coepmindspark.mindspark.BlogModel> blogModelList;

    public BlogAdapter(Context context, List<org.coepmindspark.mindspark.BlogModel> blogModelList) {
        this.context = context;
        this.blogModelList = blogModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String url = blogModelList.get(position).getUrl();
        holder.root.setOnClickListener(view -> {
            Intent intent = new Intent(context, WebView.class);
            intent.putExtra("site",url);
            context.startActivity(intent);
        });
        holder.title.setText(blogModelList.get(position).getTitle());
        holder.des.setText(blogModelList.get(position).getDes());
        Glide.with(context).load(blogModelList.get(position).img).transform(new CenterCrop(), new RoundedCorners(25)).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return blogModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,des;
        ImageView img;
        View root;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.blog_title);
            des = itemView.findViewById(R.id.blog_des);
            img = itemView.findViewById(R.id.blog_img);
            root = itemView.findViewById(R.id.blog_root);
        }
    }
}
