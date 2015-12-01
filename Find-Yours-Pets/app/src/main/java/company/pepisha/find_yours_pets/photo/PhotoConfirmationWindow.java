package company.pepisha.find_yours_pets.photo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import company.pepisha.find_yours_pets.R;


public class PhotoConfirmationWindow extends AlertDialog {

    public PhotoConfirmationWindow(Context context) {
        super(context);

        setTitle(R.string.photoConfirmation);
        setCancelable(false);
        setMessage("Cliquez sur oui pour envoyer la photo !");
    }

    public void setNegativeClickListener(DialogInterface.OnClickListener listener) {
        setButton(AlertDialog.BUTTON_NEGATIVE, "Non", listener);
    }

    public void setPositiveClickListener(DialogInterface.OnClickListener listener) {
        setButton(BUTTON_POSITIVE, "Oui", listener);
    }
}
