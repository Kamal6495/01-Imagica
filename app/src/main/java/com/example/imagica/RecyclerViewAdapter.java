package com.example.imagica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<Photo> photoList;
    private Context context;
    private int pos;

    RecyclerViewAdapter(List<Photo> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    void addPhotos(List<Photo> photos) {

        int lastCount = getItemCount();
        photoList.addAll(photos);


        Log.d(TAG, "addPhotos: " + getItemCount() + " " + photos.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_image, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pos = position;
        holder.textView.setText(photoList.get(position).getDescription());
        Glide.with(context).asBitmap()
                .load(photoList.get(position).getUrls().getRegular())
                .into(holder.imageView);
        String color = photoList.get(position).getColor();
        color = color.substring(1);
        holder.cardView.setCardBackgroundColor(Integer.parseInt(color, 16));

        Glide.with(context).asBitmap().load(photoList.get(position).getUrls().getRegular())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        saveImage(resource);
                    }
                });



    }

    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + "FILE_NAME_" + pos + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/Imagica");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);

    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

    void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CardView cardView;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.txtImage);
            cardView = (CardView) itemView.findViewById(R.id.parent);
        }
    }


}
