package ntu.dinhvu61131562.examlist;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class examViewHolder  extends RecyclerView.ViewHolder {
    TextView examMessage;
    TextView examName;
    TextView examDate;
    examViewHolder( View itemView) {
        super(itemView);
        examName = (TextView)itemView.findViewById(R.id.examName);
        examDate = (TextView)itemView.findViewById(R.id.examDate);
        examMessage = (TextView)itemView.findViewById(R.id.examMessage);
    }
}
