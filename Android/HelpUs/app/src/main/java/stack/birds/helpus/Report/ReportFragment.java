package stack.birds.helpus.Report;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment {
    View view;

    private BottomSheetBehavior bottomSheetBehavior;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ReportPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        initLayout();

        return view;
    }

    void initLayout() {
        // bottom sheet init
        View bottomLayout = view.findViewById(R.id.report_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomLayout);
        bottomSheetBehavior.setHideable(true); // bottom sheet이 숨겨진 채로 시작

        tabLayout = (TabLayout) view.findViewById(R.id.report_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("녹음"));
        tabLayout.addTab(tabLayout.newTab().setText("사진"));
        tabLayout.addTab(tabLayout.newTab().setText("동영상"));

        // tab Layout 설정
        viewPager = (ViewPager) view.findViewById(R.id.report_pager);
        pagerAdapter = new ReportPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 탭이 선택됬을 때 해당 viewPager tab으로 이동
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
