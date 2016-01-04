package company.pepisha.find_yours_pets.views;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.MessageActivity;
import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.message.Message;
import company.pepisha.find_yours_pets.parcelable.ParcelableMessage;

public class MessageViews {

    private static class SetMessageReadDbOperation extends ServerDbOperation {

        public SetMessageReadDbOperation(Context c) {
            super(c, "setMessageRead");
        }
    }

    public static void createMessagesList(ListView list, List<Message> messages) {
        final MessageAdapter listAdapter = new MessageAdapter(list.getContext(), R.layout.message_layout, messages);
        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Message message = (Message) adapter.getItemAtPosition(position);

                Intent messageScreen = new Intent(view.getContext(), MessageActivity.class);
                messageScreen.putExtra("message", (ParcelableMessage) message);
                view.getContext().startActivity(messageScreen);

                if (!message.isMessageRead()) {
                    HashMap<String, String> request = new HashMap<>();
                    request.put("idMessage", Integer.toString(message.getIdMessage()));
                    new SetMessageReadDbOperation(view.getContext()).execute(request);
                }

                listAdapter.setMessageRead(view);
            }
        });
    }

    public static List<Message> getMessagesList(Map<String, Object> messages) {
        List<Message> messagesList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : messages.entrySet()) {
            ParcelableMessage m = new ParcelableMessage((JSONObject) entry.getValue());
            messagesList.add(m);
        }

        return messagesList;
    }
}
