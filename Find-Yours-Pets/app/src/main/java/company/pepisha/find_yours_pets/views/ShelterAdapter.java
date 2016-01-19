package company.pepisha.find_yours_pets.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.shelter.Shelter;


public class ShelterAdapter extends ArrayAdapter<Shelter> {

    private LayoutInflater mInflater = null;

    public ShelterAdapter(Context context, int textViewResourceId, List<Shelter> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View shelterView = null;

        if (convertView != null) {
            shelterView = convertView;
        } else {
            shelterView = mInflater.inflate(R.layout.shelter_layout, null);
        }

        ImageView followIcon = (ImageView) shelterView.findViewById(R.id.followIcon);
        TextView shelterName = (TextView) shelterView.findViewById(R.id.shelterName);

        Shelter shelter = getItem(position);
        if (shelter.isFollowed()) {
            followIcon.setImageResource(R.drawable.star);
        } else {
            followIcon.setImageBitmap(null);
        }

        shelterName.setText(shelter.toString());

        return shelterView;
    }
}
