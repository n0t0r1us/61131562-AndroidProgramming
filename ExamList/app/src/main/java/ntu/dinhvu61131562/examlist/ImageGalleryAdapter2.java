package ntu.dinhvu61131562.examlist;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageGalleryAdapter2 extends RecyclerView.Adapter<examViewHolder> {
    List<examData> list;
    Context context;


    public ImageGalleryAdapter2(List<examData> list,
                                Context context)
    {
        this.list = list;
        this.context = context;
    }
    @Override
    public examViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.exam_card,
                        parent, false);

        examViewHolder viewHolder
                = new examViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final examViewHolder viewHolder,
                     final int position)
    {
        viewHolder.examName
                .setText(list.get(position).getName());
        viewHolder.examDate
                .setText(list.get(position).getDate());
        viewHolder.examMessage
                .setText(list.get(position).getMassage());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

}