package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPublicInfoDAO {

    public static final String REMOTE_PATH = "g2u-submarine/user-public-info";


    public static void selectUserByUID(List<String> uides, OnSelectedLisnter onSelectedLisnter){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, UserPublicInfoBean> publicInfoBeanHashMap = new HashMap<>();
                for(String uid  : uides){
                    UserPublicInfoBean userPublicInfoBean = dataSnapshot.child(uid).getValue(UserPublicInfoBean.class);
                    publicInfoBeanHashMap.put(uid,userPublicInfoBean);
                }
                onSelectedLisnter.onSelected(true,publicInfoBeanHashMap,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedLisnter.onSelected(false,null, databaseError);
            }
        });
    }

    public static void selectAllUser(OnSelectedLisnter onSelectedLisnter){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, UserPublicInfoBean> userPublicInfoBeanMap
                        = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, UserPublicInfoBean>>());
                onSelectedLisnter.onSelected(true,userPublicInfoBeanMap,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedLisnter.onSelected(false,null, databaseError);
            }
        });
    }

    public interface OnSelectedLisnter{
        void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError);
    }

    public static Task<Void> addUser(String uid, UserPublicInfoBean userPublicInfoBean){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).setValue(userPublicInfoBean);
    }

    public static Task<Void> removeUser(String uid){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).removeValue();
    }
}
