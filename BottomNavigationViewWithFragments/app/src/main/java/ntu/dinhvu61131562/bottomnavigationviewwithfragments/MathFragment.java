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

public class MathFragment extends Fragment {
    private TextView tvSo1, tvSo2, tvKQ;
    private EditText edSo1, edSo2, edKQ;
    private Button genButton;
    private ImageButton addButton, subButton, multiButton, divButton;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_math, container, false);
        tvSo1 = rootView.findViewById(R.id.tvSo1);
        tvSo2 = rootView.findViewById(R.id.tvSo2);
        tvKQ = rootView.findViewById(R.id.tvKQ);
        edSo1 = rootView.findViewById(R.id.etSo1);
        edSo2 = rootView.findViewById(R.id.etSo2);
        edKQ = rootView.findViewById(R.id.etKQ);
        genButton = rootView.findViewById(R.id.genButton);
        addButton = rootView.findViewById(R.id.addButton);
        subButton = rootView.findViewById(R.id.subtractButton);
        multiButton = rootView.findViewById(R.id.multiplyButton);
        divButton = rootView.findViewById(R.id.divideButton);

        return inflater.inflate(R.layout.fragment_math, container, false);

    }
}