package org.coepmindspark.mindspark;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    List<GalleryModel> list;
    Context context;
    Dialog dialog;
    int width;
    public GalleryAdapter(List<GalleryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dialog = new Dialog(context);
        width= context.getResources().getDisplayMetrics().widthPixels;
        Glide.with(context).load(list.get(position).imgUrl1).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery1);
        Glide.with(context).load(list.get(position).imgUrl2).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery2);
        Glide.with(context).load(list.get(position).imgUrl3).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery3);
        Glide.with(context).load(list.get(position).imgUrl4).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery4);
        Glide.with(context).load(list.get(position).imgUrl5).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery5);
        Glide.with(context).load(list.get(position).imgUrl6).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(holder.gallery6);

        holder.gallery1.setOnClickListener(view -> openDialog(list.get(position).imgUrl1));
        holder.gallery2.setOnClickListener(view -> openDialog(list.get(position).imgUrl2));

        holder.gallery3.setOnClickListener(view -> openDialog(list.get(position).imgUrl3));
        holder.gallery4.setOnClickListener(view -> openDialog(list.get(position).imgUrl4));
        holder.gallery5.setOnClickListener(view -> openDialog(list.get(position).imgUrl5));
        holder.gallery6.setOnClickListener(view -> openDialog(list.get(position).imgUrl6));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView gallery1, gallery2,gallery3,gallery4,gallery5,gallery6;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gallery1 = itemView.findViewById(R.id.gallery1);
            gallery2 = itemView.findViewById(R.id.gallery2);
            gallery3 = itemView.findViewById(R.id.gallery3);
            gallery4 = itemView.findViewById(R.id.gallery4);
            gallery5 = itemView.findViewById(R.id.gallery5);
            gallery6 = itemView.findViewById(R.id.gallery6);
        }
    }

    private void openDialog(String img){

        dialog.setContentView(R.layout.gallery_dialog);
        ImageView imageView = dialog.findViewById(R.id.gallery_image);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width,2*width/3));
        imageView.requestLayout();
        Button close = dialog.findViewById(R.id.close_btn);

        View animation = dialog.findViewById(R.id.dialog_animationView);
        close.setOnClickListener(view -> dialog.dismiss());
        Button share = dialog.findViewById(R.id.share_dialog);
        close.getLayoutParams().width = (int) (0.45*width);
        share.getLayoutParams().width = (int) (0.45*width);
        share.setOnClickListener(view -> {
            animation.setVisibility(View.VISIBLE);
            Thread thread = new Thread(() -> {
                try {
                    URL url = null;
                    try {
                        url = new URL(img);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpURLConnection connection = null;
                    try {
                        assert url != null;
                        connection = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert connection != null;
                    connection.setDoInput(true);
                    try {
                        connection.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    InputStream input = null;
                    try {
                        input = connection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap imgBitmap = BitmapFactory.decodeStream(input);
                    Random rand = new Random();
                    int randNo = rand.nextInt(100000);
                    String imgBitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(),imgBitmap,"MSIMG:"+randNo,null);
                    Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                    shareIntent.setType("image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Watch this amazing photo from MindSpark'22 app.");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch this amazing photo from MindSpark'22 app.");
                    try {
                        context.startActivity(Intent.createChooser(shareIntent,"Share with"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Error: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    animation.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();

        });
        Glide.with(context).load(img).placeholder(R.drawable.placeholder_bg).error(R.drawable.fourzerofour).into(imageView);
        dialog.show();
    }
}
