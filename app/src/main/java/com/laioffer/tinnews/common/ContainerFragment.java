package com.laioffer.tinnews.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.laioffer.tinnews.R;
import com.laioffer.tinnews.profile.TinProfileFragment;
import com.laioffer.tinnews.save.SavedNewsFragment;
import com.laioffer.tinnews.tin.TinGalleryFragment;

public class ContainerFragment extends TinBasicFragment {
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //define containerFragment index and tag
    public static final int HOME_PAGE = 0;
    public static final String HOME_PAGE_TAG = "home_page";
    public static final int SAVE_PAGE = 1;
    public static final String SAVE_PAGE_TAG = "save_page";
    public static final int PROFILE_PAGE = 2;
    public static final String PROFILE_PAGE_TAG = "profile_page";

    private int pageIndex;

    //add initFragment field
    private Fragment initFragment;


    public static ContainerFragment newInstance(int pageIndex) {
        ContainerFragment containerFragment = new ContainerFragment();
        containerFragment.pageIndex = pageIndex;
        //pass the value
        containerFragment.initFragment = createInitFragmentByIndex(pageIndex);
        return containerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate layout with child_fragment_container
        return inflater.inflate(R.layout.child_fragment_container, container, false);
    }

    //added to the containerFragment
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (initFragment != null && !initFragment.isAdded()) {
            // replace child_fragment_container with initFragment, in this case, it is saveNewsFragment
            getChildFragmentManager().beginTransaction().replace(R.id.child_fragment_container, initFragment, getCurrentTag(pageIndex))
                    .commit();
        }
    }

    //add fragment tags to the containerFragment
    public static String getCurrentTag(int position) {
        switch (position) {
            case HOME_PAGE:
                return HOME_PAGE_TAG;
            case SAVE_PAGE:
                return SAVE_PAGE_TAG;
            case PROFILE_PAGE:
                return PROFILE_PAGE_TAG;
            default:
                return null;
        }
    }



    //add position index
    public static int getPositionById(int id) {
        switch (id) {
            case R.id.action_tin:
                return HOME_PAGE;
            case R.id.action_save:
                return SAVE_PAGE;
            case R.id.action_profile:
                return PROFILE_PAGE;
            default:
                throw new IndexOutOfBoundsException();
        }
    }



    private static Fragment createInitFragmentByIndex(int pageIndex) {
        switch (pageIndex) {
            case HOME_PAGE:
                return TinGalleryFragment.newInstance();
            case SAVE_PAGE:
                return SavedNewsFragment.newInstance();
            case PROFILE_PAGE:
                return TinProfileFragment.newInstance();
            default:
                throw new IndexOutOfBoundsException();
        }
    }


}
