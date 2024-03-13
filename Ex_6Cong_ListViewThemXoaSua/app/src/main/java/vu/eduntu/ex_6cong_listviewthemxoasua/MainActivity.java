package vu.eduntu.ex_6cong_listviewthemxoasua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtThongTin;
    ListView lsvTen;
    ArrayList<String> dsTen;
    void getControls(){
        edtThongTin = (EditText) findViewById(R.id.edThongTin);
        lsvTen = (ListView) findViewById(R.id.lvTen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControls();
        dsTen = new ArrayList<String>();
        dsTen.add("Ni");
        dsTen.add("Như");
        dsTen.add("Thùy");
        dsTen.add("Mai");
        ArrayAdapter<String> bo_Nguon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dsTen);
        lsvTen.setAdapter(bo_Nguon);
    }
}