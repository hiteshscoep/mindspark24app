package org.coepmindspark.mindspark.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import org.coepmindspark.mindspark.OffersAdapter;
import org.coepmindspark.mindspark.OffersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import org.coepmindspark.mindspark.R;

public class ModuleFragment extends Fragment {

    public ModuleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_module, container, false);
        FirebaseDatabase database =  FirebaseDatabase.getInstance("https://mindspark22-default-rtdb.asia-southeast1.firebasedatabase.app");
        RecyclerView rv = root.findViewById(R.id.offersrv);
        View progress = root.findViewById(R.id.offers_animationView);
        ImageView img = root.findViewById(R.id.empty_offers);
        ArrayList<OffersModel> offersList= new ArrayList<>();
        OffersAdapter offersAdapter = new OffersAdapter(getContext(),offersList);


        database.getReference().child("Offers").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offersList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OffersModel offersModel = dataSnapshot.getValue(OffersModel.class);
                    offersList.add(offersModel);

                }

                if(offersList.size()==0){
                    img.setVisibility(View.VISIBLE);
                    img.setOnClickListener(view -> Toast.makeText(getContext(), "OFFERS COMING SOON!", Toast.LENGTH_SHORT).show());
                    Glide.with(requireContext()).load("https://cdn3d.iconscout.com/3d/premium/thumb/empty-box-5342761-4468833.png").into(img);
                }else{
                    img.setVisibility(View.GONE);
                }
                rv.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                offersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Error Occurred: "+ error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(offersAdapter);
        return root;
    }
}