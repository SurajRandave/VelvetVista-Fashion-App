package com.example.clothstoreapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.clothstoreapp.R;
import com.example.clothstoreapp.models.CartItem;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> cartItems;
    private LayoutInflater inflater;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cart_item_layout, parent, false);
        }

        // Get the CartItem for the current position
        CartItem cartItem = cartItems.get(position);

        // Find views
        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productTitle = convertView.findViewById(R.id.productTitle);
        TextView productPrice = convertView.findViewById(R.id.productPrice);

        // Set values for views
        productTitle.setText(cartItem.getTitle());
        productPrice.setText("Price: $" + cartItem.getPrice());

        // Load product image using Glide (assuming the CartItem has an imageUrl)
        Glide.with(context)
                .load(cartItem.getImageUrl())
                .into(productImage);

        return convertView;
    }
}
