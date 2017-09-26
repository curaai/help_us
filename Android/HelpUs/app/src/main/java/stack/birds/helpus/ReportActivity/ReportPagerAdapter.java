package stack.birds.helpus.ReportActivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dsm2016 on 2017-08-25.
 */

public class ReportPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private Context context;

    public ReportPagerAdapter(FragmentManager fm, int tabCount, Context context) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReportRecordFragment tabFragment1 = new ReportRecordFragment(context);
                return tabFragment1;
            case 1:
                ReportPictureFragment tabFragment2 = new ReportPictureFragment(context);
                return tabFragment2;
            case 2:
                ReportVideoFragment tabFragment3 = new ReportVideoFragment(context);
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
