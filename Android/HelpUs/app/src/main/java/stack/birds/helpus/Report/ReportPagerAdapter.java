package stack.birds.helpus.Report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dsm2016 on 2017-08-25.
 */

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public ReportPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReportRecordFragment tabFragment1 = new ReportRecordFragment();
                return tabFragment1;
            case 1:
                ReportPictureFragment tabFragment2 = new ReportPictureFragment();
                return tabFragment2;
            case 2:
                ReportVideoFragment tabFragment3 = new ReportVideoFragment();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
