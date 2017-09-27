package stack.birds.helpus.ReportActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-25.
 */

public class ReportPictureFragment extends Fragment{
    View view;
    Context context;

    RecyclerView recyclerView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private PictureAdapter adapter;

    public List<String> imageList;

    @SuppressLint("ValidFragment")
    public ReportPictureFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_tab_picture, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.report_picture_list);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        imageList = getImagesFromPhone(context);
        Log.d("picture", imageList.toString());
        adapter = new PictureAdapter(imageList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public static List<String> getImagesFromPhone(Context context) {
        final String[] projection = {MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        ArrayList<String> result = new ArrayList<String>(cursor.getCount());
        int dataColumnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(dataColumnIndex);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    public List<String> getReportData() {
        List<Integer> clicked = adapter.getIsClicked();
        List<String> clickedImage = new ArrayList<String>();
        for (int i = 0; i < clicked.size(); i++){
            clickedImage.add(imageList.get(clicked.get(i)));
        }
        return clickedImage;
    }
}

class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder>{
    private List<Integer> isClicked;
    private List<String> picturesPath;

    class PictureViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public PictureViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.report_picture_image);
        }
    }


    public PictureAdapter(List<String> picturePath) {
        this.picturesPath = picturePath;
    }

    @Override
    public PictureAdapter.PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.report_tab_picture_item, parent, false);
        isClicked = new ArrayList<Integer>();

        return new PictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PictureAdapter.PictureViewHolder holder, final int position) {
        String path = picturesPath.get(position);

        holder.image.setImageBitmap(BitmapFactory.decodeFile(path));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked.contains(position)) {
                    isClicked.remove(Integer.valueOf(position));
                } else {
                    isClicked.add(position);
                    holder.image.setBackgroundColor(Color.CYAN);
                    holder.image.setColorFilter(Color.rgb(0, 0, 0));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return picturesPath.size();
    }

    public List<Integer> getIsClicked() {
        return isClicked;
    }
}
