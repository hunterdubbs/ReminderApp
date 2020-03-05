package net.cit368.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.InternalTokenProvider;

/**
 * @author Hunter Dubbs
 * @version 2/29/2020
 * Allows you to change the password of the current user
 */
public class AccountActivity extends AppCompatActivity {

    String newPassword, confirmPassword;

    /**
     * setup form fields and button handlers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final EditText txtNewPassword = findViewById(R.id.txtNewPassword);
        final EditText txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        Button btnUpdatePassword = findViewById(R.id.btnUpdate);
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = txtNewPassword.getText().toString();
                confirmPassword = txtConfirmPassword.getText().toString();
                if(validate()) updatePassword();
            }
        });
    }

    /**
     * Ensures that all fields are valid
     * @return true if validation successful
     */
    private boolean validate(){
        return validateNewPassword() && validateConfirmPassword();
    }

    /**
     * Ensures that the new password field is filled with a password between 8 and 40 characters
     * @return true if the new password fields validates successfully
     */
    private boolean validateNewPassword(){
        if(newPassword != null && (newPassword.length() > 8 || newPassword.length() < 40)){
            return true;
        }
        Toast.makeText(AccountActivity.this, "Invalid New Password", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Ensures that the confirm password field matches the other password field
     * @return true if the password fields match
     */
    private boolean validateConfirmPassword(){
        if(newPassword.equals(confirmPassword)){
            return true;
        }
        Toast.makeText(AccountActivity.this, "New Password do not Match", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Attempts to update the users password with the new one; may fail if it has been
     * too long since they have authenticated
     */
    private void updatePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AccountActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AccountActivity.this, TaskActivity.class));
                }else{
                    Toast.makeText(AccountActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
