package emergency.applications.speeddialforelderly;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewWhiteAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Model> modellistWhite;
    private ArrayList<Model> arrayListWhite;

    public ListViewWhiteAdapter(Context mContext, List<Model> modellist) {
        this.mContext = mContext;
        this.modellistWhite = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayListWhite = new ArrayList<Model>();
        this.arrayListWhite.addAll(modellist);
    }

    public class ViewHolder {
        TextView mName;
        TextView mPhoneNumber;
    }

    @Override
    public int getCount() {
        return modellistWhite.size();
    }

    @Override
    public Object getItem(int i) {
        return modellistWhite.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ListViewWhiteAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ListViewWhiteAdapter.ViewHolder();
            view = inflater.inflate(R.layout.single_contact, null);

            holder.mName = view.findViewById(R.id.tv_name);
            holder.mPhoneNumber = view.findViewById(R.id.tv_phone_number);

            view.setTag(holder);

        } else {
            holder = (ListViewWhiteAdapter.ViewHolder) view.getTag();
        }

        holder.mName.setText(modellistWhite.get(i).getName());
        holder.mPhoneNumber.setText((modellistWhite.get(i).getPhoneNumber()));


        view.findViewById(R.id.ibtn_call_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String numberString = "tel:" + modellistWhite.get(i).getPhoneNumber();
                numberString = numberString.replaceAll("#", Uri.encode("#"));
                Log.d("phone number", numberString);
                intent.setData(Uri.parse(numberString));
                if (ActivityCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(view.getContext(), "no permission to make call", Toast.LENGTH_SHORT).show();
                    Log.d(this.getClass().getName(), "no permission to make call");
                    return;
                }
                Log.d(this.getClass().getName(), "calling");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Model model = new Model(modellistWhite.get(i).getName(), modellistWhite.get(i).getPhoneNumber());
                        if (menuItem.getTitle().equals("remove")) {
                            removeItem(i);
                        }
                        if (menuItem.getTitle().equals("edit")) {
                            editItem(view, i);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        return view;
    }

    private void removeItem(int i) {
        Log.d("TAG123", "remove: " + modellistWhite.get(i).getName() + " " + modellistWhite.get(i).getPhoneNumber());
        MainActivity.dbWhite.delete("mytablewhite", "name=? and phone=?",
                new String[]{modellistWhite.get(i).getName(), modellistWhite.get(i).getPhoneNumber()});
        MainActivity.arrayListWhite.remove(i);
        TabWhitelist.adapterWhite.notifyDataSetChanged();
    }

    private void addItem(String name, String phone) {

    }

    private void editItem(View view, final int i) {
        Log.d("MYTAG123", "edit item call");
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        final View mView = inflater.inflate(R.layout.add_contact, null);
        final EditText mName = mView.findViewById(R.id.et_name);
        final EditText mPhone = mView.findViewById(R.id.et_phone);
        String name = modellistWhite.get(i).getName();
        String phone = modellistWhite.get(i).getPhoneNumber();
        mName.setText(name);
        mPhone.setText(phone);
        final Button btnEditContact = mView.findViewById(R.id.btn_add_contact);
        btnEditContact.setText("edit contact");
        final TextView textView = mView.findViewById(R.id.tv_add_contact);
        textView.setText("Edit contact: ");
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mName.getText().toString().isEmpty() && !mPhone.getText().toString().isEmpty()) {
                    String name = mName.getText().toString();
                    String phoneNumb = mPhone.getText().toString();
                    removeItem(i);
                    insertContact(mView, dialog, name, phoneNumb);
                } else {
                    Toast.makeText(mContext.getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void insertContact(View mView, AlertDialog dialog, String name, String phoneNumb) {

        Model model = new Model(name, phoneNumb);

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = MainActivity.dbHelperWhite.getWritableDatabase();

        if (name.equals("") || phoneNumb.equals("")) {
            Log.d(this.getClass().getName(), "onClick, name or phone number empty");
            Toast.makeText(mContext.getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
            return;
        }
        cv.put("name", name);
        cv.put("phone", phoneNumb);

        long rowID = db.insert("mytablewhite", null, cv);

        Log.d(this.getClass().getName(), "row inserted, ID = " + rowID);
        MainActivity.arrayListWhite.add(model);
        TabWhitelist.adapterWhite.notifyDataSetChanged();
        dialog.dismiss();
    }
}
