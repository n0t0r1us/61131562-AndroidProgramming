package vu.eduntu.mylyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dsBH;
    ArrayAdapter<String> adapterBH;
    ListView lvBaiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timDK();
        //Bước 2
        dsBH = new ArrayList<String>();
        dsBH.add("Hotel California");
        dsBH.add("Careless Whisper");
        dsBH.add("Rasputin");
        //Bước 3
        adapterBH = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dsBH);
        //Bước 4
        lvBaiHat.setAdapter(adapterBH);
        //Bước 5 xử lý sự kiện
        lvBaiHat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Code xử lý, ví dụ:
                String chuoiThongBao = "Bạn chọn bài "+ dsBH.get(position);
                Toast.makeText(MainActivity.this,chuoiThongBao,Toast.LENGTH_SHORT).show();
            //Hoặc ta viết 1 hàm xử lý ở ngoài, và gọi tại đây
            //HamXuLyChonItem(position);
            }
        });
    }
    void HamXuLyChonItem(int position){
        String chuoiThongBao= "Bạn chọn bài "+ dsBH.get(position);;
        Toast.makeText(MainActivity.this,
                chuoiThongBao,Toast.LENGTH_SHORT).show();
    }
    void timDK(){
        lvBaiHat = findViewById(R.id.lvLoiBaiHatYeuThich);
    }
}