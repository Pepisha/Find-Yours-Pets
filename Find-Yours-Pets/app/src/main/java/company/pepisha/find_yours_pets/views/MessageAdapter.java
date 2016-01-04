package company.pepisha.find_yours_pets.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.message.Message;

public class MessageAdapter extends ArrayAdapter<Message> {

    private LayoutInflater mInflater = null;

    public MessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mInflater = LayoutInflater.from(context);
    }

    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View messageView = null;

        if (convertView != null) {
            messageView = convertView;
        } else {
            messageView = mInflater.inflate(R.layout.message_layout, null);
        }

        TextView authorName = (TextView) messageView.findViewById(R.id.authorName);
        TextView animalName = (TextView) messageView.findViewById(R.id.animalName);
        TextView messageDate = (TextView) messageView.findViewById(R.id.messageDate);

        Message message = getItem(position);
        authorName.setText(message.getNickname());
        animalName.setText(message.getAnimalName());
        messageDate.setText(message.getDateMessage());

        return messageView;
    }
}
