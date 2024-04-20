package ntu.dinhvu61131562.intentex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent iFromLogin = getIntent();
        String userName_Receive = iFromLogin.getStringExtra("user_name");
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(userName_Receive);
    }
}