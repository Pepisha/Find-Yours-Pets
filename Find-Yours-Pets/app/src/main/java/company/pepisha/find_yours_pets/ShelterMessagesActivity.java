package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.message.Message;
import company.pepisha.find_yours_pets.views.MessageViews;

public class ShelterMessagesActivity extends BaseActivity {

    private ListView messagesList;

    private int idShelter;

    private class GetSheltersMessages extends ServerDbOperation {

        public GetSheltersMessages(Context c) {
            super(c, "getSheltersMessages");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                List<Message> messages = new ArrayList<>();

                if (result.get("messagesAboutAnimals").toString() != "null") {
                    HashMap<String, Object> messagesAboutAnimals = ServerConnectionManager.unmarshallReponse(result.get("messagesAboutAnimals").toString());
                    messages.addAll(MessageViews.getMessagesList(messagesAboutAnimals));
                }

                if (result.get("messagesAboutShelter").toString() != "null") {
                    HashMap<String, Object> messagesAboutShelter = ServerConnectionManager.unmarshallReponse(result.get("messagesAboutShelter").toString());
                    messages.addAll(MessageViews.getMessagesList(messagesAboutShelter));
                }

                MessageViews.createMessagesList(messagesList, messages);
            }
        }
    }

    private void loadMessages() {
        HashMap<String, String> messagesRequest = new HashMap<String, String>();
        messagesRequest.put("idShelter", Integer.toString(idShelter));
        new GetSheltersMessages(this).execute(messagesRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_messages);

        idShelter = getIntent().getIntExtra("idShelter", 1);
        messagesList = (ListView) findViewById(R.id.messagesList);

        loadMessages();
    }
}
