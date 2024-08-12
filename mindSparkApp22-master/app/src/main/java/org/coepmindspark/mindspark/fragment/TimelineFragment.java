package org.coepmindspark.mindspark.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.coepmindspark.mindspark.Events;
import org.coepmindspark.mindspark.R;
import org.coepmindspark.mindspark.TimelineAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimelineFragment extends Fragment {

    public TimelineFragment() {

    }
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://mindspark22-default-rtdb.asia-southeast1.firebasedatabase.app");
    RecyclerView tLRecyclerview;
    TimelineAdapter adapter;
    List<Events> tlItemList;
    View loading_hr;
    EditText searchInput;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);


        tLRecyclerview = root.findViewById(R.id.timeline_recyclerview);
        searchInput = root.findViewById(R.id.search_input);
        loading_hr = root.findViewById(R.id.tl_animationView);
        tlItemList = new ArrayList<>();
        database.getReference().child("Events").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tlItemList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Events tlItem = dataSnapshot.getValue(Events.class);
                    tlItemList.add(tlItem);
                }
                adapter.notifyDataSetChanged();
                tLRecyclerview.setVisibility(View.VISIBLE);
                loading_hr.setVisibility(View.GONE);
                Date today = new Date();
                Timestamp ts1 = new Timestamp(today.getTime());
                long tsTime1 = ts1.getTime();
                int i;
                for(i=0; i<tlItemList.size(); i++){
                    if(tlItemList.get(i).getTimestamp()>tsTime1){
                        break;
                    }
                }
                tLRecyclerview.scrollToPosition(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        adapter = new TimelineAdapter(getActivity(), tlItemList);
        tLRecyclerview.setAdapter(adapter);
        tLRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        return root;
    }
}