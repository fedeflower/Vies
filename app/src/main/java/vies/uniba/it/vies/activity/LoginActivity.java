package vies.uniba.it.vies.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.model.Login;
import vies.uniba.it.vies.util.Prefs;

public class LoginActivity extends AppCompatActivity {


    private TextInputEditText mEmailView;
    private TextInputEditText mPasswordView;
    private View mLoginFormView;
    private String username;
    final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            // Set up the login form.
            mEmailView = (TextInputEditText) findViewById(R.id.email);
            mPasswordView = (TextInputEditText) findViewById(R.id.password);

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login();
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
        }

        private void Login(){
            username=mEmailView.getText().toString();
            if(username.length()>0){
                Login.getInstance().setUsername(username);
                Prefs.getInstance(this).edit().putBoolean("logged_in", true).commit();
                Prefs.getInstance(this).edit().putString("username", username).commit();
                Log.d("Comments", "Log");
                startActivity(new Intent(this,MainActivity.class));
            }
        }

}
