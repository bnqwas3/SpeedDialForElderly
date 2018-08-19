package emergency.applications.speeddialforelderly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;

import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        MainActivity.arrayList.clear();
        MainActivity.fillArrayList();
        adapter = new ListViewAdapter(getContext(), MainActivity.arrayList);
        lvContacts.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_more:
                onClickAddMore();
                break;
            case R.id.btn_emergency_sms:
                onClickEmergencyCall();

                break;
            case R.id.btn_dial_number:
                viewPager.setCurrentItem(1);
                break;

        }
    }

    private void onClickAddMore() {
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

    private void insertContact(View mView, AlertDialog dialog, String name, String phoneNumb) {

        Model model = new Model(name, phoneNumb);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();

        if (name.equals("") || phoneNumb.equals("")) {
            Log.d(this.getClass().getName(), "onClick, name or phone number empty");
            Toast.makeText(getContext(), "empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        cv.put("name", name);
        cv.put("phone", phoneNumb);

        long rowID = db.insert("mytable", null, cv);

        Log.d(this.getClass().getName(), "row inserted, ID = " + rowID);
        MainActivity.arrayList.add(model);
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    private void onClickEmergencyCall() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        final View mView = getLayoutInflater().inflate(R.layout.send_message, null);
        final Button btnSend = mView.findViewById(R.id.btn_send_msg);
        final EditText etMessage = mView.findViewById(R.id.et_message);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMessage.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    //TODO send sms here
                    Log.d("TAG123", "arraylist: " + MainActivity.arrayListWhite.toString());
                    Toast.makeText(getContext(), "seems good", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void sendSmsMsg(String mblNumVar, String smsMsgVar) {
        //TODO вставить цикл для отправки всем из белого листа и проверить отправку одному
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);
                Toast.makeText(getContext(), "Message Sent",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ErrVar) {
                Log.d("TAG123", "sendSMS error" + ErrVar.getMessage());
                Toast.makeText(getContext(), "can't send sms",
                        Toast.LENGTH_LONG).show();
                ErrVar.printStackTrace();
            }
        } else {
            Log.d("TAG123", "ask permission to send sms");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }
    }
}
