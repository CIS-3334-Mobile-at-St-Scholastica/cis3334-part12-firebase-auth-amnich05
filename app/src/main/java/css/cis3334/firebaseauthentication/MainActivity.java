package css.cis3334.firebaseauthentication;

/**
 * Updated by: Amanda Nichols
 * Date: April 18, 2018
 * Subject: CIS3334 Mobile Device Programming
 * Participation 12
 */

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    /**
     * Declare TextView, EditText, Button, and FirebaseAuth variables
     */
    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    /**
     * Create the mobile application
     * User will be able to create an account, sign in, and log out
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initialize variables
         * Link variables to buttons and fields
         */
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();         // start Firebase

        /**
         * Set up the Sign In button
         * User will enter email and password
         * When button is clicked, get user input
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // when Normal Login button is clicked, get user email and password
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        /**
         * Set up the Create Account button
         * User will enter email and password
         * When button is clicked, get user input
         */
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // when Create Account button is clicked, get user email and password
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        /**
         * Set up Google Sign In button
         * User will be able to log in using their Google account information
         */
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // when Google Login button is clicked, user can sign in with Google Account
                googleSignIn();
            }
        });

        /**
         * Set up Sign Out button
         * When button is clicked, user will be signed out
         */
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // when Sign Out button is clicked, sign out user
                signOut();
            }
        });
    }

    /**
     * When the application starts up,
     * check if user is signed in or signed out
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) { // if user is in the database
            // User is signed in
            textViewStatus.setText("Signed In");
        } else { // if user is not in the database
            // User is signed out
            textViewStatus.setText("Signed Out");
        }
    }

    /**
     * Creates a new user account
     * New user will input email and password
     * User email and password will be saved to Firebase database
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        // create user with email and password in the database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // user is signed in
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                            // user signed out
                            textViewStatus.setText("Signed Out");
                        }
                    }
                });
    }

    /**
     * Sign in the  user with email and password
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        // sign user in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // if email and password matches what is in the database
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // user signed in
                            textViewStatus.setText("Signed In");
                        } else { // if email and password do not match what is in the database
                            // If sign in fails, display a message to the user.
                            // user signed out
                            textViewStatus.setText("Signed Out");
                        }
                    }
                });
    }

    /**
     * Sign out the user
     */
    private void signOut () {
        // sign user out of database and application
        mAuth.signOut();
        textViewStatus.setText("Signed Out");
    }

    /**
     * User can sign in using their Google account information
     */
    private void googleSignIn() {

    }


}
