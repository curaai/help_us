package stack.birds.helpus.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import stack.birds.helpus.R;
import stack.birds.helpus.Service.AccountService;

/**
 * Created by dsm2016 on 2017-07-31.
 */

public class SettingFragment extends Fragment implements View.OnClickListener{

    View view;

    ImageView logoutImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_setting, container, false);

        initWidgets();

        return view;
    }

    private void initWidgets() {
//        logoutImage = (ImageView) view.findViewById(R.id.logout_img);
//        logoutImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.logout_img:
//                logout();
//                break;


        }
    }

    public void logout() {
        AccountService account = new AccountService(getContext());
        account.logout();
    }


}
