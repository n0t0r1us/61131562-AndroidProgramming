package ntu.dinhvu61131562.bottomnavigationviewwithfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MathFragment extends Fragment {
    private TextView tvSo1, tvSo2, tvKQ;
    private EditText etSo1, etSo2, etKQ;
    private Button genButton;
    private ImageButton addButton, subButton, multiButton, divButton;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_math, container, false);
        tvSo1 = rootView.findViewById(R.id.tvSo1);
        tvSo2 = rootView.findViewById(R.id.tvSo2);
        tvKQ = rootView.findViewById(R.id.tvKQ);
        etSo1 = rootView.findViewById(R.id.etSo1);
        etSo2 = rootView.findViewById(R.id.etSo2);
        etKQ = rootView.findViewById(R.id.etKQ);
        genButton = rootView.findViewById(R.id.genButton);
        addButton = rootView.findViewById(R.id.addButton);
        subButton = rootView.findViewById(R.id.subtractButton);
        multiButton = rootView.findViewById(R.id.multiplyButton);
        divButton = rootView.findViewById(R.id.divideButton);

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int so1 = random.nextInt(10) +1;
                int so2 = random.nextInt(10)+1;
                etSo1.setText(String.valueOf(so1));
                etSo2.setText(String.valueOf(so2));
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = Integer.parseInt(etSo1.getText().toString());
                int s2 = Integer.parseInt(etSo2.getText().toString());
                int kQ = s1+s2;
                etKQ.setText(String.valueOf(kQ));
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = Integer.parseInt(etSo1.getText().toString());
                int s2 = Integer.parseInt(etSo2.getText().toString());
                int kQ = s1-s2;
                etKQ.setText(String.valueOf(kQ));
            }
        });
        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = Integer.parseInt(etSo1.getText().toString());
                int s2 = Integer.parseInt(etSo2.getText().toString());
                int kQ = s1*s2;
                etKQ.setText(String.valueOf(kQ));
            }
        });

        return rootView;

    }
}