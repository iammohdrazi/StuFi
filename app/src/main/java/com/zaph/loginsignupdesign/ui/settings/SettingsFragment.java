package com.zaph.loginsignupdesign.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.ui.Splash;

public class SettingsFragment extends Fragment {

   /* private Button logOut;
    private FirebaseAuth mAuth;*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

       /* logOut = (Button) root.findViewById(R.id.log_out);
        mAuth = FirebaseAuth.getInstance();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), Splash.class);
                startActivity(intent);
            }
        });*/

        return root;
    }
}