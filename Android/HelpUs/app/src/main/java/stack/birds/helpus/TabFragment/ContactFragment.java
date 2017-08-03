package stack.birds.helpus.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import stack.birds.helpus.Adapter.ContactAdapter;
import stack.birds.helpus.Class.User;
import stack.birds.helpus.Helper.UserDBHelper;
import stack.birds.helpus.R;

/**
 * Created by sch on 2017-07-31.
 */

public class ContactFragment extends Fragment {

    View view;

    private List<User> userList;
    UserDBHelper userDBHelper;
    ContactAdapter contactAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        userList = new ArrayList<User>();

        userList.add(new User("필성", "zhsir123", "010-8976-5447"));
        userList.add(new User("재빈", "zhsir00", "010-1234-5678"));
        userList.add(new User("근철", "zhsir00", "010-9876-5432"));

        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler);
        contactAdapter = new ContactAdapter(userList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
    }
}