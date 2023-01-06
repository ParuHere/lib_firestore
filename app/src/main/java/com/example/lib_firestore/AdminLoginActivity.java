package com.example.lib_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lib_firestore.Admin.AdminActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    Button login_button,Stbutton;
    EditText email, pass;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference adminref = db.collection("admin");
    private DocumentReference noteref = db.collection("Admin").document("A1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().hide();

        login_button=findViewById(R.id.btnLogin);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
        Stbutton = findViewById(R.id.st);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc= GoogleSignIn.getClient(this, gso);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

        Stbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentLoginActivity.class));
            }
        });
    }
    public void login(){
        noteref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String username = documentSnapshot.getString(USERNAME);
                            String password = documentSnapshot.getString(PASSWORD);

                            String inputusername = email.getText().toString();
                            String inputpass = pass.getText().toString();

                            if(Objects.equals(username, inputusername) && Objects.equals(password, inputpass)){
                                Toast.makeText(AdminLoginActivity.this, "Logged In as Admin", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent (AdminLoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }

                        }else {
                            Toast.makeText(AdminLoginActivity.this, " No data ", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminLoginActivity.this, "Couldnt Get data", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}