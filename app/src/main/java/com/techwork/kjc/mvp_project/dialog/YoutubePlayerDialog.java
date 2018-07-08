package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.VideoCodeDAO;
import com.techwork.kjc.mvp_project.util.g2u;

public class YoutubePlayerDialog extends Dialog {


    public YoutubePlayerDialog(@NonNull Context context, String videopath) {
        super(context);
        setContentView(R.layout.dialog_youtubeplayer);

        WebView webView = findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        VideoCodeDAO.selectVideoCode(videopath, new VideoCodeDAO.OnSelected() {
            @Override
            public void onSelected(String videoCode) {
                if(videoCode == null) videoCode = "Frs7j21R4Cc";
                webView.loadData(
                        "<style>html,body,iframe{ margin:0px; padding:0px; height:100%; width:100% }</style>"+
                                "<iframe src='https://www.youtube.com/embed/"+videoCode+"' frameborder='0' allow='autoplay; encrypted-media' allowfullscreen></iframe>",
                        "text/html", "utf-8"
                );
                findViewById(R.id.progress).setVisibility(View.GONE);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webView.setWebContentsDebuggingEnabled(true);
        }
    }
}
