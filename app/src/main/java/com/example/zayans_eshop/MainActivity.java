package com.example.zayans_eshop;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

import com.example.zayans_eshop.data.Product;
import com.example.zayans_eshop.data.UserAccount;
import com.example.zayans_eshop.ui.account__fragment;
import com.example.zayans_eshop.ui.cart__fragment;
import com.example.zayans_eshop.ui.home__fragment;
import com.example.zayans_eshop.ui.messages__fragment;
import com.example.zayans_eshop.ui.signed__in__account__fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // The universal ArrayList<Product> used for storing products as per categories
    public static ArrayList<Product> products = new ArrayList<>();
    public static UserAccount userAccount;
    public static boolean justLoggedFlag = false;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new home__fragment();
                            break;
                        case R.id.message:
                            selectedFragment = new messages__fragment();
                            break;
                        case R.id.cart:
                            selectedFragment = new cart__fragment();
                            break;
                        case R.id.account:
                            if (userAccount.getUserName() == null) {
                                selectedFragment = new account__fragment();
                            } else {
                                selectedFragment = new signed__in__account__fragment();
                            }
                            break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle(Html.fromHtml("<font color='#2399DD'>ayan's Megashop</font>"));


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new home__fragment()).commit();

        userAccount = new UserAccount(null, null, null, null, null);

        SharedPreferences userAccountPrefs = getSharedPreferences("userAccount", MODE_PRIVATE);
        userAccount.setUserName(userAccountPrefs.getString("userName", null));
        userAccount.setUserPhone(userAccountPrefs.getString("userPhone", null));
        userAccount.setUserEmail(userAccountPrefs.getString("userEmail", null));
        userAccount.setUserLocation(userAccountPrefs.getString("userLocation", null));
        userAccount.setUniqId(userAccountPrefs.getString("uniqId", null));

        if (justLoggedFlag) {
            Log.i("TEST", "restarted with flag");
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new signed__in__account__fragment()).commit();
        } else {
            Log.i("TEST", "restarted without flag");
        }
    }
}
