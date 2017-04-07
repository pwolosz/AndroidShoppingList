package com.example.patryk.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.patryk.shoppinglist.models.ShoppingListItem;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingViewHolder> {
    private List<ShoppingListItem> list;
    private Context mContext;
    private FragmentManager fragmentManager;
    private ShoppingListDbAdapter shoppingListDbAdapter;

    public ShoppingAdapter(List<ShoppingListItem> list, Context context, FragmentManager fragmentManager, ShoppingListDbAdapter shoppingListDbAdapter) {
        this.list = list;
        this.mContext = context;
        this.fragmentManager = fragmentManager;
        this.shoppingListDbAdapter = shoppingListDbAdapter;
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_shopping_item, null);
        ShoppingViewHolder shoppingViewHolder = new ShoppingViewHolder(view);
        return shoppingViewHolder;
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, final int position) {
        final ShoppingListItem shoppingItem = list.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        holder.nameView.setText(shoppingItem.getName());
        holder.descriptionView.setText(shoppingItem.getDescription());
        setItemImage(holder, shoppingItem);

        holder.nameView.setTextColor(Color.parseColor(sharedPreferences.getString("nameColor","#000000")));
        holder.descriptionView.setTextColor(Color.parseColor(sharedPreferences.getString("descriptionColor","#000000")));


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                (new ShoppingListItemEditFragment(shoppingItem)).show(fragmentManager, "fragmentManager");
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteShoppingListItem(shoppingItem.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    private void deleteShoppingListItem(int id, int position) {
        if(shoppingListDbAdapter.deleteShoppingListItem(id))
        {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        }
    }

    private void setItemImage(ShoppingViewHolder holder, ShoppingListItem shoppingListItem) {
        String imageUrl = shoppingListItem.getImageUrl();

        if(!imageUrl.equals("")) {
            Picasso.with(mContext).load(imageUrl).into(holder.imageView);
        }
    }
}
