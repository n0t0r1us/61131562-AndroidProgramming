package vu.eduntu.ex_6_listviewdemo;

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

        //Hiển thị dữ liệu lên ListView
        //B1+: Cần có dữ liệu
        //từ Database(SQL, noSQL, XML,...)
        //chúng ta hard-core dữ liệu trực tiếp
        //cần biến phù hợp để chứa dữ liệu
        ArrayList<String> dsTenTinhThanhVN; //khai báo
        dsTenTinhThanhVN = new ArrayList<String >(); //tạo thể hiện cụ thể, xin mới
        //thêm dữ liệu (đọc từ 1 src
        //nhưng ta hard-core (cho sẵn để demo)
        dsTenTinhThanhVN.add("Hà Nội");
        dsTenTinhThanhVN.add("Thành phố Hồ Chí Minh");
        dsTenTinhThanhVN.add("Khánh Hòa");
        dsTenTinhThanhVN.add("Đà Lạt");
        dsTenTinhThanhVN.add("Nha Trang");
        dsTenTinhThanhVN.add("Phú Yên");
        //B2. Tạo Adapter
        ArrayAdapter<String> adapterTinhThanh;
        adapterTinhThanh = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dsTenTinhThanhVN);
        //B3. Gắn vào điều khiển hiện thị ListView
        //3.1. Tìm ĐK
        ListView lvTenTinhThanh = findViewById(R.id.lvDanhSachTT);
        //3.2. Gắn
        lvTenTinhThanh.setAdapter(adapterTinhThanh);
        //3.3. Lắng nghe và xử lý sự kiện User tương tác

    }
}