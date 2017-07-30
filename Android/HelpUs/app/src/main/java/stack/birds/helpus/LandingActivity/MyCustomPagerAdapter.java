package stack.birds.helpus.LandingActivity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-29.
 */

public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    int images[];
    public MyCustomPagerAdapter(Context context, int images[]) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // viewpager로 보여줄 xml 파일
        View itemView = layoutInflater.inflate(R.layout.landing_viewpager_item, container, false);

        // pager안에 들어가는 내부 view
        ImageView imageView = (ImageView) itemView.findViewById(R.id.pager_image);
        Glide.with(context).load(images[position]).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
