package com.example.clothstoreapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clothstoreapp.R;

import java.util.List;

public class slider_imageAdapter extends RecyclerView.Adapter<slider_imageAdapter.ViewHolder> {

    private Context context;
    private List<String> imageUrls;

    // Constructor accepting context and list of image URLs
    public slider_imageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the slider image layout
        View view = LayoutInflater.from(context).inflate(R.layout.slider_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the image URL at the current position
        String imageUrl = imageUrls.get(position);

        // Use Glide to load the image from the URL into the ImageView
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.paceholder) // Optional: Set a placeholder image
                .error(R.drawable.error_image)      // Optional: Set an error image if loading fails
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // Return the size of the list of image URLs
        return imageUrls.size();
    }

    // ViewHolder to hold the image view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the ImageView
            imageView = itemView.findViewById(R.id.sliderImage);
        }
    }
}
