package vies.uniba.it.vies.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import vies.uniba.it.vies.activity.MainActivity;
import vies.uniba.it.vies.activity.TabGMActivity;
import vies.uniba.it.vies.database.DBHelper;

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


    public static void exitAlert(Context context) {

        final MainActivity main =((MainActivity) context);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Uscita");

        alertDialogBuilder.setMessage("Sei sicuro di voler uscire?");
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()

                {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }

                }

        );
        alertDialogBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener()

                {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        main.finish();
                    }

                }

        );

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show alert
        alertDialog.show();
    }

    public static void deleteAlert(Context context,final int album_id){
       final TabGMActivity act=(TabGMActivity) context;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Attenzione!");

        alertDialogBuilder.setMessage("Sei sicuro di voler eliminare l'album?");
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();


            }

        });
        alertDialogBuilder.setPositiveButton("Si",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                dialog.cancel();
                //query!!
                //act.query();
                DBHelper.getInstance(act).deleteTravel(album_id);
                act.finish();
            }

        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show alert
        alertDialog.show();
    }


}
