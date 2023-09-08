package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.AppPersistence;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.logic.IUserLoginManager;

public class LoginActivity
    extends Activity {
    
    private EditText usernameInput;
    private EditText passwordInput;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppPersistence.copyDatabaseToDevice(this, getApplicationContext(), getAssets(), getString(R.string.db));
        
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
    }
    
    public void buttonLogin(View v) {
        try {
            final String username =  usernameInput.getText().toString();
            final String password =  passwordInput.getText().toString();
            
            Services.get(IUserLoginManager.class)
                    .Login(username, password);
    
            enterApp();
        }
        catch (InvalidCredentialsException e) {
            Log.e("LoginActivity", "Failed to Login", e);
            passwordInput.setText("");
            
            try {
                TextView errorTextView = findViewById(R.id.errorMessage);
                errorTextView.setText(e.getLocalizedMessage());
            }
            catch (Exception ex) {
                Log.w("LoginActivity", "Failed to set error message", ex);
            }
        }
    }
    
    public void buttonRegister(View v) {
        Intent homeIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        LoginActivity.this.startActivity(homeIntent);
    }
    
    void enterApp() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(homeIntent);
        finish();
    }
}
