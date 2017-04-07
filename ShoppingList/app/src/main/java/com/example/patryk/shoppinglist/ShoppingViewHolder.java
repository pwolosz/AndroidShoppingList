package com.example.patryk.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ShoppingViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    TextView descriptionView;
    ImageView imageView;
    Button deleteButton;

    public ShoppingViewHolder(View itemView) {
        super(itemView);
        this.nameView = (TextView) itemView.findViewById(R.id.item_name);
        this.descriptionView = (TextView) itemView.findViewById(R.id.item_description);
        this.imageView = (ImageView) itemView.findViewById(R.id.image);
        this.deleteButton = (Button) itemView.findViewById(R.id.item_delete_button);
    }
}