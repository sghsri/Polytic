package io.github.sghsri.representativeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeInfo extends AppCompatActivity {

    private Representative mRep;
    private CandidateInfo mCandInfo;

    private ListView mEnactedBills;
    private ListView mCommitteesList;
    private ImageView mPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_info);
        mRep = (Representative)getIntent().getSerializableExtra("repinfo");
        mCandInfo = (CandidateInfo)getIntent().getSerializableExtra("repactivities");
        mPortrait = findViewById(R.id.rep_image);
        Picasso.with(this).load("https://www.govtrack.us/data/photos/412573-200px.jpeg").into(mPortrait);

        mEnactedBills = findViewById(R.id.enacted_bills_list);
        mCommitteesList = findViewById(R.id.committees_list);
        if (mCandInfo != null) {
            List<Committee> committees = new ArrayList<>();
            for (Committee c : mCandInfo.getCommittees()) committees.add(c);

            ArrayAdapter mArrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCandInfo.getEnactedLegislation());
            mEnactedBills.setAdapter(mArrAdapter);
            mCommitteesList.setAdapter(new CommitteeAdapter(this, R.layout.committee_item_layout, committees));
            setListViewHeightBasedOnChildren(mEnactedBills);
            setListViewHeightBasedOnChildren(mCommitteesList);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount() * 2 / 3; i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
