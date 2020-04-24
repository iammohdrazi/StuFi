package com.zaph.loginsignupdesign.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseHelper {

    private static FirebaseAuth mAuth=null;

    public static FirebaseUser getUser(){
        if(mAuth == null){
            mAuth= FirebaseAuth.getInstance();
            return mAuth.getCurrentUser();
        }
        return mAuth.getCurrentUser();
    }
}
