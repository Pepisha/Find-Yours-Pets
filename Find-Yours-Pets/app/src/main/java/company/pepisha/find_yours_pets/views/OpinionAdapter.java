package company.pepisha.find_yours_pets.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.opinion.Opinion;

public class OpinionAdapter extends ArrayAdapter<Opinion> {

    private LayoutInflater mInflater = null;

    public OpinionAdapter(Context context, int textViewResourceId, List<Opinion> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View opinionView = null;

        if (convertView != null) {
            opinionView = convertView;
        } else {
            opinionView = mInflater.inflate(R.layout.opinion_layout, null);
        }

        TextView opinionText = (TextView) opinionView.findViewById(R.id.opinionText);
        RatingBar opinionRate = (RatingBar) opinionView.findViewById(R.id.opinionsRate);

        Opinion opinion = getItem(position);
        opinionText.setText(opinion.getComment());
        opinionRate.setRating(opinion.getStars());

        return opinionView;
    }
}
