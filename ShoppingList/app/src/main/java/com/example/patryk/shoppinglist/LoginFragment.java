package com.example.patryk.shoppinglist;

        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

public class LoginFragment extends Fragment {

    Runnable r = new Runnable() {

        @Override
        public void run() {
            startActivity(new Intent(getActivity(), ShoppingListActivity.class));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Handler h = new Handler();
        h.postDelayed(r, 3000);

        return view;
    }
}
