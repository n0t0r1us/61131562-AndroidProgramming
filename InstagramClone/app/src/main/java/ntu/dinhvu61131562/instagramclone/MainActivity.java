package ntu.dinhvu61131562.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ntu.dinhvu61131562.instagramclone.Fragment.ProfileFragment;
import ntu.dinhvu61131562.instagramclone.Fragment.ThongBaoFragment;
import ntu.dinhvu61131562.instagramclone.Fragment.TimKiemFragment;
import ntu.dinhvu61131562.instagramclone.Fragment.TrangChuFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFrag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationOnItemSelectedListener);

        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String nguoiDang = intent.getString("idNguoiDang");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileId", nguoiDang);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contain,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_contain,
                    new TrangChuFragment()).commit();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_contain,
                new TrangChuFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationOnItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_trangchu){
                        selectedFrag = new TrangChuFragment();
                    } else if (itemId == R.id.nav_timkiem){
                        selectedFrag = new TimKiemFragment();
                    } else if (itemId == R.id.nav_them){
                        selectedFrag = null;
                        startActivity(new Intent(MainActivity.this, PostActivity.class));
                    } else if (itemId == R.id.nav_tim){
                        selectedFrag = new ThongBaoFragment();
                    } else if (itemId == R.id.nav_pro5){
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                        editor.putString("profileId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();
                        selectedFrag = new ProfileFragment();
                    }
                    if (selectedFrag != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_contain,
                                selectedFrag).commit();
                    }
                    return true;
                }
            };
}