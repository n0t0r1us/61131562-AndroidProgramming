package ntu.dinhvu61131562.intentex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Change (View v){
        Intent iScreen2 = new Intent(MainActivity.this, Surprise.class);
        startActivity(iScreen2);

    }
}