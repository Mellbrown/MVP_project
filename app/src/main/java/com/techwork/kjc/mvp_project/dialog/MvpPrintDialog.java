package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MvpPrintDialog extends Dialog {
    CircleImageView profile_image;
    public MvpPrintDialog(@NonNull Context context, Uri src) {
        super(context);
        setContentView(R.layout.mvp);
        profile_image = findViewById(R.id.profile_image);
        profile_image.setImageURI(src);
    }
}
