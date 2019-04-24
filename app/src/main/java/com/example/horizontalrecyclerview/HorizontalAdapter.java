package com.example.horizontalrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mars at 2019-04-24
 */
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ListHolder>{
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<DataModel> dataList;
    private ActionCallback.data<CustomScrollView> callback;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public HorizontalAdapter(Context context, ArrayList<DataModel>  dataList, ActionCallback.data<CustomScrollView> callback) {
        this.context = context;
        this.dataList = dataList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.recycleview_item_list, viewGroup, false);

        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
        listHolder.t1.setText(dataList.get(i).getT1());
        listHolder.t2.setText(dataList.get(i).getT2());
        listHolder.t3.setText(dataList.get(i).getT3());
        listHolder.t4.setText(dataList.get(i).getT4());
        listHolder.t5.setText(dataList.get(i).getT5());

        callback.onDataResult(listHolder.c_horizontalScrollView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public interface ActionCallback extends Serializable {
        interface data<E> extends ActionCallback {
            void onDataResult(E result);
        }
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView title;
        CustomScrollView c_horizontalScrollView;

        ListHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.textView5);
            t2 = itemView.findViewById(R.id.textView6);
            t3 = itemView.findViewById(R.id.textView7);
            t4 = itemView.findViewById(R.id.textView8);
            t5 = itemView.findViewById(R.id.textView9);
            title = itemView.findViewById(R.id.title);
            c_horizontalScrollView = itemView.findViewById(R.id.c_horizontalScrollView);

            c_horizontalScrollView.getChildAt(0).setOnClickListener(this);//讓scrollview得到onClick監聽
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

}
