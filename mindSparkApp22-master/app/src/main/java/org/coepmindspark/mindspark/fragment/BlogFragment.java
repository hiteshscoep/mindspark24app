package org.coepmindspark.mindspark.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.coepmindspark.mindspark.BlogAdapter;
import org.coepmindspark.mindspark.BlogModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

import org.coepmindspark.mindspark.R;


public class BlogFragment extends Fragment {

    public BlogFragment() {
        // Required empty public constructor
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_blog, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView rv = root.findViewById(R.id.blog_rv);
        View progress = root.findViewById(R.id.blog_animationView);
        ArrayList<BlogModel> blogModelList= new ArrayList<>();
        BlogAdapter blogAdapter = new BlogAdapter(getContext(),blogModelList);
        db.collection("blog")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BlogModel blogModel = document.toObject(BlogModel.class);
                            blogModelList.add(blogModel);
                            blogAdapter.notifyDataSetChanged();
                        }

                        rv.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);

                    }
                    else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(blogAdapter);

        return root;
    }
}