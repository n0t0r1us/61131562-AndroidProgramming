package vu.eduntu.ex_6cong_listviewthemxoasua;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dsTD;
    ArrayAdapter<String> adapterTD;
    Button btnThem,btnSua,btnXoa;
    ListView lvTD;
    EditText edtTD;

    int ViTri = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem = (Button)findViewById(R.id.btnThem);
        btnSua = (Button)findViewById(R.id.btnSua);
        btnXoa = (Button)findViewById(R.id.btnXoa);
        edtTD = (EditText)findViewById(R.id.edtTD);
        dsTD = new ArrayList<String>();
        dsTD.add("Ni");
        dsTD.add("Như");
        dsTD.add("Thùy");
        dsTD.add("Mai");
        dsTD.add("Trâm");
        dsTD.add("Ngân");

        adapterTD = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                dsTD);

        lvTD = findViewById(R.id.lvNguoiYeuThich);
        lvTD.setAdapter(adapterTD);
        lvTD.setOnItemClickListener(BoLangNghevaXL);
        ThemPhanTu();
        SuaPhanTu();
        XoaPhanTu();

    }
    AdapterView.OnItemClickListener BoLangNghevaXL = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String strTenTDChon = dsTD.get(i);
            /*Toast.makeText(MainActivity.this, "Bạn vừa chọn: "+String.valueOf(i), Toast.LENGTH_LONG).show();*/
            Toast.makeText(MainActivity.this,strTenTDChon, Toast.LENGTH_LONG).show();
        }
    };
    public void ThemPhanTu(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baiHat = edtTD.getText().toString();
                dsTD.add(baiHat);
                adapterTD.notifyDataSetChanged();
            }
        });
    }
    public void XoaPhanTu() {
        lvTD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtTD.setText(dsTD.get(position));
                ViTri = position;
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsTD.remove(ViTri);
                adapterTD.notifyDataSetChanged();
            }
        });
    }
    public void SuaPhanTu(){
        lvTD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtTD.setText(dsTD.get(position));
                ViTri = position;
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsTD.set(ViTri, edtTD.getText().toString());
                adapterTD.notifyDataSetChanged();
            }
        });
    }
}