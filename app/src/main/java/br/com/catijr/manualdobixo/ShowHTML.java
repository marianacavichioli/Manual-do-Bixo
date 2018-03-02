package br.com.catijr.manualdobixo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.Console;
import java.sql.SQLOutput;

public class ShowHTML extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_html);
        TextView test = (TextView) findViewById(R.id.test_box);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title;

        if (bundle != null) {
            title = bundle.getString("filename");
            title = "file:///android_asset/"+title;

            WebView view = new WebView(this);
            view.getSettings().setJavaScriptEnabled(true);
            view.loadUrl(title);
            setContentView(view);
        }
    }
}
