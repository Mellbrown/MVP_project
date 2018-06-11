package com.techwork.kjc.mvp_project.model;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.bean.ProfileUpdateBean;
import com.techwork.kjc.mvp_project.bean.UserInfoBean;
import com.techwork.kjc.mvp_project.exception.NotLoginException;


/**
 * Created by mlyg2 on 2018-06-11.
 */

public class ProfileModel {
    private static final String userinfoPATH = "userinfo";

    //로그인한 사용자의 개인 정보를 불러옵니다.
    public static void getProfile(final GetProfileListener getProfileListener) throws NotLoginException{
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new NotLoginException();
        }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference(userinfoPATH+"/"+uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getProfileListener.result(dataSnapshot.getValue(UserInfoBean.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getProfileListener.loadfail(databaseError);
            }
        });
    }
    interface GetProfileListener{ void result(UserInfoBean profile); void loadfail(DatabaseError databaseError); }

    //로그인한 사용자의 개인 정보를 업데이트합니다.
    public static void updateProfile(ProfileUpdateBean profileUpdateBean, UpdateProfileListener updateProfileListener) throws NotLoginException {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new NotLoginException();
        }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        UserInfoBean userInfoBean = new UserInfoBean();

    }
    interface UpdateProfileListener{void updateComplete(); void updateFail(); }
}
