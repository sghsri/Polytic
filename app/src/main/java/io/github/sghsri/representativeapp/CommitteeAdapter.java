package io.github.sghsri.representativeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CommitteeAdapter extends ArrayAdapter<Committee> {

    public CommitteeAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CommitteeAdapter(Context context, int resource, List<Committee> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.committee_item_layout, null);
        }

        Committee p = getItem(position);

        if (p != null) {
            TextView tt1 = v.findViewById(R.id.committee_name);
            TextView tt2 = v.findViewById(R.id.subcommittee_text);

            tt1.setText(p.getName());

            String subText = "";
            for (String s : p.getSubcommitees().keySet()) {
                subText += p.getSubcommitees().get(s) + " of " + s + '\n';
            }
            tt2.setText(subText);
        }

        return v;
    }

}