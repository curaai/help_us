package stack.birds.helpus.ContactActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        public Button button;

        // 만약 마지막 item일 경우 버튼 추가
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

    public ContactAdapter(List<User> UserList, Context context) {
        this.UserList = UserList;
        this.context = context;
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
        if(position != UserList.size() - 1) {
            User = UserList.get(position);
            holder.name.setText(User.getName());
            holder.number.setText(User.getPhoneNum());
        } else {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showContactDialog();
                }
            });
        }
    }

    // 마지막 아이템의 버튼은 클릭시 item들이 추가로 생김
    @Override
    public int getItemViewType(int position) {
        return (position == UserList.size() - 1)? 1 : 0;
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
