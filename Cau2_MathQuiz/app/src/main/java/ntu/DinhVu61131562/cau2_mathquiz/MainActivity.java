package ntu.DinhVu61131562.cau2_mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView cauHoiTextView;
    private TextView ketQuaTextView;
    private Button dapAn1Button, dapAn2Button, dapAn3Button, dapAn4Button;
    private ImageButton doiCauHoiButton, choiLaiButton;
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
        choiLaiButton = findViewById(R.id.choiLaiButton);

        showNextQuestion();

        dapAn1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(dapAn1Button.getText().toString());
            }
        });
        dapAn2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(dapAn2Button.getText().toString());

            }
        });
        dapAn3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(dapAn3Button.getText().toString());
            }
        });
        dapAn4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(dapAn3Button.getText().toString());
            }
        });
        doiCauHoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });
        choiLaiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
    }
    private void showNextQuestion(){
        Random random = new Random();

        int num1 = random.nextInt(20) +1;
        int num2 = random.nextInt(20) +1;

        char operator;
        int operatorIndex = random.nextInt(4);
        switch (operatorIndex){
            case 0:
                operator = '+';
                break;
            case 1:
                operator = '-';
                break;
            case 2:
                operator = 'x';
                break;
            case 3:
                operator = '÷';
                break;
            default:
                operator = '+';
                break;
        }
        int ketQuaDung = calculate(num1, num2, operator);

        cauHoiTextView.setText(num1 + " " + operator + " " + num2 + " = ?");

        int[] options = generateOptions(ketQuaDung);

        dapAn1Button.setText(String.valueOf(options[0]));
        dapAn2Button.setText(String.valueOf(options[1]));
        dapAn3Button.setText(String.valueOf(options[2]));
        dapAn4Button.setText(String.valueOf(options[3]));

    }
    private int calculate(int num1, int num2, char operator){
        int result = 0;
        switch (operator){
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case 'x':
                result = num1 * num2;
                break;
            case '÷':
                result = num1 / num2;
                break;
        }
        return result;
    }
    private int[] generateOptions(int ketQuaDung){
        Random random = new Random();
        int[] options = new int[4];

        options[random.nextInt(4)]= ketQuaDung;

        for (int i = 0 ; i < 4; i++){
            if (options[i] == 0){
                options[i] = ketQuaDung + random.nextInt(20) - 10;
            }
        }
        return options;
    }
    private void checkAnswer(String selectedOptions){
        int selectedAnswer = Integer.parseInt(selectedOptions);
        if(selectedAnswer == calculateAnswer()){
            soCauDung++;
            Toast.makeText(this, "Đúng!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sai!", Toast.LENGTH_SHORT).show();
        }
        tongSoCauHoi++;

        ketQuaTextView.setText("Kết quả: " + soCauDung + "/" + tongSoCauHoi);

        if(tongSoCauHoi < 10){
            showNextQuestion();
        } else {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            dapAn1Button.setEnabled(false);
            dapAn2Button.setEnabled(false);
            dapAn3Button.setEnabled(false);
            dapAn4Button.setEnabled(false);
        }
    }
    private int calculateAnswer(){
        int num1 = Integer.parseInt(cauHoiTextView.getText().toString().split(" ")[0]);
        char operator = cauHoiTextView.getText().toString().split(" ")[1].charAt(0);
        int num2 = Integer.parseInt(cauHoiTextView.getText().toString().split(" ")[2]);
        return calculate(num1, num2, operator);
    }
    private void restartGame(){
        soCauDung = 0;
        tongSoCauHoi = 0;
        ketQuaTextView.setText("Kết quả: " + soCauDung + "/" + tongSoCauHoi);
        dapAn1Button.setEnabled(true);
        dapAn2Button.setEnabled(true);
        dapAn3Button.setEnabled(true);
        dapAn4Button.setEnabled(true);
        showNextQuestion();
    }
}