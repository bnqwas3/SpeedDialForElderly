package emergency.applications.speeddialforelderly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TabDial extends Fragment implements View.OnClickListener{
    TextView tvNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_dial, container, false);

        tvNumber = rootView.findViewById(R.id.tv_number);

        Button btn0 = rootView.findViewById(R.id.btn_0);
        Button btn1 = rootView.findViewById(R.id.btn_1);
        Button btn2 = rootView.findViewById(R.id.btn_2);
        Button btn3 = rootView.findViewById(R.id.btn_3);
        Button btn4 = rootView.findViewById(R.id.btn_4);
        Button btn5 = rootView.findViewById(R.id.btn_5);
        Button btn6 = rootView.findViewById(R.id.btn_6);
        Button btn7 = rootView.findViewById(R.id.btn_7);
        Button btn8 = rootView.findViewById(R.id.btn_8);
        Button btn9 = rootView.findViewById(R.id.btn_9);
        Button btnAsterisk = rootView.findViewById(R.id.btn_asterisk);
        Button btnNumbSign = rootView.findViewById(R.id.btn_number_sign);
        ImageButton btnRemoveLast = rootView.findViewById(R.id.imgb_back);
        ImageButton btnCall = rootView.findViewById(R.id.ibtn_call_number);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnAsterisk.setOnClickListener(this);
        btnNumbSign.setOnClickListener(this);
        btnRemoveLast.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        StringBuilder number = new StringBuilder(tvNumber.getText());
        switch (view.getId()){
            case R.id.btn_0:
                number.append('0');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_1:
                number.append('1');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_2:
                number.append('2');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_3:
                number.append('3');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_4:
                number.append('4');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_5:
                number.append('5');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_6:
                number.append('6');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_7:
                number.append('7');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_8:
                number.append('8');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_9:
                number.append('9');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_asterisk:
                number.append('*');
                tvNumber.setText(number.toString());
                break;
            case R.id.btn_number_sign:
                number.append('#');
                tvNumber.setText(number.toString());
                break;
            case R.id.imgb_back:
                if(number.length()>0) {
                    number.deleteCharAt(number.length() - 1);
                }
                tvNumber.setText(number.toString());
                break;
            case R.id.ibtn_call_number:
                if(number.length()>0){
                    for (int index = 0; index < number.length(); index++) {
                        if (number.charAt(index) == '#') {
                            number.deleteCharAt(index);
                            number.insert(index, Uri.encode("#"));
                        }
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    String numberString = "tel:" + number.toString();
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
        }
    }
}
