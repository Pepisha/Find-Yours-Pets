package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.appevents.AppEventsLogger;

import company.pepisha.find_yours_pets.session.SessionManager;

public class BaseActivity extends Activity {
    protected static SessionManager session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (session == null) {
            session = new SessionManager(getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(session.isLoggedIn()) {
            if(session.isUserAdmin()) {
                getMenuInflater().inflate(R.menu.menu_admin, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_connected, menu);
            }
        } else {
            getMenuInflater().inflate(R.menu.menu_disconnected, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean newPage = false;

        if (id == R.id.action_animals) {
            Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeScreen);
            newPage = true;
        }
        else if (id == R.id.action_shelters) {
            Intent sheltersScreen = new Intent(getApplicationContext(), SheltersActivity.class);
            startActivity(sheltersScreen);
            newPage = true;
        }
        else if (id == R.id.action_user_profile) {
            Intent userProfileScreen = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(userProfileScreen);
            newPage = true;
        }
        else if (id == R.id.ation_pets_preferences) {
            Intent petsPreferencesScreen = new Intent(getApplicationContext(), PetsPreferencesActivity.class);
            startActivity(petsPreferencesScreen);
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_add_shelter) {
            Intent addShelterScreen = new Intent(getApplicationContext(), AddShelterActivity.class);
            startActivity(addShelterScreen);
            newPage = true;
        }
        else if (id == R.id.action_disconnection) {
            session.logoutUser();
            Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainScreen);
            newPage = true;
        }

        if (newPage) {
            finish();
            return newPage;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
