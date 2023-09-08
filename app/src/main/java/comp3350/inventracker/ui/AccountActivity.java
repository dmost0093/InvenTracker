package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.logic.IUserLoginManager;

public class AccountActivity
    extends Activity {
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
    
    public void buttonChangePassword(final View v)
    {
        startActivity(new Intent(this, PasswordUpdateActivity.class));
    }
    
    public void buttonLogout(final View view) {
        Services.get(IUserLoginManager.class).LogOut();
        
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
