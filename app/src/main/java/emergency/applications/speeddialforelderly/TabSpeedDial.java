package emergency.applications.speeddialforelderly;

import android.app.AlertDialog;
import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static emergency.applications.speeddialforelderly.MainActivity.arrayList;
import static emergency.applications.speeddialforelderly.MainActivity.clearDatabase;
import static emergency.applications.speeddialforelderly.MainActivity.dbHelper;

public class TabSpeedDial extends Fragment implements View.OnClickListener {
    private ListView lvContacts;
    public static ListViewAdapter adapter;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_speed_dial, container, false);
        Button btnDial = rootView.findViewById(R.id.btn_dial_number);
        Button btnAddMore = rootView.findViewById(R.id.btn_add_more);
        Button btnEmergencyCall = rootView.findViewById(R.id.btn_emergency_sms);
        viewPager = getActivity().findViewById(R.id.container);
        btnAddMore.setOnClickListener(this);
        btnEmergencyCall.setOnClickListener(this);
        btnDial.setOnClickListener(this);
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
                break;
            case R.id.btn_dial_number:
                viewPager.setCurrentItem(1);
                break;

        }
    }

    private void onClickAddMore(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        final View mView = getLayoutInflater().inflate(R.layout.add_contact, null);
        final Button btnAddContact = mView.findViewById(R.id.btn_add_contact);
        final EditText mName = mView.findViewById(R.id.et_name);
        final EditText mPhone = mView.findViewById(R.id.et_phone);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mName.getText().toString().isEmpty() && !mPhone.getText().toString().isEmpty()) {
                    String name = mName.getText().toString();
                    String phoneNumb = mPhone.getText().toString();
                    insertContact(mView, dialog, name, phoneNumb);
                } else {
                    Toast.makeText(getContext(), "empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void insertContact(View mView, AlertDialog dialog, String name, String phoneNumb){

        Model model = new Model(name, phoneNumb);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (name.equals("") || phoneNumb.equals("")) {
            Log.d(this.getClass().getName(), "onClick, name or phone number empty");
            Toast.makeText(getContext(), "empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        cv.put("name", name);
        cv.put("phone", phoneNumb);

        long rowID = db.insert("mytable", null, cv);

        Log.d(this.getClass().getName(), "row inserted, ID = " + rowID);
        arrayList.add(model);
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}
