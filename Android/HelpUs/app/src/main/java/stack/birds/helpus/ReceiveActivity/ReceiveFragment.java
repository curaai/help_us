package stack.birds.helpus.ReceiveActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class ReceiveFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_list, container, false);

        return view;
    }
}
