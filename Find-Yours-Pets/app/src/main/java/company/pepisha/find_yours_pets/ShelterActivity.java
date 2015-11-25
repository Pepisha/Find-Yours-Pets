package company.pepisha.find_yours_pets;

import android.os.Bundle;
import android.widget.TextView;

import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;

public class ShelterActivity extends BaseActivity {

    private Shelter shelter;

    private void fillShelterFields() {
        TextView shelterName = (TextView) findViewById(R.id.shelterName);
        shelterName.setText(shelter.getName());

        TextView shelterDescription = (TextView) findViewById(R.id.shelterDescription);
        shelterDescription.setText(shelter.getDescription());

        TextView shelterAddress = (TextView) findViewById(R.id.shelterAddress);
        shelterAddress.setText(Integer.toString(shelter.getIdAddress()));

        TextView shelterMail = (TextView) findViewById(R.id.shelterMail);
        shelterMail.setText(shelter.getMail());

        TextView shelterPhone = (TextView) findViewById(R.id.shelterPhone);
        shelterPhone.setText(shelter.getPhone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);

        shelter = (ParcelableShelter) getIntent().getParcelableExtra("shelter");

        fillShelterFields();
    }
}
