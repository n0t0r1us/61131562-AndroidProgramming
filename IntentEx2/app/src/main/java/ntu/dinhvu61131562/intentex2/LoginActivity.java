package ntu.dinhvu61131562.intentex2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edtUserName = (EditText) findViewById(R.id.edtUserName);
        EditText edtPassWord = (EditText) findViewById(R.id.edtPass);
        EditText edtUserMail = (EditText) findViewById(R.id.edtMail);
        Button btnXacNhan = (Button) findViewById(R.id.btnOK);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtUserName = (EditText) findViewById(R.id.edtUserName);
                EditText edtPassWord = (EditText) findViewById(R.id.edtPass);
                EditText edtUserMail = (EditText) findViewById(R.id.edtMail);
                String tenDN = edtUserName.getText().toString();
                String mk = edtPassWord.getText().toString();
                String email = edtUserMail.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                boolean isValidEmail = email.matches(emailPattern);

                if( tenDN.equals("dinhdu")&& mk.equals("6969") &&isValidEmail ){
                    Intent iQuiz = new Intent(LoginActivity.this, HomeActivity.class);
                    iQuiz.putExtra("user_name", tenDN);
                    iQuiz.putExtra("pass",mk);
                    iQuiz.putExtra("email", email);
                    startActivity(iQuiz);
                }
                else {
                    if(!isValidEmail){
                        Toast.makeText(LoginActivity.this,"EMAIL KHÔNG HỢP LỆ!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "BẠN NHẬP SAI THÔNG TIN", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }
}