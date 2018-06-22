package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MvpPrintDialog extends Dialog {
    CircleImageView profile_image;
    Requester requester;
    public MvpPrintDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.mvp);
        requester.getMVPprofileImage();
        // requester.setImageURI(path);

        profile_image = findViewById(R.id.profile_image);

    }
    public interface Requester{
        void getMVPprofileImage();
        void setImageURI(URI path);
    }
}
