package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.message.Message;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.parcelable.ParcelableMessage;

public class MessageActivity extends BaseActivity {

    private Message message;
    private GridLayout messageLayout;
    private ParcelableAnimal animal;

    private class GetAnimalDbOperation extends ServerDbOperation {
        public GetAnimalDbOperation(Context c) {
            super(c, "getAnimalInformations");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if(successResponse(result)) {
                animal = new ParcelableAnimal((JSONObject) result.get("animal"));
                addAnimal();
            }
        }
    }

    private void addToGrid(View v, int row, int col, int rowSpan, int colSpan) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, rowSpan),
                GridLayout.spec(col, colSpan));

        messageLayout.addView(v, params);
    }


    private void fillMessageFields() {
        Button userButton = (Button) findViewById(R.id.userNickname);
        userButton.setText(message.getNickname());

        TextView content = (TextView) findViewById(R.id.contentMessage);
        content.setText(message.getContent());
    }

    private void addAnimalIfKnown() {
        if (message.getIdAnimal() != 0) {
            HashMap<String, String> request = new HashMap<>();
            request.put("idAnimal", Integer.toString(message.getIdAnimal()));
            request.put("nickname", session.getUserDetails().get("nickname"));

            new GetAnimalDbOperation(this).execute(request);
        }
    }

    private void addAnimal() {
        TextView animalText = new TextView(this);
        animalText.setText(R.string.animal);

        Button animalButton = new Button(this);
        animalButton.setText(message.getAnimalName());
        animalButton.setBackgroundResource(R.drawable.button_brown_style);
        animalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animalScreen = new Intent(getApplicationContext(), AnimalActivity.class);
                animalScreen.putExtra("animal", animal);
                startActivity(animalScreen);
            }
        });

        addToGrid(animalText, 2, 0, 1, 1);
        addToGrid(animalButton, 2, 1, 1, 1);
    }

    private void onClickSeeUser() {
        Button seeUser = (Button) findViewById(R.id.userNickname);
        seeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userProfile = new Intent(getApplicationContext(), UserProfileActivity.class);
                userProfile.putExtra("nickname", message.getNickname());
                startActivity(userProfile);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activity);

        message = (ParcelableMessage) getIntent().getParcelableExtra("message");
        messageLayout = (GridLayout) findViewById(R.id.messageLayout);

        fillMessageFields();
        addAnimalIfKnown();
        onClickSeeUser();
    }
}
