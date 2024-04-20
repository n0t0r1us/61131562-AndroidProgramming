package ntu.dinhvu61131562.intentex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Surprise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(ClickListenerChange);
    }

    View.OnClickListener ClickListenerChange = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent backToHome = new Intent(Surprise.this, MainActivity.class);
            startActivity(backToHome);
        }
    };
}