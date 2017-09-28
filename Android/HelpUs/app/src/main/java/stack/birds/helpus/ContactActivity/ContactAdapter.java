package stack.birds.helpus.ContactActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import stack.birds.helpus.Item.User;
import stack.birds.helpus.R;

/**
 * Created by dsm2016 on 2017-08-03.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<User> UserList;
    private User User;
    private Context context;

    private String TAG = "contact adapter";
    
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;

        // 만약 마지막 item일 경우 버튼 추가
        public ContactViewHolder(View view, int viewType) {
            super(view);

            name = (TextView) view.findViewById(R.id.contact_name);
            number = (TextView) view.findViewById(R.id.contact_number);
        }
    }

    public ContactAdapter(List<User> UserList, Context context) {
        this.UserList = UserList;
        Log.d(TAG, "size is " + UserList.size());
        this.context = context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);
        return new ContactViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        User = UserList.get(position);
        holder.name.setText(User.getName());
        holder.number.setText(User.getPhoneNum());
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    private void showContactDialog() {
        Log.d(TAG, "Clicked");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.contact_input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView);

        final EditText id = (EditText) promptView.findViewById(R.id.dialog_id);
        final EditText name = (EditText) promptView.findViewById(R.id.dialog_name);
        final EditText phone = (EditText) promptView.findViewById(R.id.dialog_phone);
        builder.setCancelable(false)
                .setPositiveButton("연락처 추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Realm mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        
                        User user = mRealm.createObject(User.class);
                        user.setId(id.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPhoneNum(phone.getText().toString());
                        
                        mRealm.commitTransaction();
                        Log.d(TAG, "Contact add");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.create().show();

    }
}
