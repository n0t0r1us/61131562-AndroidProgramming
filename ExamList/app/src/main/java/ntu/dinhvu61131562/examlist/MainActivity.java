package ntu.dinhvu61131562.examlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView view;
    ArrayList<examData> ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.recyclerView);
        ds = new ArrayList<>();
        ds.add(new examData("First exam", "May 23, 2015", "Best Of Luck"));
        ds.add(new examData("Second Exam", "June 09, 2015", "b of l"));
        ds.add(new examData("My Test Exam", "April 27, 2017", "This is testing exam .."));
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(new ImageGalleryAdapter2(ds, this));
    }
}