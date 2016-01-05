package company.pepisha.find_yours_pets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;

public class SendMessageActivity extends BaseActivity {

    private Animal animal = null;

    private int idShelter;

    private EditText messageContent;

    private class SendMessageDbOperation extends ServerDbOperation {

        public SendMessageDbOperation(Context c) {
            super(c, "sendMessage");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.messageSent), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void onSendButtonClick() {
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> request = new HashMap<>();

                if (animal != null) {
                    request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
                }

                if (idShelter != 0) {
                    request.put("idShelter", Integer.toString(idShelter));
                }

                request.put("nickname", session.getUserDetails().get("nickname"));
                request.put("content", messageContent.getText().toString());

                if (messageContent.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), getResources().getString(R.string.emptyMessage), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (animal != null || idShelter != 0) {
                    new SendMessageDbOperation(v.getContext()).execute(request);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        TextView title = (TextView) findViewById(R.id.title);
        messageContent = (EditText) findViewById(R.id.messageContent);

        if (getIntent().hasExtra("animal")) {
            animal = getIntent().getParcelableExtra("animal");

            title.setText(animal.getName());

            ImageView picture = (ImageView) findViewById(R.id.animalPicture);
            picture.setImageBitmap((Bitmap) getIntent().getParcelableExtra("photo"));

            messageContent.setText("Bonjour,\n\n"
                    + "Je suis intéressé par l'animal " + animal.getName() + " de votre refuge.\n"
                    + "Serait-il possible de passer le voir ?\n\n"
                    + "Merci.");
        } else {
            idShelter = getIntent().getIntExtra("idShelter", 0);
        }

        onSendButtonClick();
    }
}
