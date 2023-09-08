package comp3350.inventracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IPasswordUpdateManager;
import comp3350.inventracker.logic.IRestrictionsManager;

public class PasswordUpdateActivity
    extends Activity {
    
    private EditText oldPasswordInput;
    private EditText newPasswordInput;
    private EditText confirmPassInput;
    private TextView errorTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    
        oldPasswordInput = findViewById(R.id.oldPassword);
        newPasswordInput = findViewById(R.id.newPassword);
        confirmPassInput = findViewById(R.id.passwordConfirm);
        errorTextView    = findViewById(R.id.errorMessage);
    }
    
    public void buttonUpdateAccount(View v) {
        final String oldPassword     = oldPasswordInput.getText().toString();
        final String newPassword     = this.newPasswordInput.getText().toString();
        final String confirmPassword = this.confirmPassInput.getText().toString();
        
        try {
            
            String username = Services.get(IRestrictionsManager.class).getUsername();
            
            Services.get(IPasswordUpdateManager.class)
                    .UpdatePassword(username, oldPassword, newPassword, confirmPassword);
            
            onBackPressed();
        }
        catch (RegistrationException.Password e) {
            errorTextView.setText(e.getLocalizedMessage());
            newPasswordInput.requestFocus();
        }
        catch (RegistrationException.PasswordConfirmation e) {
            errorTextView.setText(e.getLocalizedMessage());
            this.confirmPassInput.requestFocus();
        }
        catch (InvalidCredentialsException e) {
            errorTextView.setText(e.getLocalizedMessage());
            this.oldPasswordInput.requestFocus();
        }
    }
}