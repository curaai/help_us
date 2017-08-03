package stack.birds.helpus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import stack.birds.helpus.Class.User;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-03.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<User> userList;
    private User user;

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;
        public Button button;

        public ContactViewHolder(View view, int viewType) {
            super(view);

            if (viewType == 0) {
                name = (TextView) view.findViewById(R.id.contact_name);
                number = (TextView) view.findViewById(R.id.contact_number);
            } else {
                button = (Button) view.findViewById(R.id.contact_add_btn);
            }

        }
    }

    public ContactAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_list_row, parent, false);
                return new ContactViewHolder(view, viewType);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_button_row, parent, false);
                return new ContactViewHolder(view, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if(position != userList.size() - 1) {
            user = userList.get(position);
            holder.name.setText(user.getName());
            holder.number.setText(user.getPhoneNum());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userList.size() - 1)? 1 : 0;
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
