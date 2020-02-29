package net.cit368.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Hunter Dubbs
 * @version 2/28/2020
 * This class allows the user to input a username and password and validates them.
 */
public class RegisterActivity extends AppCompatActivity {

    private String username, password;

    /**
     * Configure form fields and create submit button listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText txtUsername = findViewById(R.id.txtUsername);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                if(validate()) finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    /**
     * Validates the user email and password
     * @return true if both fields validate successfully
     */
    public boolean validate(){
        return validateUsername() && validatePassword();
    }

    /**
     * Validates the user email to ensure it is not empty and is a valid email address
     * @return true if the email field validates successfully
     */
    public boolean validateUsername(){
        if(username != null && username != "" && Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            return true;
        }
        Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Validates the password field to ensure it is between 8 and 40 characters
     * @return true if the email field validates successfully
     */
    public boolean validatePassword(){
        if(password != null && (password.length() > 8 || password.length() < 40)){
            return true;
        }
        Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Returns the validated fields back to MainActivity to actually generate the account.
     */
    @Override
    public void finish(){
        Intent result = new Intent();
        result.putExtra("username", username);
        result.putExtra("password", password);
        setResult(RESULT_OK, result);
        super.finish();
    }

    /**
     * Login button onClick
     */
    private void openLoginActivity() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
