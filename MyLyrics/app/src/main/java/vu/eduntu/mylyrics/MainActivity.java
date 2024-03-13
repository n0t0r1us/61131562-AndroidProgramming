package vu.eduntu.mylyrics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bước 2
        ArrayList<String> dsBH;
        dsBH = new ArrayList<String>();
        dsBH.add("Hotel California");
        dsBH.add("Careless Whisper");
        dsBH.add("Rasputin");
        //Bước 3
        ArrayAdapter<String> adapterBH;
        adapterBH = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dsBH);
        //Bước 4
        ListView lvBaiHat = findViewById(R.id.lvLoiBaiHatYeuThich);
        lvBaiHat.setAdapter(adapterBH);
        //Bước 5 xử lý sự kiện
        //
    }
}