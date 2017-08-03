package stack.birds.helpus.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ContactAdapter(List<User> userList) {
        this.userList = userList;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;

        public ContactViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.contact_name);
            number = (TextView) view.findViewById(R.id.contact_number);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        user = userList.get(position);
        holder.name.setText(user.getName());
        holder.number.setText(user.getPhoneNum());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
