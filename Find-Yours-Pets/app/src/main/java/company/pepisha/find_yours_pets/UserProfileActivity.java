package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.user.User;
import company.pepisha.find_yours_pets.parcelable.ParcelableUser;

public class UserProfileActivity  extends BaseActivity {

    private ParcelableUser user;


    private class GetUserInformationsDbOperation extends ServerDbOperation {
        public GetUserInformationsDbOperation(Context c) {
            super(c, "getUserInformations");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
        /*    ParcelableUser userInformations = new ParcelableUser((JSONObject) );
            user = userInformations;*/
        }
    }


    private void fillUserFields() {
        TextView userNickname = (TextView) findViewById(R.id.userLogin);
        userNickname.setText(user.getNickname());

        TextView userFirstname = (TextView) findViewById(R.id.userFirstname);
        userFirstname.setText(user.getFirstname());

        TextView userLastname = (TextView) findViewById(R.id.userLastname);
        userLastname.setText(user.getLastname());

        TextView userMail = (TextView) findViewById(R.id.userMail);
        userMail.setText(user.getEmail());

        TextView userPhone = (TextView) findViewById(R.id.userPhone);
        userPhone.setText(user.getPhone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

      /*  request.put("nickname", )
        new GetUserInformationsDbOperation(getApplicationContext()).execute(request);
        user = (ParcelableUser) getIntent().getParcelableExtra("user");
*/
        fillUserFields();
    }
}
