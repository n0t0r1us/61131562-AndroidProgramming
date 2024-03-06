package vu.edu.ex3_simplesumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Gắn Layout tương ứng với file này
        setContentView(R.layout.activity_main);
    }

    //Bộ lắng nghe và xử lý sự kiên click lên nút tính tổng
    public void XuLyCong(View view){
        //Tìm, tham chiếu đến điều khiển trên tệp XML, mapping sang Java file
        EditText editTextSoA = findViewById(R.id.edtA);
        EditText editTextSoB = findViewById(R.id.edtB);
        EditText editTextKQ = findViewById(R.id.edtKQ);
        //lấy dữ liệu về ở điều khiển số a
        String strA = editTextSoA.getText().toString(); //strA="2"

        //lấy dữ liệu về ở điều khiển số b
        String strB = editTextSoB.getText().toString(); //strB="4"

        //Chuyển dữ liệu sang dạng số
        int so_A = Integer.parseInt(strA);  //2
        int so_B = Integer.parseInt(strB);  //4
        //Tính toán theo yêu cầu
        int tong = so_A+so_B;   //6
        String strTong = String.valueOf(tong); //chuyển sang dạng chuỗi; "6"
        //Hiển thị ra màn hình
        editTextKQ.setText(strTong);
    }
}