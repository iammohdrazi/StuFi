package com.zaph.loginsignupdesign.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.ui.assignment.AssignmentFragment;
import com.zaph.loginsignupdesign.ui.chat.ChatFragment;
import com.zaph.loginsignupdesign.ui.feedback.FeedbackFragment;
import com.zaph.loginsignupdesign.ui.settings.SettingsFragment;
import com.zaph.loginsignupdesign.ui.share.ShareFragment;
import com.zaph.loginsignupdesign.ui.students.StudentsFragment;
import com.zaph.loginsignupdesign.ui.sync.SyncFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private ImageView ivMenu;
    private LinearLayout llContainer;
    private NavigationView navigationView;
    private TextView toolbarHeading;
    private FirebaseAuth mAuth;
    private TextView drawerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarHeading = findViewById(R.id.tvToolbarHeading);
        toolbarHeading.setVisibility(View.VISIBLE);

        drawer = findViewById(R.id.drawer_layout);
        ivMenu= findViewById(R.id.ivMenu);
        llContainer= findViewById(R.id.llContainer);
        navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ivMenu.setOnClickListener(this);
        ivMenu.setVisibility(View.VISIBLE);
        /*drawerEmail = findViewById(R.id.drawer_name);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        drawerEmail.setText(currentUser.getEmail());*/

        navigationView.setCheckedItem(R.id.nav_students);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llContainer, new StudentsFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMenu :
                drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_students:
                    drawer.closeDrawer(Gravity.LEFT);
                toolbarHeading.setText("Students");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.llContainer, new StudentsFragment())
                            .commit();
                    break;
            case R.id.nav_assignment:
                toolbarHeading.setText("Notes");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new AssignmentFragment())
                        .commit();
                break;
            case R.id.nav_settings:
                toolbarHeading.setText("Settings");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new SettingsFragment())
                        .commit();
                break;

            case R.id.nav_sync:
                toolbarHeading.setText("Upload");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new SyncFragment())
                        .commit();
                break;
            case R.id.nav_share:
                toolbarHeading.setText("Share");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new ShareFragment())
                        .commit();
                break;
            case R.id.nav_chat:
                toolbarHeading.setText("Chat");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new ChatFragment())
                        .commit();
                break;
            case R.id.nav_feedback:
                toolbarHeading.setText("Feedback");
                drawer.closeDrawer(Gravity.LEFT);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.llContainer, new FeedbackFragment())
                        .commit();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}


