package stack.birds.helpus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import stack.birds.helpus.ReceiveActivity.ReceiveFragment;
import stack.birds.helpus.ReportActivity.ReportFragment;
import stack.birds.helpus.TabFragment.ContactFragment;
import stack.birds.helpus.TabFragment.ReportListFragment;
import stack.birds.helpus.TabFragment.SettingFragment;

/**
 * Created by sch on 2017-07-31.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            case 1:
                ReportFragment reportFragment = new ReportFragment();
                return reportFragment;
            case 2:
                ReportListFragment reportListFragment = new ReportListFragment();
                return reportListFragment;
            case 3:
                ReceiveFragment receiveFragment = new ReceiveFragment();
                return receiveFragment;
            case 4:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
