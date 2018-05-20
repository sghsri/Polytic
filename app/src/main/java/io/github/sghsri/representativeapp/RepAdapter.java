package io.github.sghsri.representativeapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.out;


/**
 * Created by SriramHariharan on 3/12/18.
 */

public class RepAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Representative> reps;
    private static LayoutInflater inflater = null;

    public RepAdapter(Context contxt, ArrayList<Representative> reps) {
        this.reps = reps;
        context = contxt;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return reps.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return reps.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView pic;
        TextView name;
        TextView office;
        LinearLayout party;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Representative r = reps.get(position);
        Holder holder = new Holder();
        convertView = inflater.inflate(R.layout.rep_row, null);
        holder.pic = convertView.findViewById(R.id.rep_row_pic);
        holder.name = convertView.findViewById(R.id.rep_row_name);
        holder.office = convertView.findViewById(R.id.rep_row_office);
        holder.party = convertView.findViewById(R.id.rep_row_party);
        if(r.getPhotoUrl() != "default") {
            Picasso.with(convertView.getContext()).load(r.getPhotoUrl()).into(holder.pic);
        }
        holder.name.setText(r.getName());
        holder.office.setText(r.getOffice());
        if(r.getParty().equals("Republican")){
            holder.party.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        }else{
            holder.party.setBackgroundColor(convertView.getResources().getColor(R.color.colorPrimary));
        }
        return convertView;
    }
}
