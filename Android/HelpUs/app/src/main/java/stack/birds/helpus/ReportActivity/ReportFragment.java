package stack.birds.helpus.ReportActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import stack.birds.helpus.Item.Report;
import stack.birds.helpus.Item.User;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class ReportFragment extends Fragment {
    View view;
    Realm mRealm;
    private String TAG = "REPORT";

    ExpandableRelativeLayout expandableLayout1, expandableLayout2;

    private BottomSheetBehavior bottomSheetBehavior;
    private TabLayout tabLayout;
    private String[] tabNames = {"녹음", "사진", "비디오"};
    private ViewPager viewPager;
    private ReportPagerAdapter pagerAdapter;
    private String URI = "https://dmlwlsdk07.000webhostapp.com/push_notification.php";

    private EditText title, content;
    private Button reportButton, contactButton, expandableButton1, expandableButton2;

    List<String> recordList;
    List<String> pictureList;
    static List<User> userList;
    String users;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        mRealm = Realm.getDefaultInstance();

        // bottom sheet and tab Layout initialize
        initLayout();
        userList = new ArrayList<User>();

        title = (EditText) view.findViewById(R.id.report_title);
        content = (EditText) view.findViewById(R.id.report_content);
        reportButton = (Button) view.findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report();
            }
        });
        contactButton = (Button) view.findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = selectReceiver();
                Log.d(TAG, FirebaseInstanceId.getInstance().getToken());

            }
        });

        expandableLayout1 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout1);
        expandableButton1 = (Button) view.findViewById(R.id.expandableButton1);
        expandableButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableLayout2.isExpanded()) {
                    expandableLayout2.collapse();
                }
                expandableLayout1.toggle(); // toggle expand and collapse
            }
        });

        expandableLayout2 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout2);
        expandableButton2 = (Button) view.findViewById(R.id.expandableButton2);
        expandableButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableLayout1.isExpanded()) {
                    expandableLayout1.collapse();
                }
                expandableLayout2.toggle(); // toggle expand and collapse

            }
        });

        
        return view;
    }

    void initLayout() {
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


        ReportSingle single = ReportSingle.getInstance();
        pictureList = single.getPicture();
        recordList = single.getRecord();

        uploadFiles();
    }

    public List<User> getUserList() {
        RealmResults<User> res = mRealm.where(User.class).findAll();
        return mRealm.copyFromRealm(res);
    }

    public ArrayList<User> selectReceiver() {
        ArrayList<String> itemList = new ArrayList<String>();
        final List<User> users = getUserList();
        for(User user: users) {
            itemList.add(user.getName());
        }
        CharSequence[] items = itemList.toArray(new CharSequence[itemList.size()]);
        // arraylist to keep the selected items
        final ArrayList<User> selectUser = new ArrayList<User>();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Select The Difficulty Level")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if(selectUser.size() < 4) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                User user = users.get(indexSelected);
                                selectUser.add(user);
                            } else if (selectUser.contains(users.get(indexSelected))) {
                                // Else, if the item is already in the array, remove it
                                selectUser.remove(indexSelected);
                            }
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
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        final MediaType MEDIA_TYPE_MP3 = MediaType.parse("audio/*");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        SharedPreferences login = getContext().getSharedPreferences("auto_login", Activity.MODE_PRIVATE);
        String ID = login.getString("id", null);

        //title
        final String reportTitle = title.getText().toString();

        //content
        final String reportContent = content.getText().toString();

        // receivers
        users = "";
        for(User user: userList) {
            users += user.getId() + ",";
        }

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", ID)
                .addFormDataPart("title", reportTitle)
                .addFormDataPart("content", reportContent)
                .addFormDataPart("receivers", "aaaaaa");

        if (pictureList != null) {
            for(int i = 0; i < pictureList.size(); i++) {
                File f = new File(pictureList.get(i));
                body.addFormDataPart("image" + (i + 1), pictureList.get(i), RequestBody.create(MEDIA_TYPE_PNG, f));
            }
        }
        if (recordList != null) {
            for(int i = 0; i < recordList.size(); i++) {
                File f = new File(recordList.get(i));
                body.addFormDataPart("music" + (i + 1), recordList.get(i), RequestBody.create(MEDIA_TYPE_MP3, f));
            }
        }

        Request request = new Request.Builder()
                .url(URI)
                .post(body.build())
                .build();

        OkHttpClient client = new OkHttpClient();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.toString());
                if (response.toString().contains("200")) {
                    // if success to upload save reported data
                    Realm mRealm = Realm.getDefaultInstance();
                    mRealm.beginTransaction();
                    
                    Report report = mRealm.createObject(Report.class);
                    report.setTitle(reportTitle);
                    report.setContent(reportContent);
                    report.setReceivers(users);
                    if(!recordList.get(0).isEmpty()) {
                        report.setMusic1(recordList.get(0));    
                    }
                    if(!recordList.get(1).isEmpty()) {
                        report.setMusic2(recordList.get(1));
                    }
                    if(!recordList.get(2).isEmpty()) {
                        report.setMusic3(recordList.get(2));
                    }
                    if(!pictureList.get(0).isEmpty()) {
                        report.setImage1(pictureList.get(0));
                    }
                    if(!pictureList.get(1).isEmpty()) {
                        report.setImage2(pictureList.get(1));
                    }
                    if(!pictureList.get(2).isEmpty()) {
                        report.setImage3(pictureList.get(2));
                    }

                    mRealm.commitTransaction();
                }
            }

        });

    }
}
