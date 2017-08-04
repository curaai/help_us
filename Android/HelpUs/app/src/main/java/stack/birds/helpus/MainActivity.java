package stack.birds.helpus;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import stack.birds.helpus.Adapter.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    MainPagerAdapter pagerAdapter;

    static final int PERMISSION_REQUEST_CODE = 1;
    public static String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    final int[] icons = new int[] {R.drawable.home, R.drawable.send, R.drawable.list, R.drawable.icon, R.drawable.setting};

    private boolean hasPermissions(String[] permissions) {
        int res = 0;

        // String Array의 퍼미션 체크
        for(String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            // permission이 허가 안된경우
            if( !(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }

        // permission이 허가 됫을 떄
        return true;
    }

    private void requestNecessaryPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 사용자가 퍼미션이 없을 경우 허락을 받소 시작함
        if (!hasPermissions(PERMISSIONS)) {
            requestNecessaryPermissions(PERMISSIONS);
        }

        //  테스팅 시에는 잠시 취소
//        final AccountService account = new AccountService(getApplicationContext());
//        int result = account.autoLogin();
//        // 만약 자동로그인이 안되어있을 시에 LandingActivity 로 넘어감
//        if(result != 1) {
//            Intent intent = new Intent(this, LandingActivity.class);
//            startActivity(intent);
//            finish();
//        }

        // 메인 액티비티의 탭
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("HOME"));
        tabLayout.addTab(tabLayout.newTab().setText("신고하기"));
        tabLayout.addTab(tabLayout.newTab().setText("신고내역"));
        tabLayout.addTab(tabLayout.newTab().setText("신고접수"));
        tabLayout.addTab(tabLayout.newTab().setText("설정"));

        // 각 탭의 아이콘 설정
        for(int i = 0; i < icons.length; i++)
            tabLayout.getTabAt(i).setIcon(icons[i]);

        // viewpager 생성 밑 mainPagerAdapter 설정
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        pagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 탭이 선택됬을 때 해당 viewPager page로 이동
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