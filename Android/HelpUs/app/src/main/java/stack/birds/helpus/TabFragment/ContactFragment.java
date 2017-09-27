package stack.birds.helpus.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stack.birds.helpus.Adapter.ContactAdapter;
import stack.birds.helpus.R;

/**
 * Created by sch on 2017-07-31.
 */

public class ContactFragment extends Fragment {

    View view;

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
        recyclerView.setAdapter(contactAdapter);
    }
}