package com.example.smartcampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.model2.student_model;
import com.example.smartcampus.ui2.IssueRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import util.StudentUser;

public class ShowIssue extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Toolbar toolbar2;
    private StorageReference storageReference;
    private List<student_model> studentList;
    private RecyclerView recyclerView;
    private IssueRecyclerAdapter issueRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("Management");
    private TextView noPostsEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_issue);
        this.setTitle("List Of Issues");
        toolbar2 = findViewById(R.id.toolbar2);//actionBar
        setSupportActionBar(toolbar2);
        noPostsEntry = findViewById(R.id.list_of_issues);
        recyclerView = findViewById(R.id.recyclerView_issues);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //posts ArrayList
        studentList = new ArrayList<>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                if(user != null && firebaseAuth != null){
                    Toast.makeText(this, "Nothing added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "user is null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_signout:
                if(user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(ShowIssue.this,MainActivity.class));
                    Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                studentList.clear();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot students: task.getResult()){
                        student_model mod  = students.toObject(student_model.class);
                        studentList.add(mod);
                    }
                    issueRecyclerAdapter = new
                            IssueRecyclerAdapter(ShowIssue.this,studentList);
                    recyclerView.setAdapter(issueRecyclerAdapter);

                    issueRecyclerAdapter.notifyDataSetChanged();
                }
                else{
                    noPostsEntry.setVisibility(View.VISIBLE);
                    Toast.makeText(ShowIssue.this, "Hey bro there's no data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*
        collectionReference.whereEqualTo("userId", StudentUser.getInstance().getUserid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for (QueryDocumentSnapshot students: queryDocumentSnapshots){
                                student_model mod  = students.toObject(student_model.class);
                                studentList.add(mod);
                            }
                            //Recyclerview
                            issueRecyclerAdapter = new
                                    IssueRecyclerAdapter(list_of_issues.this,studentList);
                            recyclerView.setAdapter(issueRecyclerAdapter);

                            issueRecyclerAdapter.notifyDataSetChanged();

                        }
                        else{
                            noPostsEntry.setVisibility(View.VISIBLE);
                            Toast.makeText(list_of_issues.this, "Hey bro there's no data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Any failure
                        Toast.makeText(list_of_issues.this, "Opps! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
         */
    }
}