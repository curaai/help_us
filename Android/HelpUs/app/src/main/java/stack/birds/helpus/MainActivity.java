package stack.birds.helpus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import stack.birds.helpus.Adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  테스팅 시에는 잠시 취소
//        final Account account = new Account(getApplicationContext());
//        int result = account.autoLogin();
//        // 만약 자동로그인이 안되어있을 시에 LandingActivity 로 넘어감
//        if(result != 1) {
//            Intent intent = new Intent(this, LandingActivity.class);
//            startActivity(intent);
//            finish();
//        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("신고하기"));
        tabLayout.addTab(tabLayout.newTab().setText("신고내역"));
        tabLayout.addTab(tabLayout.newTab().setText("신고접수"));
        tabLayout.addTab(tabLayout.newTab().setText("설정"));

        viewPager = (ViewPager) findViewById(R.id.main_pager);
        pagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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