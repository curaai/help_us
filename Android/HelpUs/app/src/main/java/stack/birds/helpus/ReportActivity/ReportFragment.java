package stack.birds.helpus.ReportActivity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import stack.birds.helpus.Item.Record;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment {
    View view;

    private BottomSheetBehavior bottomSheetBehavior;
    private TabLayout tabLayout;
    private String[] tabNames = {"녹음", "사진"};
    private ViewPager viewPager;
    private ReportPagerAdapter pagerAdapter;

    private EditText title, content;
    private Button reportButton;

    int currentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);

        // bottom sheet and tab Layout initialize
        initLayout();

        title = (EditText) view.findViewById(R.id.report_title);
        content = (EditText) view.findViewById(R.id.report_content);
        reportButton = (Button) view.findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        // tab Layout 설정
        currentPage = 0;
        viewPager = (ViewPager) view.findViewById(R.id.report_pager);
        pagerAdapter = new ReportPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),
                getContext());
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

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    public void report() {
        List<Record> recordPath;
        List<String> picturePath;

        ReportPictureFragment reportFragment = (ReportPictureFragment) getActivity().getSupportFragmentManager().
                findFragmentByTag(makeFragmentName(R.id.report_pager, 0));
        picturePath = reportFragment.getReportData();

        ReportRecordFragment recordFragment = (ReportRecordFragment) getActivity().getSupportFragmentManager().
                findFragmentByTag(makeFragmentName(R.id.report_pager, 1);
        recordPath = recordFragment.getReportData();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    }
}
