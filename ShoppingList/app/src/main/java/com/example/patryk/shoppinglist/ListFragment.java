package com.example.patryk.shoppinglist;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.patryk.shoppinglist.models.ShoppingListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment implements ShoppingListItemEditFragment.NoticeDialogListener {
    private RecyclerView recyclerView;
    private ArrayList<ShoppingListItem> shoppingListItems;
    private ShoppingListDbAdapter shoppingListDbAdapter;
    private ShoppingAdapter shoppingAdapter;

    private final String defaultImageUrl = "https://cdn4.iconfinder.com/data/icons/devine_icons/Black/PNG/Folder%20and%20Places/Stack.png";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.shopping_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        shoppingListDbAdapter = new ShoppingListDbAdapter(getContext());
        shoppingListDbAdapter = shoppingListDbAdapter.getDbContext();
        shoppingListItems = (ArrayList<ShoppingListItem>)shoppingListDbAdapter.getShoppingList();
        shoppingAdapter = new ShoppingAdapter(shoppingListItems, getActivity(),getFragmentManager(),shoppingListDbAdapter);
        recyclerView.setAdapter(shoppingAdapter);

        view.findViewById(R.id.add_item_button).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                (new ShoppingListItemEditFragment()).show(getFragmentManager(), "fragmentManager");
            }
        });

        refresh();

        return view;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        String urlText = ((EditText)dialog.getDialog().findViewById(R.id.url_edit)).getText().toString();

        ShoppingListItem shoppingListItem = new ShoppingListItem(((EditText)dialog.getDialog().findViewById(R.id.description_edit)).getText().toString(),
                ((EditText)dialog.getDialog().findViewById(R.id.name_edit)).getText().toString(),
                ((ShoppingListItemEditFragment)dialog).getItemId(),
                (urlText.isEmpty()) ? defaultImageUrl : urlText);

        saveShoppingListItem(shoppingListItem);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDestroy() {
        shoppingListDbAdapter.closeDbContext();
        super.onDestroy();
    }

    private void saveShoppingListItem(ShoppingListItem shoppingListItem) {
        if(shoppingListItem.getId()>0) {
            if(shoppingListDbAdapter.updateShoppingListItem(shoppingListItem)) {
                for(int i = 0; i < shoppingListItems.size(); i++) {
                    if(shoppingListItems.get(i).getId() == shoppingListItem.getId()) {
                        shoppingListItems.set(i, shoppingListItem);
                        recyclerView.getAdapter().notifyItemChanged(i);
                        break;
                    }
                }
            }
        } else {
            int id = shoppingListDbAdapter.insertShoppingListItem(shoppingListItem.getName(), shoppingListItem.getDescription(), shoppingListItem.getImageUrl());
            if(id > 0) {
                shoppingListItem.setId(id);
                shoppingListItems.add(shoppingListItem);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    public void refresh() {
        shoppingAdapter.notifyDataSetChanged();
    }
}
