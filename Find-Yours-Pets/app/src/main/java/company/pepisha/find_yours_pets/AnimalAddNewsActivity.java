package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;

public class AnimalAddNewsActivity extends BaseActivity {

    private int idAnimal;

    private class AddAnimalsNewsDbOperation extends ServerDbOperation {
        public AddAnimalsNewsDbOperation(Context c) {
            super(c, "addAnimalsNews");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Intent animalNewsScreen = new Intent(getApplicationContext(), AnimalNewsActivity.class);
                animalNewsScreen.putExtra("idAnimal", idAnimal);
                startActivity(animalNewsScreen);
            }
        }
    }

    private void addAnimalsNews() {
        EditText description = (EditText) findViewById(R.id.newDescription);
        idAnimal = getIntent().getIntExtra("idAnimal",1);

        HashMap<String, String> request = new HashMap<>();
        request.put("description", description.getText().toString());
        request.put("idAnimal", Integer.toString(idAnimal));

        new AddAnimalsNewsDbOperation(this).execute(request);
    }

    private void onClickAddNews() {
        Button addNewsButton = (Button) findViewById(R.id.addNewsButton);
        addNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimalsNews();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_add_news);

        onClickAddNews();
    }
}
