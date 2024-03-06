package vu.edu.ex5_addsubmuldiv_anynomous;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Khai báo các đối tượng gắn với điều khiển tương ứng
    EditText editTextSoA;
    EditText editTextSoB;
    EditText editTextKQ;
    Button nutCong, nutTru, nutNhan, nutChia;
    void TimDieuKhien(){
        editTextSoA = (EditText) findViewById(R.id.edtSoA);
        editTextSoB = (EditText) findViewById(R.id.edtSoB);
        editTextKQ = (EditText) findViewById(R.id.edtKQ);
        nutCong = (Button) findViewById(R.id.btnCong);
        nutTru = (Button) findViewById(R.id.btnTru);
        nutNhan = (Button) findViewById(R.id.btnNhan);
        nutChia = (Button) findViewById(R.id.btnChia);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimDieuKhien();
        //Gắn bộ lắng nghe sự kiện và code xử lý cho tứng nút

        nutCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XULY_CONG();
            }
        });
        nutTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xử lý
                XULY_TRU();

            }
        });
        nutNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xử lý
                XULY_NHAN();

            }
        });
        nutChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xử lý chia
                XULY_CHIA();

            }
        });
    }
    void XULY_CONG(){
        //Lấy dữ liệu
        String soA = editTextSoA.getText().toString();
        String soB = editTextSoB.getText().toString();
        float num1 = Float.parseFloat(soA);
        float num2 = Float.parseFloat(soB);
        float Tong = num1 + num2;
        String chuoiKQ = String.valueOf(Tong);
        editTextKQ.setText(chuoiKQ);


    }
    void XULY_TRU(){
        String soA = editTextSoA.getText().toString();
        String soB = editTextSoB.getText().toString();
        float num1 = Float.parseFloat(soA);
        float num2 = Float.parseFloat(soB);
        float Hieu = num1 - num2;
        String chuoiKQ = String.valueOf(Hieu);
        editTextKQ.setText(chuoiKQ);

    }
    void XULY_NHAN(){
        String soA = editTextSoA.getText().toString();
        String soB = editTextSoB.getText().toString();
        float num1 = Float.parseFloat(soA);
        float num2 = Float.parseFloat(soB);
        float Tich = num1 * num2;
        String chuoiKQ = String.valueOf(Tich);
        editTextKQ.setText(chuoiKQ);

    }
    void XULY_CHIA(){
        String soA = editTextSoA.getText().toString();
        String soB = editTextSoB.getText().toString();
        float num1 = Float.parseFloat(soA);
        float num2 = Float.parseFloat(soB);
        float Thuong = num1 / num2;
        String chuoiKQ = String.valueOf(Thuong);
        editTextKQ.setText(chuoiKQ);

    }
}