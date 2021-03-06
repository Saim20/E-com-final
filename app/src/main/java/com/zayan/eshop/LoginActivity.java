package com.zayan.eshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zayan.eshop.data.BackgroundLoginEngine;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

            private String userName;
            private String userPass;
            private BackgroundLoginEngine loginEngine;
            private TextView tv;
            private Toast warningToast;
            private View toast;
            private boolean cartFlag;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.loginscreen);

                final TextInputEditText name = findViewById(R.id.username);
                final TextInputEditText pass = findViewById(R.id.userpass);
                final Button submit = findViewById(R.id.login);

                LayoutInflater inflater = getLayoutInflater();
                toast = inflater.inflate(R.layout.toast_warning, (ViewGroup) findViewById(R.id.toast));
                tv = toast.findViewById(R.id.toast_text);

                warningToast = new Toast(this);
                warningToast.setDuration(Toast.LENGTH_SHORT);
                warningToast.setView(toast);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userName = name.getText().toString();
                        userPass = pass.getText().toString();


                        if (userName.length() < 4) {
                            if (userName.length() == 0) {
                                tv.setText("Enter name");
                                warningToast.show();
                            } else {
                                tv.setText("Invalid username");
                                warningToast.show();
                            }
                        } else if (userPass.length() < 6) {
                            if (userPass.length() == 0) {
                                tv.setText("Enter password");
                                warningToast.show();
                            } else {
                                tv.setText("Invalid password");
                                warningToast.show();
                            }
                        } else {
                            submit.setEnabled(false);
                            submit.setAlpha(.5f);
                            loginEngine = new BackgroundLoginEngine(LoginActivity.this, submit, warningToast, tv);
                            loginEngine.execute(userName, userPass);
                        }
                    }
                });

                cartFlag = getIntent().getBooleanExtra("cartFlag", false);
            }

            @Override
            public void onBackPressed() {
                if (loginEngine != null)
                    loginEngine.cancel(true);
                super.onBackPressed();
                finish();
    }
}
