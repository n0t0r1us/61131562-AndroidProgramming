package vu.eduntu.mylyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }
    void timDK(){
        lvBaiHat = findViewById(R.id.lvLoiBaiHatYeuThich);
    }
}