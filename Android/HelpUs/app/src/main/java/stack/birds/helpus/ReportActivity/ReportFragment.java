package stack.birds.helpus.ReportActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import stack.birds.helpus.Item.Record;
import stack.birds.helpus.Item.User;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment {
    View view;
    Realm mRealm;

    private BottomSheetBehavior bottomSheetBehavior;
    private TabLayout tabLayout;
    private String[] tabNames = {"녹음", "사진", "비디오"};
    private ViewPager viewPager;
    private ReportPagerAdapter pagerAdapter;
    private String URI = "asdf";

    private EditText title, content;
    private Button reportButton, contactButton;

    List<Record> recordList;
    List<String> pictureList;
    List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        mRealm = Realm.getDefaultInstance();

        // bottom sheet and tab Layout initialize
        initLayout();

        title = (EditText) view.findViewById(R.id.report_title);
        content = (EditText) view.findViewById(R.id.report_content);
        reportButton = (Button) view.findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userList.size() == 0) {
                    Toast.makeText(getActivity(), "신고를 받을 대상을 지정해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    report();
                }
            }
        });
        contactButton = (Button) view.findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = selectReceiver();
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


        ReportPictureFragment reportFragment = (ReportPictureFragment) getActivity().getSupportFragmentManager().
                findFragmentByTag(makeFragmentName(R.id.report_pager, 0));
        pictureList = reportFragment.getReportData();

        ReportRecordFragment recordFragment = (ReportRecordFragment) getActivity().getSupportFragmentManager().
                findFragmentByTag(makeFragmentName(R.id.report_pager, 1));
        recordList = recordFragment.getReportData();


        uploadFiles();
    }

    public List<User> getUserList() {
        RealmResults<User> res = mRealm.where(User.class).findAll();
        return res.subList(0, res.size());
    }

    public ArrayList<User> selectReceiver() {
        ArrayList<String> itemList = new ArrayList<String>();
        userList = getUserList();

        CharSequence[] items = itemList.toArray(new CharSequence[itemList.size()]);
        // arraylist to keep the selected items
        final ArrayList<User> selectUser = new ArrayList<User>();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Select The Difficulty Level")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectUser.add(userList.get(indexSelected));
                        } else if (selectUser.contains(userList.get(indexSelected))) {
                            // Else, if the item is already in the array, remove it
                            selectUser.remove(indexSelected);
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();

        return selectUser;
    }

    private void uploadFiles() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        SharedPreferences login = getContext().getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
        String ID = login.getString("id", null);
        try {
            // id
            builder.addPart("id", new StringBody(ID));

            //title
            String reportTitle = title.getText().toString();
            builder.addPart("title", new StringBody(reportTitle));

            //content
            String reportContent = content.getText().toString();
            builder.addPart("content", new StringBody(reportContent));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // upload picture files
        for(int i = 0; i < pictureList.size(); i++) {
            builder.addPart("picture" + i, new FileBody(new File(pictureList.get(i))));
        }

        // upload mp3 files
        for(int i = 0; i < recordList.size(); i++) {
            builder.addPart("music" + i, new FileBody(new File(recordList.get(i).getFilePath())));
        }

        // receivers
        String users = "";
        for(User user: userList) {
            users += user.getId() + ",";
        }

        HttpClient client = AndroidHttpClient.newInstance("Android");
        HttpPost post = new HttpPost(URI);
        String response = "";
        try {
            post.setEntity(builder.build()); //builder.build() 메쏘드를 사용하여 httpEntity 객체를 얻는다.
            HttpResponse httpRes;
            httpRes = client.execute(post);
            HttpEntity httpEntity = httpRes.getEntity();
            if (httpEntity != null) {
                response = EntityUtils.toString(httpEntity);
            }
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e1) {
        } catch (IOException e1) {
        } catch (ParseException e) {
        }
    }
}
