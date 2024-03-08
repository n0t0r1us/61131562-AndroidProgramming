package vu.edu.ex5_addsubmuldiv_var;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Tìm view
        TimView();
        //Gắn các bộ lắng nghe
        btnCong.setOnClickListener(boLangNghe_XuLyCong);
        btnTru.setOnClickListener(boLangNghe_XuLyTru);
        btnNhan.setOnClickListener(boLangNghe_XuLyNhan);
        //btnChia.setOnClickListener(boLangNghe_XuLyChia);
        // ví dụ bộ lắng nghe ẩn danh
        btnChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code xử lý chia
                //b1. Lấy số a, b
                String strSoA = edtSoA.getText().toString();
                String strSoB = edtSoB.getText().toString();
                //b2. Chuyển thành số để tính toán
                double soA = Double.parseDouble(strSoA);
                double soB = Double.parseDouble(strSoB);
                //b3. Tính toán
                double thuong = soA/soB;
                //Xuất
                String strKQ = String.valueOf(thuong);
                tvKetQua.setText(strKQ);

            }
        });
    }
    //------------------------------------------------
    //Tạo các bộ lắng nghe và xử lý sự kiện
    View.OnClickListener boLangNghe_XuLyCong = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Code xử lý cộng
            //b1. Lấy số a, b
            String strSoA = edtSoA.getText().toString();
            String strSoB = edtSoB.getText().toString();
            //b2. Chuyển thành số để tính toán
            double soA = Double.parseDouble(strSoA);
            double soB = Double.parseDouble(strSoB);
            //b3. Tính toán
            double tong = soA+soB;
            //Xuất
            String strKQ = String.valueOf(tong);
            tvKetQua.setText(strKQ);
        }
    };
    //------------------------------------------------
    //Tạo các bộ lắng nghe và xử lý sự kiện
    View.OnClickListener boLangNghe_XuLyTru = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Code xử lý trừ
            //b1. Lấy số a, b
            String strSoA = edtSoA.getText().toString();
            String strSoB = edtSoB.getText().toString();
            //b2. Chuyển thành số để tính toán
            double soA = Double.parseDouble(strSoA);
            double soB = Double.parseDouble(strSoB);
            //b3. Tính toán
            double hieu = soA-soB;
            //Xuất
            String strKQ = String.valueOf(hieu);
            tvKetQua.setText(strKQ);
        }
    };
    //------------------------------------------------
    //Tạo các bộ lắng nghe và xử lý sự kiện
    View.OnClickListener boLangNghe_XuLyNhan = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Code xử lý nhân
            //b1. Lấy số a, b
            String strSoA = edtSoA.getText().toString();
            String strSoB = edtSoB.getText().toString();
            //b2. Chuyển thành số để tính toán
            double soA = Double.parseDouble(strSoA);
            double soB = Double.parseDouble(strSoB);
            //b3. Tính toán
            double tich = soA*soB;
            //Xuất
            String strKQ = String.valueOf(tich);
            tvKetQua.setText(strKQ);
        }
    };
    //------------------------------------------------
    //Tạo các bộ lắng nghe và xử lý sự kiện
//    View.OnClickListener boLangNghe_XuLyChia = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            //Code xử lý chia
//            //b1. Lấy số a, b
//            String strSoA = edtSoA.getText().toString();
//            String strSoB = edtSoB.getText().toString();
//            //b2. Chuyển thành số để tính toán
//            double soA = Double.parseDouble(strSoA);
//            double soB = Double.parseDouble(strSoB);
//            //b3. Tính toán
//            double thuong = soA/soB;
//            //Xuất
//            String strKQ = String.valueOf(thuong);
//            tvKetQua.setText(strKQ);
//        }
//    };
    //------------------------------------------------
    void TimView(){
        edtSoA =(EditText) findViewById(R.id.edtSoA);
        edtSoB =(EditText) findViewById(R.id.edtSoB);
        btnCong = (Button) findViewById(R.id.btnCong);
        btnTru = (Button) findViewById(R.id.btnTru);
        btnNhan = (Button) findViewById(R.id.btnNhan);
        btnChia = (Button) findViewById(R.id.btnChia);
        tvKetQua = (TextView) findViewById(R.id.edtKQ);
    }
    // Khai báo các đối tượng tương ứng với các điều khiển (view) cần thao tác
    EditText edtSoA, edtSoB;
    Button btnCong, btnTru, btnNhan, btnChia;
    TextView tvKetQua;
}