package stack.birds.helpus.LandingActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import stack.birds.helpus.R;

public class LandingActivity extends AppCompatActivity {

    int images[] = {R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth};
    String smallTextList[] = {"", "", "녹음된 음성파일들이", "우리손으로 만들어요"};
    String bigTextList[] = {"신고를 증거로", "버튼하나로 신고를", "증거로", "안전한 세상"};
    MyCustomPagerAdapter myCustomPagerAdpater;

    TextView smallText, bigText;
    ViewPager pager;
    LinearLayout over, first_view_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        pager = (ViewPager) findViewById(R.id.pager);

        myCustomPagerAdpater = new MyCustomPagerAdapter(getApplicationContext(), images);
        pager.setAdapter(myCustomPagerAdpater);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(pager, true);

        smallText = (TextView) findViewById(R.id.landing_text_small);
        bigText = (TextView) findViewById(R.id.landing_text_big);
        over = (LinearLayout) findViewById(R.id.landing_over);
        first_view_group = (LinearLayout) findViewById(R.id.landing_over);

        first_view_group.bringToFront();

        PagerThread thread = new PagerThread();
        thread.start();
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

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == 2) {

                        int position = pager.getCurrentItem();

                        if(position == images.length - 1) {
                            over.setVisibility(View.VISIBLE);
                        } else {
                            over.setVisibility(View.INVISIBLE);
                        }

                        if (smallTextList[position] == "") {
                            smallText.setTextSize(0);
                        } else {
                            smallText.setTextSize(36);
                            smallText.setText(smallTextList[position]);
                        }

                        bigText.setText(bigTextList[position]);
                        first_view_group.bringToFront();
                    }
                }
            });
        }
    }
}
