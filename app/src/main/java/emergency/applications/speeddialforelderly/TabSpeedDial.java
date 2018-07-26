package emergency.applications.speeddialforelderly;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TabSpeedDial extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_speed_dial, container, false);
        Button btnAddMore = rootView.findViewById(R.id.btn_add_more);
        btnAddMore.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_add_more:
                break;
        }
    }
}
