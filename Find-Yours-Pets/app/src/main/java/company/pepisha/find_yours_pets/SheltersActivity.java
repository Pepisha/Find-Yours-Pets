package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.db.shelter.ShelterOperation;

public class SheltersActivity extends Activity {

    ListView sheltersList;
    ShelterOperation shelterOperation;

    private void addShelters() {
        shelterOperation = new ShelterOperation(this);
        shelterOperation.open();

        List<Shelter> shelters = shelterOperation.getAllShelters();
        List<String> shelterStrings = new ArrayList<String>();

        for (Shelter s : shelters) {
            shelterStrings.add(s.getName() + " (" + s.getMail() + ")");
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shelterStrings);
        sheltersList.setAdapter(listAdapter);

        shelterOperation.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);

        sheltersList = (ListView) findViewById(R.id.sheltersList);

        addShelters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shelters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
