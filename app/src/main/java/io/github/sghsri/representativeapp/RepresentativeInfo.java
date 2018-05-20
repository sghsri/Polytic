package io.github.sghsri.representativeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RepresentativeInfo extends AppCompatActivity {

    private Representative mRep;
    private CandidateInfo mCandInfo;

    private ListView mEnactedBills;
    private ListView mCommitteesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_info);
        mRep = (Representative)getIntent().getSerializableExtra("repinfo");
        mCandInfo = (CandidateInfo)getIntent().getSerializableExtra("repactivities");

        mEnactedBills = findViewById(R.id.enacted_bills_list);
        mCommitteesList = findViewById(R.id.committees_list);
        if (mCandInfo != null) {
            ArrayAdapter mArrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCandInfo.getEnactedLegislation());
            mEnactedBills.setAdapter(mArrAdapter);
        }
    }


}
