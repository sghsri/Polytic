package io.github.sghsri.representativeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RepresentativeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_info);
        Representative rep = (Representative)getIntent().getSerializableExtra("repinfo");
    }
}
