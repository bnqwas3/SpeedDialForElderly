package emergency.applications.speeddialforelderly;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TabWhitelist extends Fragment implements View.OnClickListener {
    private ListView lvContactsWhite;
    public static ListViewWhiteAdapter adapterWhite;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_whitelist, container, false);
        Button btnDial = rootView.findViewById(R.id.btn_dial_number_whitelist);
        Button btnAddMore = rootView.findViewById(R.id.btn_add_more_whitelist);
        Button btnEmergencyCall = rootView.findViewById(R.id.btn_emergency_sms_whitelist);
        viewPager = getActivity().findViewById(R.id.container);
        btnAddMore.setOnClickListener(this);
        btnEmergencyCall.setOnClickListener(this);
        btnDial.setOnClickListener(this);
        lvContactsWhite = rootView.findViewById(R.id.lv_contacts);
        MainActivity.arrayListWhite.clear();
        MainActivity.fillArrayListWhite();
        adapterWhite = new ListViewWhiteAdapter(getContext(), MainActivity.arrayListWhite);
        lvContactsWhite.setAdapter(adapterWhite);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_add_more_whitelist:
                onClickAddMore();
                break;
            case R.id.btn_emergency_sms_whitelist:
                MainActivity.clearDatabaseWhite("mytablewhite");
                MainActivity.arrayListWhite.clear();
                adapterWhite.notifyDataSetChanged();
                break;
            case R.id.btn_dial_number_whitelist:
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
        SQLiteDatabase db = MainActivity.dbHelperWhite.getWritableDatabase();

        if (name.equals("") || phoneNumb.equals("")) {
            Log.d(this.getClass().getName(), "onClick, name or phone number empty");
            Toast.makeText(getContext(), "empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        cv.put("name", name);
        cv.put("phone", phoneNumb);

        long rowID = db.insert("mytablewhite", null, cv);

        Log.d(this.getClass().getName(), "row inserted, ID = " + rowID);
        MainActivity.arrayListWhite.add(model);
        adapterWhite.notifyDataSetChanged();
        dialog.dismiss();
    }
}
