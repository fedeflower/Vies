package vies.uniba.it.vies.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Federico on 05/04/2016.
 */
public class viesAlert {
    public static void openAlert(Context context) {
        final Context context1 = context;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context1);
        alertDialogBuilder.setTitle("Avviso");

        alertDialogBuilder.setMessage("Purtroppo questa funzione non è stata ancora implementata.");
        alertDialogBuilder.setPositiveButton("Continua",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                dialog.cancel();

                Toast.makeText(context1, "Ci scusiamo per l'inconveniente...",

                        Toast.LENGTH_LONG).show();
            }

        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show alert
        alertDialog.show();
    }
}
