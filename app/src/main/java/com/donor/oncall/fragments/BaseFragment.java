package com.donor.oncall.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;

import com.donor.oncall.R;

/**
 * Created by prashanth on 3/1/16.
 */
public class BaseFragment extends Fragment {

    public void replaceViewFragment(Fragment fragment, boolean addToBackStack) {
        replaceViewFragment(R.id.container, fragment, addToBackStack);
    }

    protected void replaceViewFragment(int containerResourceId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_left_right,
                        R.animator.slide_right_left,
                        R.animator.slide_left_right,
                        R.animator.slide_right_left)
                .replace(containerResourceId, fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }


}
