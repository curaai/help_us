package stack.birds.helpus.LandingActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import stack.birds.helpus.R;

public class LandingActivity extends AppCompatActivity {

    ViewPager pager;
    int images[] = {R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth};
    MyCustomPagerAdapter myCustomPagerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        pager = (ViewPager) findViewById(R.id.pager);

        myCustomPagerAdpater = new MyCustomPagerAdapter(getApplicationContext(), images);
        pager.setAdapter(myCustomPagerAdpater);
    }
}
