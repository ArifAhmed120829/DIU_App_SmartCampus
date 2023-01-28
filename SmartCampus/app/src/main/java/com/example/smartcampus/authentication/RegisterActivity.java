package com.example.smartcampus.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.LoginActivity;
import com.example.smartcampus.R;
import com.example.smartcampus.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import util.StudentUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_name,reg_email,reg_password;
    private Button register;
    private String name,email,pass;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference dbRef;
    private TextView openLog;
    //private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore firestore_database = FirebaseFirestore.getInstance();
    private CollectionReference firestore_collectionreference = firestore_database.collection("StudentUsers");

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){
            openMain();
        }
    }
    private void openMain(){
        startActivity(new Intent(this, UserActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        reg_name = findViewById(R.id.regName);
        reg_email = findViewById(R.id.regEmail);
        reg_password = findViewById(R.id.regPassword);
        register = findViewById(R.id.register_btn);
        openLog = findViewById(R.id.openLog);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();
                if(currentuser!= null){
                    //User already logged in
                }
                else{

                }

            }
        };
    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity2.class));
        finish();
    }

    private void validateData() {
        name = reg_name.getText().toString();
        email = reg_email.getText().toString();
        pass = reg_password.getText().toString();
        if(name.isEmpty()){
            reg_name.setError("Required");
            reg_name.requestFocus();
        } else if (email.isEmpty()){
            reg_email.setError("Required");
            reg_email.requestFocus();
        } else if(pass.isEmpty()){
            reg_password.setError("Required");
            reg_password.requestFocus();
        } else{
            createUser();
        }
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    uploadData();

                } else{
                    Toast.makeText(RegisterActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        dbRef = reference.child("users");
        String key = dbRef.push().getKey();

        HashMap<String, String> user = new HashMap<>();
        user.put("key",key);
        user.put("name",name);
        user.put("email",email);
        currentuser = auth.getCurrentUser();
        assert currentuser!=null;
        final String currentuserid = currentuser.getUid();
        HashMap<String,String> userobj = new HashMap<>();
        userobj.put("key",currentuserid);// ID
        userobj.put("username",name); //NAME
        //Adding users to Firestore
        firestore_collectionreference.add(userobj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(Objects.requireNonNull(task.getResult()).exists()){
                            String name = task.getResult().getString("username");//document
                            // if the user is registered succesfully
                            //then move to student_point_at_issue

                            //Getting use of global StudentUser
                            StudentUser studentUser = StudentUser.getInstance();
                            studentUser.setUserid(currentuserid);
                            studentUser.setUsername(name);

                            Intent i = new Intent(RegisterActivity.this,UserActivity.class);
                            i.putExtra("username",name);//field
                            i.putExtra("userid",currentuserid);//field
                            startActivity(i);
                            finish();
                        }
                        else{

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dbRef.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                    //openMain();
                } else{
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}