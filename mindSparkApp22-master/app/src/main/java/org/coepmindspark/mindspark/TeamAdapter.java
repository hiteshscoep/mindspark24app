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

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder>{
    Context context ;
    List<TeamModel> teamModelList;

    public TeamAdapter(Context context, List<TeamModel> teamModelList) {
        this.context = context;
        this.teamModelList = teamModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.team_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(teamModelList.get(position).getName());
        holder.position.setText(teamModelList.get(position).getPosition());
        String mail = "mailto:"+teamModelList.get(position).getMail();
        String lin = teamModelList.get(position).getLinked_in();
        String insta = teamModelList.get(position).getInsta();
        Glide.with(context).load(teamModelList.get(position).getImg()).transform(new CenterCrop(), new RoundedCorners(25)).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.img);
        holder.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mail));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    context.startActivity(intent);
                }
            }
        });
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lin));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    context.startActivity(intent);
                }
            }
        });
        holder.insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(insta));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, position;
        ImageView img, mail, lin, insta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            position = itemView.findViewById(R.id.team_position);
            img = itemView.findViewById(R.id.team_img);
            mail = itemView.findViewById(R.id.team_mail);
            lin = itemView.findViewById(R.id.team_lin);
            insta = itemView.findViewById(R.id.team_insta);
        }
    }
}
