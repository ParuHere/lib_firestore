package com.example.lib_firestore.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lib_firestore.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

    EditText bookname,author,edition,isbn,copies;
    Button addbook;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private static final String BOOK_NAME = "bookname";
    private static final String AUTHOR = "author";
    private static final String EDITION = "edition";
    private static final String ISBN = "isbn";
    private static final String COPIES = "copies";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        getSupportActionBar().hide();

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc= GoogleSignIn.getClient(this, gso);

        bookname = findViewById(R.id.bookname);
        author = findViewById(R.id.author);
        edition=findViewById(R.id.edition);
        isbn = findViewById(R.id.isbn);
        copies = findViewById(R.id.copies);

        addbook = findViewById(R.id.addbookbutton);
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
                clear();
                Toast.makeText(AddBookActivity.this, "Book Added ",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void addBook() {

        String Bookname = bookname.getText().toString();
        String Author = author.getText().toString();
        String Edition = edition.getText().toString();
        String Isbn = isbn.getText().toString();
        String Copies = copies.getText().toString();

        Map<String, Object> book = new HashMap<>();
        book.put(BOOK_NAME,Bookname);
        book.put(AUTHOR,Author);
        book.put(EDITION,Edition);
        book.put(ISBN,Isbn);
        book.put(COPIES,Copies);

        db.collection("Books").document(Isbn).set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddBookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddBookActivity.this, "Errrrorrr!!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void clear() {

        bookname.setText("");
        author.setText("");
        edition.setText("");
        isbn.setText("");
        copies.setText("");
    }
}