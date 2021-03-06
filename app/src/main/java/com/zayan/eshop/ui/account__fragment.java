package com.zayan.eshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zayan.eshop.LoginActivity;
import com.zayan.eshop.MainActivity;
import com.zayan.eshop.R;
import com.zayan.eshop.RegisterActivity;

public class account__fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.account_fragment, container, false);
        if (MainActivity.userAccount.getUserName() != null) {
            MainActivity.bottomNavigationView.getMenu().getItem(3).setChecked(true);
            if (MainActivity.signed__in__account__fragment == null)
                MainActivity.signed__in__account__fragment = new signed__in__account__fragment();
            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, MainActivity.signed__in__account__fragment).commit();
        }
            TextView button = root.findViewById(R.id.register);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }

            });
            Button button2 = root.findViewById(R.id.loginbutton);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    requireActivity().overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
                }

            });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.userAccount.getUserName() != null) {
            MainActivity.bottomNavigationView.getMenu().getItem(3).setChecked(true);
            if (MainActivity.signed__in__account__fragment == null)
                MainActivity.signed__in__account__fragment = new signed__in__account__fragment();
            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, MainActivity.signed__in__account__fragment).commit();
        }
    }
}
