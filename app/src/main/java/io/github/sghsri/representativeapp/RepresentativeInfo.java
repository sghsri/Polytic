package io.github.sghsri.representativeapp;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeInfo extends AppCompatActivity {

    private Representative mRep;
    private CandidateInfo mCandInfo;

    private ListView mEnactedBills;
    private ListView mCommitteesList;
    private ListView mAdvocacyList;
    private ImageView mPortrait;

    protected PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRep = (Representative)getIntent().getSerializableExtra("repinfo");
        mCandInfo = (CandidateInfo)getIntent().getSerializableExtra("repactivities");
        mPortrait = findViewById(R.id.rep_image);
        Picasso.with(this).load(mRep.getPhotoUrl()).into(mPortrait);
        RecyclerView RV = findViewById(R.id.socialrecyclerview);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RV.setLayoutManager(horizontalLayoutManagaer);
        RV.setAdapter(new SocialRecyclerAdapter(mRep.getSocialMedia(),getApplicationContext()));

        if(mRep.getName().equals("Ted Cruz")){
            mPortrait.setBackgroundResource(R.drawable.tedcruz);
        }

        mEnactedBills = findViewById(R.id.enacted_bills_list);
        mCommitteesList = findViewById(R.id.committees_list);
        mAdvocacyList = findViewById(R.id.advlist);
        mAdvocacyList.setAdapter(new AdvocacyAdapter(getApplicationContext(),mCandInfo.getAdvRating()));
        System.out.println(mCandInfo.getAdvRating());
        if (mCandInfo != null) {
            List<Committee> committees = new ArrayList<>();
            for (Committee c : mCandInfo.getCommittees()) committees.add(c);

            ArrayAdapter mArrAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCandInfo.getEnactedLegislation());
            mEnactedBills.setAdapter(mArrAdapter);
            mCommitteesList.setAdapter(new CommitteeAdapter(this, R.layout.committee_item_layout, committees));
            setListViewHeightBasedOnChildren(mEnactedBills);
            setListViewHeightBasedOnChildren(mCommitteesList);
        }
        Representative rep = (Representative)getIntent().getSerializableExtra("repinfo");

        mChart = findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
     //   mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setCenterTextSize(30);
        mChart.setHoleRadius(58f);
        mChart.setCenterTextColor(Color.BLACK);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setDrawEntryLabels(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setExtraLeftOffset(-50f);
        mChart.setExtraRightOffset(10f);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        setData(mCandInfo);

        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setXOffset(65f);
        l.setTextSize(12f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

    }

    private void setData(CandidateInfo rep) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for(String is : rep.getIssues().keySet()) {
            double val = rep.getIssues().get(is);
            entries.add(new PieEntry((float) val, is));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
