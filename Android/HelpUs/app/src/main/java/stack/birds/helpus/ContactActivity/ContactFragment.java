package stack.birds.helpus.ContactActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import stack.birds.helpus.Item.User;
import stack.birds.helpus.R;

/**
 * Created by sch on 2017-07-31.
 */

public class ContactFragment extends Fragment {

    private String TAG = "contact";
    View view;
    Realm mRealm;

    ContactAdapter contactAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contactAdapter = new ContactAdapter(getUserList(), getActivity());
        recyclerView.setAdapter(contactAdapter);
    }

    private List<User> getUserList() {
        mRealm = Realm.getDefaultInstance();
        RealmResults<User> result = mRealm.where(User.class).findAll();
        List<User> list = mRealm.copyFromRealm(result);
        Log.d(TAG, "user list size is " + list.size());
        // last item is contact add button
        list.add(null);
        return list;
    }
}