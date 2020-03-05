package net.cit368.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Hunter Dubbs
 * @version 3/4/2020
 * This activity allows the user to sign in to their account or create a new one.
 * It is the entry point of the app.
 */
public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_CODE = 1, REGISTER_CODE = 2;
    private FirebaseAuth auth;
    private FirebaseUser user;

    /**
     * Setup buttons and button click handlers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        Button btnSignin = findViewById(R.id.btn_signin);
        Button btnRegister = findViewById(R.id.btn_register);
        btnSignin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createSigninIntent();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(new Intent(MainActivity.this, RegisterActivity.class), REGISTER_CODE);
            }
        });

    }

    /**
     * When the activity starts, it should check if a user is already logged in.
     */
    @Override
    public void onStart(){
        super.onStart();
        user = auth.getCurrentUser();
        //the user can bypass sign in if they are already loggged in
        if(user != null){
            startActivity(new Intent(MainActivity.this, TaskActivity.class));
        }
    }

    /**
     * Utilizes the Firebase AuthUI built-in class to handle existing user login
     */
    public void createSigninIntent(){
        List<AuthUI.IdpConfig> signinMethods = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(signinMethods).build(), SIGNIN_CODE);
    }

    /**
     * This method handles data returned from activities called to handle sign in and register functions.
     * @param requestCode the code corresponding to what activity is returning the result
     * @param resultCode the result of the activity
     * @param data the data returned by the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNIN_CODE) { //handle sign in results
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
                Log.i("Auth", "Log in successful");
            } else {
                Toast.makeText(MainActivity.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                Log.e("Auth", "Log in failure");
            }
        }else if(requestCode == REGISTER_CODE){ //handle register results
            Bundle extras = data.getExtras();
            if(resultCode == RESULT_OK){
                auth.createUserWithEmailAndPassword(extras.getString("username"), extras.getString("password")).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Auth", "User created");
                            user = auth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, TaskActivity.class));
                        }else{
                            Log.e("Auth", "User creation failed");
                            Toast.makeText(MainActivity.this, "Could not create account", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

}
