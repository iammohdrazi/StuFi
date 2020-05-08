package com.zaph.loginsignupdesign.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    private static FirebaseAuth mAuth=null;
    private static FirebaseFirestore dbRef = null;

    public static FirebaseUser getUser(){
        if(mAuth == null){
            mAuth= FirebaseAuth.getInstance();
            return mAuth.getCurrentUser();
        }
        return mAuth.getCurrentUser();
    }

    public static FirebaseFirestore getDbRef(){
        if(dbRef == null){
            dbRef = FirebaseFirestore.getInstance();
            return dbRef;
        }
        return dbRef;
    }

}
