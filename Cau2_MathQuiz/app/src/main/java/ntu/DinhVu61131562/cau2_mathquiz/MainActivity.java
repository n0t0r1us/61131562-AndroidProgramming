package ntu.DinhVu61131562.cau2_mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView cauHoiTextView;
    private TextView ketQuaTextView;
    private Button dapAn1Button, dapAn2Button, dapAn3Button, dapAn4Button;
    private ImageButton doiCauHoiButton;
    private int soCauDung = 0;
    private int tongSoCauHoi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        cauHoiTextView = findViewById(R.id.cauHoiTextView);
        dapAn1Button = findViewById(R.id.dapAn1Button);
        dapAn2Button = findViewById(R.id.dapAn2Button);
        dapAn3Button = findViewById(R.id.dapAn3Button);
        dapAn4Button = findViewById(R.id.dapAn4Button);
        ketQuaTextView = findViewById(R.id.ketQuaTextView);
        doiCauHoiButton = findViewById(R.id.doiCauHoiButton);

        showNextQuestion();

        dapAn1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dapAn2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dapAn3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dapAn4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });



    }
    private void showNextQuestion(){

    }
}