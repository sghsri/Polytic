package io.github.sghsri.representativeapp;


import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SriramHariharan on 2/28/16.
 */
public class AdvocacyAdapter extends BaseAdapter implements OnClickListener{
    private Context context;
    private Map<String,String> scores;

    public AdvocacyAdapter(Context context, Map<String, String> scr){
        this.context = context;
        this.scores = scr;
    }
    public int getCount() {
        return scores.size();
    }
    public Object getItem(int position) {
        return scores.keySet().toArray()[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.advorow, null);
        }
        TextView group =  convertView.findViewById(R.id.advocgroup);
        group.setText((String)getItem(position));

        TextView score =  convertView.findViewById(R.id.score);
        String sc = scores.get(getItem(position));
        score.setText(sc);
        reColor(sc, score);
        return convertView;
    }
    private void reColor(String val, TextView score){
        if(val.matches("[a-zA-Z0-9]*")){
            //if letter:
            switch (val){
                case "A": score.setTextColor(Color.GREEN); break;
                case "B": score.setTextColor(Color.parseColor("#90EE90")); break;
                case "C": score.setTextColor(Color.YELLOW); break;
                case "D": score.setTextColor(Color.parseColor("#FFA500")); break;
                case "F": score.setTextColor(Color.RED); break;
            }
        } else {
            int num = Integer.parseInt(val.substring(0, val.indexOf('%')));
            if(num > 90 || num == 90) {
                score.setTextColor(Color.GREEN);
            }
            else if((num > 80 || num == 80)){
                score.setTextColor(Color.parseColor("#90EE90"));
            }
            else if((num > 75 || num == 75)) {
                score.setTextColor(Color.YELLOW);
            }
            else if (num > 70)
            {
                score.setTextColor(Color.parseColor("#FFA500"));
            }
            else {
                score.setTextColor(Color.RED);
            }

        }
    }
    @Override
    public void onClick(View view) {

    }



}
