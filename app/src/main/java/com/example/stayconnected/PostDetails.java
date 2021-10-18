package com.example.stayconnected;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.stayconnected.Adapter.PostAdapter;
import com.example.stayconnected.Model.Posts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Posts> postsList;
    PostAdapter adapter;

    String postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        postsList=new ArrayList<>();
        adapter=new PostAdapter(this,postsList);

        postid=getIntent().getStringExtra("postid");

        recyclerView.setAdapter(adapter);


        readPosts();







    }

    private void readPosts() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Posts")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsList.clear();
                Posts posts=snapshot.getValue(Posts.class);

                postsList.add(posts);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(PostDetails.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}