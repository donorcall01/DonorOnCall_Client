package com.donor.oncall;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.donor.oncall.fragments.LoginFragment;
import com.donor.oncall.fragments.RegisterationFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean addToBackStack = true;
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment());
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();

    }

}
