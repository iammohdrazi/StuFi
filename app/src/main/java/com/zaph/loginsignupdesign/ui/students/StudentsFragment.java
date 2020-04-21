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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.models.Student;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zaph.loginsignupdesign.ui.AddStudent;
import com.zaph.loginsignupdesign.ui.DatabaseHelperClass;
import com.zaph.loginsignupdesign.ui.MainAdapter;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.zaph.loginsignupdesign.ui.DatabaseHelperClass.*;


public class StudentsFragment extends Fragment {


    private ArrayList<Student> phoneList = new ArrayList();
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView studentEditor;
    private DatabaseHelperClass myDb ;
    private Dialog myDialog;
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
    private BottomAppBar bottomAppBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private LottieAnimationView noData;
    private TextView noStudents;

    private FirebaseFirestore mFirestore;
    private static final String FIRE_LOG = "Fire_log";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_students, container, false);

        //getSupportActionBar().setTitle("Students");

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        myDb = new DatabaseHelperClass(getActivity()); // dataBase constructor is calling here
        setUpRecycler(root);
        /*bottomAppBar = root.findViewById(R.id.toolbar);*/
        /*searchStudent = root.findViewById(R.id.search_student);
        searchIcon = root.findViewById(R.id.search_menu_icon);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_search:
                        break;
                    case R.id.menu_notes:
                        Intent intent = new Intent(getActivity(), Note.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getActivity(), "Touch Again", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });*/
        /*tvToolbarHeading = root.findViewById(R.id.tvToolbarHeading);
        tvToolbarHeading.setText("Students");
        tvToolbarHeading.setVisibility(View.VISIBLE);*/

       /* mFirestore.collection("events").document("hostDetails").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists() && documentSnapshot !=null){
                        String hostname = documentSnapshot.getString("name");
                    }
                } else {
                    Log.d(FIRE_LOG,"Error : "+ task.getException().getMessage());
                }
            }
        });

        //continousely retriving data say realTime

*/
        noData = root.findViewById(R.id.noStudents);
        noStudents = root.findViewById(R.id.textNoStudentsAdded);
        getStudentData();
        checkIfEmpty();
        return root;
    }

    void setUpRecycler(View view) {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter=new MainAdapter(getActivity(),this,phoneList);
        recyclerView.setAdapter(mainAdapter);

        fab = (FloatingActionButton)view.findViewById(R.id.add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivityForResult(intent,101);
            }
        });

    }

    public void getStudentData(){
        Cursor res = myDb.getAllData();

        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("events").document("hostDetails").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if(documentSnapshot.exists() && documentSnapshot !=null){
                    phoneList.add(
                            new Student(
                                    documentSnapshot.getString("name"),
                                    documentSnapshot.getString("phone"),
                                    documentSnapshot.getString("gender"),
                                    documentSnapshot.getString("studentid"),
                                    documentSnapshot.getString("email"),
                                    documentSnapshot.getString("course"),
                                    documentSnapshot.getString("year"),
                                    documentSnapshot.getString("eventname"),
                                    documentSnapshot.getString("eventcategory"),
                                    documentSnapshot.getString("eventvenue"),
                                    documentSnapshot.getString("eventfee"),
                                    documentSnapshot.getString("eventpayment"),
                                    documentSnapshot.getString("joiningcriteria")
                            )
                    );

                }
            }
        });

       /* mFirestore.collection("events").document("hostDetails").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists() && documentSnapshot !=null){
                        phoneList.add(
                                new Student(
                                        documentSnapshot.getString("name"),
                                        documentSnapshot.getString("phone"),
                                        documentSnapshot.getString("gender"),
                                        documentSnapshot.getString("studentid"),
                                        documentSnapshot.getString("email"),
                                        documentSnapshot.getString("course"),
                                        documentSnapshot.getString("year")
                                )
                        );

                    }
                } else {
                    Log.d(FIRE_LOG,"Error : "+ task.getException().getMessage());
                }
            }
        });
*/
        /*if(res.getCount() == 0){

            //Error Message

            return;
        }*/

        /*StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            phoneList.add(
                    new Student(
                            res.getString(res.getColumnIndex(COL_NAME)),
                            res.getString(res.getColumnIndex(COL_PHONE)),
                            res.getString(res.getColumnIndex(COL_GENDER)),
                            res.getString(res.getColumnIndex(COL_ID)),
                            res.getString(res.getColumnIndex(COL_EMAIL)),
                            res.getString(res.getColumnIndex(COL_BRANCH)),
                            res.getString(res.getColumnIndex(COL_YEAR)),
                            res.getString(res.getColumnIndex(COL_ATTENDANCE))
                    )
            );

        }*/

        mainAdapter.notifyDataSetChanged();
        checkIfEmpty();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Student student=(Student) data.getSerializableExtra("data");
                phoneList.add(student);
                mainAdapter.notifyDataSetChanged();
                checkIfEmpty();
            }
        }else if(requestCode == 202){
            if(resultCode == RESULT_OK){
                //(TODO) Refresh Code Here
                phoneList.clear();
                checkIfEmpty();
                getStudentData();
            }
        }
    }


    public void showPopup(String name, String id, final String phone, String gender, final String email, String branch, String year){
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
                Uri telnumber = Uri.parse("smsto:"+number);
                Intent opensms = new Intent(Intent.ACTION_SENDTO,telnumber);
                startActivity(Intent.createChooser(opensms,"Send message Using"));
            }
        });

        studentPopupEmail = myDialog.findViewById(R.id.student_popUp_email);
        studentPopupEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String uriText = "mailto:" + Uri.encode(email)+
                        "?subject="+Uri.encode("Dear Student")+
                        "&body=" + Uri.encode("Here's an information Regarding -> ");
                emailIntent.setData(Uri.parse(uriText));
                startActivity(Intent.createChooser(emailIntent,"Send Mail via"));
            }
        });
    }

    private static String extractNumber(String phone){

        return phone.replaceAll(" ","")
                .replaceAll("\\(","")
                .replace(")","")
                .replace("-","");
    }

private void checkIfEmpty(){

        if(phoneList.size() > 0 ){
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            noStudents.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            noStudents.setVisibility(View.VISIBLE);
        }

}

}