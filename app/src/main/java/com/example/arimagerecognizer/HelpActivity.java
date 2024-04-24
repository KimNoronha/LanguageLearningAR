package com.example.arimagerecognizer;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.google.ar.core.examples.java.ml.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
//
//        TextView textViewHelpContent = findViewById(R.id.textViewHelpContent);
//        String formattedText = getString(R.string.help_page_content);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            textViewHelpContent.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            textViewHelpContent.setText(Html.fromHtml(formattedText));
//        }
}}