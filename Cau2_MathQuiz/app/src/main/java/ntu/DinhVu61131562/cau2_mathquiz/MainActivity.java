package ntu.DinhVu61131562.cau2_mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView cauHoiTextView;
    private TextView ketQuaTextView;
    private Button dapAn1Button, dapAn2Button, dapAn3Button, dapAn4Button;
    private Button doiCauHoiButton;
    private int soCauDung = 0;
    private int tongSoCauHoi = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

    }
}