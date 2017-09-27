package stack.birds.helpus.ReceiveActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import stack.birds.helpus.Item.Receive;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class ReceiveFragment extends Fragment {
    View view;
    Realm mRealm;

    List<Receive> receiveList;
    RecyclerView recyclerView;
    ReceiveAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_list, container, false);
        mRealm = Realm.getInstance(getActivity());

        initRecyclerLayout();

        return view;
    }

    public void initRecyclerLayout() {
        // TODO report_recycler_view -> receive recycler_view
        // get report receive data from Realm Database
        receiveList = getReceiveList();
        adapter = new ReceiveAdapter(receiveList);

        recyclerView = (RecyclerView) view.findViewById(R.id.report_recycler_view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public List<Receive> getReceiveList() {
        RealmResults<Receive> res = mRealm.where(Receive.class).findAll();
        return res.subList(0, res.size());
    }
}
