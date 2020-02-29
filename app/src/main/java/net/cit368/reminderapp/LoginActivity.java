package net.cit368.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Register Button onClick - Opens register view
        final Button registerBtn = findViewById(R.id.loginRegisterBtn);
        final Button loginBtn = findViewById(R.id.loginLoginBtn);

        //Register button onClick
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        //Login view's "Login" button onClick
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });



    }

    //Register button onClick
    private void openRegisterActivity() {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    //TODO
    //Login view's "Login" button onClick
    private void handleLogin() {

        //Change activity to main activity
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }


}
