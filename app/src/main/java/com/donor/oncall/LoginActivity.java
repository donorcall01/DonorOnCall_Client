package com.donor.oncall;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.donor.oncall.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean addToBackStack = true;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment());
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();

    }

   /* @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            this.getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }*/

}
