package comp3350.inventracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IRegistrationManager;

public class RegistrationActivity
    extends Activity {
    
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPassInput;
    private CheckBox isAdminInput;
    private TextView errorTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    
        usernameInput    = findViewById(R.id.username);
        isAdminInput     = findViewById(R.id.isAdmin);
        passwordInput    = findViewById(R.id.password);
        confirmPassInput = findViewById(R.id.passwordConfirm);
        errorTextView    = findViewById(R.id.errorMessage);
    }
    
    public void buttonConfirmRegistration(View v) {
        final String  username        = usernameInput.getText().toString();
        final boolean isAdmin         = isAdminInput.isChecked();
        final String  password        = passwordInput.getText().toString();
        final String  confirmPassword = this.confirmPassInput.getText().toString();
        
        try {
            Services.get(IRegistrationManager.class).RegisterNewUser(username, password, confirmPassword, isAdmin);
            onBackPressed();
        }
        catch (RegistrationException.Username e) {
            errorTextView.setText(e.getLocalizedMessage());
            usernameInput.requestFocus();
        }
        catch (RegistrationException.Password e) {
            errorTextView.setText(e.getLocalizedMessage());
            passwordInput.requestFocus();
        }
        catch (RegistrationException.PasswordConfirmation e) {
            errorTextView.setText(e.getLocalizedMessage());
            this.confirmPassInput.requestFocus();
        }
        catch (RegistrationException e) {
            errorTextView.setText(e.getLocalizedMessage());
        }
    }
}