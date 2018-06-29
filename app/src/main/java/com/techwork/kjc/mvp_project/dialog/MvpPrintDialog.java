package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MvpPrintDialog extends Dialog {
    CircleImageView profile_image;
    TextView mName;
    public MvpPrintDialog(@NonNull Context context, Uri src, String name) {
        super(context);
        setContentView(R.layout.mvp);
        profile_image = findViewById(R.id.profile_image);
        profile_image.setImageURI(src);
        mName = findViewById(R.id.name);
        mName.setText(name);
    }
}
