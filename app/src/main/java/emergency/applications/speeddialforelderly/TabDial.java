package emergency.applications.speeddialforelderly;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        }
    }
}
