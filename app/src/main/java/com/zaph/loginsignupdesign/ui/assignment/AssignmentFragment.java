package com.zaph.loginsignupdesign.ui.assignment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaph.loginsignupdesign.NewTaskActivity;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.ui.DoesAdapter;
import com.zaph.loginsignupdesign.ui.MyDoes;

import java.util.ArrayList;


public class AssignmentFragment extends Fragment {

    TextView titledoes, subtitledoes, enddoes;
    ImageView btnAddNew;
    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;
    TextView connectDb;
    LottieAnimationView notesplash;
    ShimmerFrameLayout shimmerFrameLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        titledoes = root.findViewById(R.id.titledoes);
        subtitledoes = root.findViewById(R.id.subtitledoes);
        enddoes = root.findViewById(R.id.enddoes);
        btnAddNew = root.findViewById(R.id.btnAddNew);
        notesplash = root.findViewById(R.id.notesplash);
        connectDb = root.findViewById(R.id.connectdb);
        shimmerFrameLayout=root.findViewById(R.id.shimmerLayout);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewTaskActivity.class);
                startActivity(intent);
            }
        });

        //import Font
        Typeface MLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/regular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/light.ttf");

        //customize font here
        titledoes.setTypeface(MMedium);
        subtitledoes.setTypeface(MLight);
        enddoes.setTypeface(MMedium);

        //working with data
        ourdoes = root.findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<MyDoes>();
        shimmerFrameLayout.startShimmer();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("StuFi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                //set code to retrieve data
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
                    list.add(p);

                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                ourdoes.setVisibility(View.VISIBLE);
                doesAdapter = new DoesAdapter(getActivity(), list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
                checkIfEmpty();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ourdoes.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                checkIfEmpty();
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

            }
        });

        return root;


    }

    private void checkIfEmpty() {

        if (list.size() > 0) {
            ourdoes.setVisibility(View.VISIBLE);
            notesplash.setVisibility(View.GONE);
            enddoes.setVisibility(View.GONE);
        }
        else {
            ourdoes.setVisibility(View.GONE);
            notesplash.setVisibility(View.VISIBLE);
            enddoes.setVisibility(View.VISIBLE);
        }

    }

}