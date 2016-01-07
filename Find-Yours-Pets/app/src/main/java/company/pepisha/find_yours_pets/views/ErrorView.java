package company.pepisha.find_yours_pets.views;


import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import company.pepisha.find_yours_pets.R;

public class ErrorView {

    public static void errorPage(final Activity a, String message) {
        a.setContentView(R.layout.error_page);

        TextView errorText = (TextView) a.findViewById(R.id.errorText);
        errorText.setText(message);

        Button returnButton = (Button) a.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.finish();
            }
        });
    }
}
