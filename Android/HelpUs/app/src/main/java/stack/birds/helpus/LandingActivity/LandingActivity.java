package stack.birds.helpus.LandingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import stack.birds.helpus.AccountActivity.LoginActivity;
import stack.birds.helpus.AccountActivity.SignupActivity;
import stack.birds.helpus.Adapter.LandingPagerAdapter;
import stack.birds.helpus.R;

public class LandingActivity extends AppCompatActivity {

    int images[] = {R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth};
    String smallTextList[] = {"", "", "녹음된 음성파일들이", "우리손으로 만들어요"};
    String bigTextList[] = {"신고를 증거로", "버튼하나로 신고를", "증거로", "안전한 세상"};
    LandingPagerAdapter landingPagerAdpater;

    TextView smallText, bigText;
    Button toSignin, toSignup;
    ViewPager pager;
    LinearLayout moveAnother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        pager = (ViewPager) findViewById(R.id.pager);

        landingPagerAdpater = new LandingPagerAdapter(getApplicationContext(), images);
        pager.setAdapter(landingPagerAdpater);
        // Landing pager 밑 dot 설정
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(pager, true);

        smallText = (TextView) findViewById(R.id.landing_text_small);
        bigText = (TextView) findViewById(R.id.landing_text_big);
        toSignin = (Button) findViewById(R.id.landing_to_login);
        toSignup = (Button) findViewById(R.id.landing_to_signup);
        moveAnother = (LinearLayout) findViewById(R.id.landing_to_another);

        moveAnother.setVisibility(View.INVISIBLE);

        btnInit();

        PagerThread thread = new PagerThread();
        thread.start();
    }

    // 버튼 클릭시 해당하는 액티비티로 이동
    private void btnInit() {
        toSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        toSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class PagerThread extends Thread {
        public void run() {
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                // 현재 페이지가 마지막 페이지 일경우 안보이게 해뒀던 버튼을 생성
                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 2) {

                        int position = pager.getCurrentItem();

                        if(position == images.length - 1) {
                            moveAnother.setVisibility(View.VISIBLE);
                        } else {
                            moveAnother.setVisibility(View.INVISIBLE);
                        }

                        // 작은 글씨가 없을 경우에는 smallText의 사이즈를 줄여 bigText가 위로 정렬되게 함
                        if (smallTextList[position] == "") {
                            smallText.setTextSize(0);
                        } else {
                            smallText.setTextSize(36);
                            smallText.setText(smallTextList[position]);
                        }

                        bigText.setText(bigTextList[position]);
                    }
                }
            });
        }
    }
}
