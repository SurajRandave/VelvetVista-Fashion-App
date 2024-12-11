package com.example.clothstoreapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.clothstoreapp.R;
import com.example.clothstoreapp.models.item;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    private Context context;
    private List<item> productList;

    // Constructor
    public productAdapter(Context context, List<item> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the product item
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item currentProduct = productList.get(position);

        // Set data for product image, title, and price
        Glide.with(context)
                .load(currentProduct.getPicUrls()[0])  // Assuming first image is the main image
                .placeholder(R.drawable.paceholder)    // Set a placeholder image
                .into(holder.productImage);

        holder.productTitle.setText(currentProduct.getTitle());
        holder.productPrice.setText("$" + currentProduct.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Reference to the views in the item layout
            productTitle = itemView.findViewById(R.id.titleTxt);   // Ensure these IDs match the XML
              // Ensure these IDs match the XML
              // Ensure these IDs match the XML
        }
    }
}
