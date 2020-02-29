package net.cit368.reminderapp;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_CODE = 1, REGISTER_CODE = 2;
    private FirebaseAuth auth;
    public FirebaseUser user;

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

    @Override
    public void onStart(){
        super.onStart();
        user = auth.getCurrentUser();
        //the user can bypass sign in if they are already signed in
        if(user != null){
            //TODO redirect to tasks activity
        }
    }

    public void createSigninIntent(){
        List<AuthUI.IdpConfig> signinMethods = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(signinMethods).build(), SIGNIN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNIN_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                //TODO redirect to tasks page
                System.out.println("======================LOG IN SUCCESSFUL======================");
            } else {
                Toast.makeText(MainActivity.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                System.out.println("======================LOG IN FAILURE======================");
            }
        }else if(requestCode == REGISTER_CODE){
            Bundle extras = data.getExtras();
            if(resultCode == RESULT_OK){
                auth.createUserWithEmailAndPassword(extras.getString("username"), extras.getString("password")).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            System.out.println("======================user created successfully======================");
                            user = auth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                            //TODO redirect to tasks activity
                        }else{
                            System.out.println("======================create account failed======================");
                            Toast.makeText(MainActivity.this, "Could not create account", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("LOGGED OUT");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
    }

}
