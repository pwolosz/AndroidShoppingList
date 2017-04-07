package com.example.patryk.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.patryk.shoppinglist.models.ShoppingListItem;


public class ShoppingListItemEditFragment extends DialogFragment {
    private String description;
    private String name;
    private String imageUrl;
    private int itemId = 0;
    NoticeDialogListener mListener;

    public ShoppingListItemEditFragment() {

    }

    public ShoppingListItemEditFragment(ShoppingListItem shoppingListItem) {
        this.description = shoppingListItem.getDescription();
        this.name = shoppingListItem.getName();
        this.imageUrl = shoppingListItem.getImageUrl();
        this.itemId = shoppingListItem.getId();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_shopping_list_item,null);

        builder.setView(view)
                .setPositiveButton( "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ShoppingListItemEditFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShoppingListItemEditFragment.this.getDialog().cancel();
                    }
                });
        ((EditText)view.findViewById(R.id.name_edit)).setText(name);
        ((EditText)view.findViewById(R.id.description_edit)).setText(description);
        ((EditText)view.findViewById(R.id.url_edit)).setText(imageUrl);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (NoticeDialogListener) getFragmentManager().findFragmentById(R.id.main_container);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public int getItemId() {
        return itemId;
    }
}
