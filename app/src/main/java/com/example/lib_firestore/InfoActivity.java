package com.example.lib_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {


    TextView name,mail;
    Button logout;
    ImageView pic;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private static final String USER_ID = "user_id";
    private static final String DISPLAY_NAME = "name";
    private static final String EMAIL = "email";
    private static final String PIC = "photo_url";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().hide();

        name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        logout = findViewById(R.id.logout_button);
        pic = findViewById(R.id.profilepic);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc= GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            String Userid = account.getId();
            String Name = account.getDisplayName();
            String Email = account.getEmail();
            String PicUrl = String.valueOf(account.getPhotoUrl());


            name.setText(Name);
            mail.setText(Email);
            Picasso.get().load(PicUrl).into(pic);

            Map<String, Object> user = new HashMap<>();
            user.put(USER_ID,Userid);
            user.put(DISPLAY_NAME,Name);
            user.put(EMAIL,Email);
            user.put(PIC,PicUrl);

            db.collection("Users").document(Userid).set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(InfoActivity.this, "User Added ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InfoActivity.this, "Errrorr!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

    }



    private void SignOut() {

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), StudentLoginActivity.class));
            }
        });

    }

}