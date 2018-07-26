package emergency.applications.speeddialforelderly;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static emergency.applications.speeddialforelderly.MainActivity.arrayList;
import static emergency.applications.speeddialforelderly.MainActivity.clearDatabase;
import static emergency.applications.speeddialforelderly.MainActivity.dbHelper;

public class TabSpeedDial extends Fragment implements View.OnClickListener{
    private ListView lvContacts;
    private ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_speed_dial, container, false);
        Button btnAddMore = rootView.findViewById(R.id.btn_add_more);
        Button btnEmergencyCall = rootView.findViewById(R.id.btn_emergency_sms);
        btnAddMore.setOnClickListener(this);
        btnEmergencyCall.setOnClickListener(this);
        lvContacts = rootView.findViewById(R.id.lv_contacts);
        arrayList.clear();
        MainActivity.fillArrayList();
        adapter = new ListViewAdapter(getContext(), arrayList);
        lvContacts.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btn_add_more:
                onClickAddMore();
                break;
            case R.id.btn_emergency_sms:
                clearDatabase("mytable");
                arrayList.clear();
                adapter.notifyDataSetChanged();
        }
    }

    public void onClickAddMore(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.add_contact, null);
        final EditText mName = mView.findViewById(R.id.et_name);
        final EditText mPhone = mView.findViewById(R.id.et_phone);
        final Button btnAddContact = mView.findViewById(R.id.btn_add_contact);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mName.getText().toString().isEmpty() && !mPhone.getText().toString().isEmpty()) {
                    String name = mName.getText().toString();
                    String phoneNumb = mPhone.getText().toString();
                    Model model = new Model(name, phoneNumb);

                    ContentValues cv = new ContentValues();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    if (name.equals("") || phoneNumb.equals("")) {
                        Log.d(this.getClass().getName(), "onClick, name or phone number empty");
                        Toast.makeText(getContext(), "empty fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cv.put("name", name);
                    cv.put("phone", phoneNumb);

                    lvContacts.deferNotifyDataSetChanged();
                    long rowID = db.insert("mytable", null, cv);

                    Log.d(this.getClass().getName(), "row inserted, ID = " + rowID);
                    arrayList.add(model);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "you should input some data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
