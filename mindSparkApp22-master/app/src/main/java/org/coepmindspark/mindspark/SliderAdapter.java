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

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private final List<org.coepmindspark.mindspark.Slider> sliderList;
    private final Context context;

    public SliderAdapter(Context context, List<org.coepmindspark.mindspark.Slider> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item, parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Glide.with(context).load(sliderList.get(position).getBg()).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.imageView);
        holder.title.setText(sliderList.get(position).getTitle());
        String url = sliderList.get(position).getUrl();
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
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
        return sliderList.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;
        private final TextView title;
        public SliderViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.slider_bg);
            title = view.findViewById(R.id.slider_title);
        }
    }
}
