package emergency.applications.speeddialforelderly;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Model> modellist;
    private ArrayList<Model> arrayList;

    public ListViewAdapter(Context mContext, List<Model> modellist) {
        this.mContext = mContext;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mName;
        TextView mPhoneNumber;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.single_contact, null);

            holder.mName = view.findViewById(R.id.tv_name);
            holder.mPhoneNumber = view.findViewById(R.id.tv_phone_number);

            view.setTag(holder);

        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.mName.setText(modellist.get(i).getName());
        holder.mPhoneNumber.setText((modellist.get(i).getPhoneNumber()));


        view.findViewById(R.id.ibtn_call_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String numberString = "tel:" + modellist.get(i).getPhoneNumber();
                Log.d("phone number", numberString);
                intent.setData(Uri.parse(numberString));
                if (ActivityCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(view.getContext(),"no permission to make call",Toast.LENGTH_SHORT).show();
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
            public void onClick(View view){
                Toast.makeText(view.getContext(), "do some stuff", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
