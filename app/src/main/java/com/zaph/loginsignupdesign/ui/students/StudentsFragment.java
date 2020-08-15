package com.zaph.loginsignupdesign.ui.students;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.api.ApiClient;
import com.zaph.loginsignupdesign.firebase.FirebaseHelper;
import com.zaph.loginsignupdesign.models.Event;
import com.zaph.loginsignupdesign.models.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zaph.loginsignupdesign.ui.AddStudent;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;
import com.zaph.loginsignupdesign.ui.MainAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class StudentsFragment extends Fragment {

    private ArrayList<Event> eventList = new ArrayList();
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView studentEditor;
    private DatabaseHelperClass myDb;
    private TextView txtClose;
    private TextView studentName;
    private TextView studentId;
    private TextView studentPhone;
    private TextView studentGender;
    private TextView studentEmail;
    private TextView studentBranch;
    private TextView studentYear;

    private ImageView studentPopupCall;
    private ImageView studentPopupMessage;
    private ImageView studentPopupEmail;

    private LottieAnimationView noData;
    private TextView noStudents;

    private ShimmerFrameLayout shimmerFrameLayout;
    private Dialog myDialog;

    private FirebaseFirestore mFirestore;
    private static final String FIRE_LOG = "Fire_log";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_students, container, false);

        //getSupportActionBar().setTitle("Students");

        shimmerFrameLayout = root.findViewById(R.id.maineventshimmer);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        myDb = new DatabaseHelperClass(getActivity()); // dataBase constructor is calling here
        setUpRecycler(root);
        noData = root.findViewById(R.id.noStudents);
        noStudents = root.findViewById(R.id.textNoStudentsAdded);

        noData.setVisibility(View.GONE);
        noStudents.setVisibility(View.GONE);
        getEvents();
        checkIfEmpty();
        return root;
    }

    void setUpRecycler(View view) {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter = new MainAdapter(getActivity(), this, eventList);
        recyclerView.setAdapter(mainAdapter);

        fab = (FloatingActionButton) view.findViewById(R.id.add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivityForResult(intent, 101);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
               getEvents();
            }
        }else if (requestCode == 207) {
            if (resultCode == RESULT_OK) {
                getEvents();
            }
        }
    }


    public void showDetails(String name, String id, final String phone, String gender, final String email, String branch, String year) {
        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.custompopup);

        studentName = myDialog.findViewById(R.id.tv_student_name);
        studentName.setText(name);

        studentId = myDialog.findViewById(R.id.tv_student_id);
        studentId.setText(id);

        studentPhone = myDialog.findViewById(R.id.tv_student_phone);
        studentPhone.setText(phone);

        studentGender = myDialog.findViewById(R.id.tv_student_gender);
        studentGender.setText(gender);

        studentEmail = myDialog.findViewById(R.id.tv_student_email);
        studentEmail.setText(email);

        studentBranch = myDialog.findViewById(R.id.tv_student_branch);
        studentBranch.setText(branch);

        studentYear = myDialog.findViewById(R.id.tv_student_year);
        studentYear.setText(year);

        txtClose = myDialog.findViewById(R.id.custompopup_close);


        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

        studentPopupCall = myDialog.findViewById(R.id.student_popUp_call);
        studentPopupCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:" + extractNumber(phone);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });

        studentPopupMessage = myDialog.findViewById(R.id.student_popUp_message);
        studentPopupMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = extractNumber(phone);
                Uri telnumber = Uri.parse("smsto:" + number);
                Intent opensms = new Intent(Intent.ACTION_SENDTO, telnumber);
                startActivity(Intent.createChooser(opensms, "Send message Using"));
            }
        });

        studentPopupEmail = myDialog.findViewById(R.id.student_popUp_email);
        studentPopupEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String uriText = "mailto:" + Uri.encode(email) +
                        "?subject=" + Uri.encode("Dear Student") +
                        "&body=" + Uri.encode("Here's an information Regarding -> ");
                emailIntent.setData(Uri.parse(uriText));
                startActivity(Intent.createChooser(emailIntent, "Send Mail via"));
            }
        });
    }

    private static String extractNumber(String phone) {

        return phone.replaceAll(" ", "")
                .replaceAll("\\(", "")
                .replace(")", "")
                .replace("-", "");
    }

    private void checkIfEmpty() {

        if (eventList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noStudents.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        }
        if(eventList.size() == 0){
            recyclerView.setVisibility(View.GONE);
            noStudents.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        }
    }


    private void getEvents() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", FirebaseHelper.getUser().getUid());

        ApiClient.getClient().getEventByUser(map).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    eventList = (ArrayList<Event>) response.body();
                    Log.i("DATA", eventList + "");
                    mainAdapter.setData(eventList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    checkIfEmpty();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

}