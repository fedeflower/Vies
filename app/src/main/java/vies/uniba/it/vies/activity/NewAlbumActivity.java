package vies.uniba.it.vies.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.model.Travel;
import vies.uniba.it.vies.utils.DateUtils;

public class NewAlbumActivity extends AppCompatActivity {

    private TextInputEditText travelName;
    private TextInputEditText travelDate;
    private TextInputEditText travelLocationName;
    private TextInputEditText travelDescrizione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        travelName = (TextInputEditText) findViewById(R.id.travelNameInput);
        travelDate = (TextInputEditText) findViewById(R.id.travelDateInput);
        travelLocationName = (TextInputEditText) findViewById(R.id.travelLocationNameInput);
        travelDescrizione = (TextInputEditText) findViewById(R.id.travelDescrizioneInput);

        final Calendar dateOutCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateOutPicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateOutCalendar.set(Calendar.YEAR, year);
                dateOutCalendar.set(Calendar.MONTH, monthOfYear);
                dateOutCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                travelDate.setText(DateUtils.formatDate(dateOutCalendar.getTime()));
            }

        };


       /* final Calendar dateInCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateInPicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateInCalendar.set(Calendar.YEAR, year);
                dateInCalendar.set(Calendar.MONTH, monthOfYear);
                dateInCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                travelDateIn.setText(DateUtils.formatDate(dateInCalendar.getTime()));
            }

        };*/

       /* travelDateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(NewAlbumActivity.this, dateInPicker,
                        dateInCalendar.get(Calendar.YEAR),
                        dateInCalendar.get(Calendar.MONTH),
                        dateInCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

        travelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(NewAlbumActivity.this, dateOutPicker,
                        dateOutCalendar.get(Calendar.YEAR),
                        dateOutCalendar.get(Calendar.MONTH),
                        dateOutCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton saveTravelButton = (FloatingActionButton) findViewById(R.id.saveTravelButton);
        saveTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getClass().getCanonicalName(), "saveTravel <<START>>");

                Travel travel = new Travel();
                travel.setName(travelName.getText().toString());
                travel.setDateOut(dateOutCalendar.getTimeInMillis());
                //travel.setDateIn(dateInCalendar.getTimeInMillis());
                travel.getLocation().setName(travelLocationName.getText().toString());
                travel.setDescrizione(travelDescrizione.getText().toString());

                //CHECK SE VIENE INSERITA UNA LOCALITA' DIVERSA DA QUELLE PREDEFINITE == DA QUELLE NON PRESENTI NELLA GALLERIA

                Log.d("Comments", travelLocationName.getText().toString().toUpperCase());
                if (travelLocationName.getText().toString().toUpperCase().equals("PARIGI")
                        || travelLocationName.getText().toString().toUpperCase().equals("ROMA")
                        || travelLocationName.getText().toString().toUpperCase().equals("BARI")) {
                    Toast.makeText(getApplicationContext(), "Verranno caricate automaticamente tutte le foto scattate intorno a  "
                                    + travelLocationName.getText().toString()
                                    + ", nel periodo selezionato.",

                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Non è stata trovata nessuna foto scattata a "
                                    + travelLocationName.getText().toString()
                                    + ", nel periodo selezionato"
                                    + ". Sarà caricato l'album di default.",

                            Toast.LENGTH_LONG).show();
                }


                DBHelper.getInstance(getApplicationContext()).insertTravel(travel);
                finish();

                Log.d(getClass().getCanonicalName(), "saveTravel <<END>>");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
