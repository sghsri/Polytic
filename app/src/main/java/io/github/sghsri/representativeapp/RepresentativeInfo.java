package io.github.sghsri.representativeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RepresentativeInfo extends AppCompatActivity {

    private Representative mRep;
    private CandidateInfo mCandInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_info);
        mRep = (Representative)getIntent().getSerializableExtra("repinfo");
        mCandInfo = (CandidateInfo)getIntent().getSerializableExtra("repactivities");
    }
}
