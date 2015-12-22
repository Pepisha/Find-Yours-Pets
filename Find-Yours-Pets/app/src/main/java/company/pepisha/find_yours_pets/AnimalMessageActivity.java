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

public class AnimalMessageActivity extends BaseActivity {

    private Animal animal;

    private EditText messageContent;

    private class SendMessageAboutAnimalDbOperation extends ServerDbOperation {

        public SendMessageAboutAnimalDbOperation(Context c) {
            super(c, "sendMessageAboutAnimal");
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
                request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
                request.put("nickname", session.getUserDetails().get("nickname"));
                request.put("content", messageContent.getText().toString());
                new SendMessageAboutAnimalDbOperation(v.getContext()).execute(request);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_message);

        animal = getIntent().getParcelableExtra("animal");
        ImageView picture = (ImageView) findViewById(R.id.animalPicture);
        picture.setImageBitmap((Bitmap) getIntent().getParcelableExtra("photo"));

        TextView petName = (TextView) findViewById(R.id.petName);
        petName.setText(animal.getName());

        messageContent = (EditText) findViewById(R.id.messageContent);
        messageContent.setText("Bonjour,\n\n"
                        + "Je suis intéressé par l'animal " + animal.getName() + " de votre refuge.\n"
                        + "Serait-il possible de passer le voir ?\n\n"
                        + "Merci.");

        onSendButtonClick();
    }
}
