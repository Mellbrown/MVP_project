package resource;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.controller.TestController;

import java.util.ArrayList;

public class FirebaseResource extends AppCompatActivity{

    public void goLogin(ArrayList<String> info,TestController cur){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(info.get(0),info.get(1)).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    cur.rendingFRG5_Measure();
                } else {

                }
            }
        });
    }

    public void createAuth(ArrayList<String> info){
        Toast.makeText(getApplicationContext(), "ing....", Toast.LENGTH_LONG).show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(info.get(0),info.get(1)).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getApplicationContext(), "응 수고", Toast.LENGTH_LONG).show();
            }
        });
    }
}
