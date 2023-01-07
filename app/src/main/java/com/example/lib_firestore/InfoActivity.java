package com.example.lib_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class InfoActivity extends Fragment {


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

    public InfoActivity(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        name = view.findViewById(R.id.name);
        mail = view.findViewById(R.id.email);
        logout = view.findViewById(R.id.logout_button);
        pic = view.findViewById(R.id.profilepic);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc= GoogleSignIn.getClient(getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
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
                            Toast.makeText(getContext(), "User Added ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Errrorr!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });

        return view;
    }


    private void SignOut() {

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                startActivity(new Intent(getContext(), StudentLoginActivity.class));
            }
        });

    }

}