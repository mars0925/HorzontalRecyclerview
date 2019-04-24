package com.example.horizontalrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HorizontalAdapter.ActionCallback.data<CustomScrollView>,HorizontalAdapter.OnItemClickListener, View.OnTouchListener{
    RecyclerView r_recyclerView;
    private final OnScrollChangedListener listener = new OnScrollChangedListener();
    HorizontalAdapter adapter;
    CustomScrollView c_horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r_recyclerView = findViewById(R.id.r_recyclerView);
        c_horizontalScrollView = findViewById(R.id.c_horizontalScrollView);

        ArrayList<DataModel> dataList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            dataList.add(new DataModel("aaaaa","25","worker","TTTTTT","wwwwww"));
        }


        LinearLayoutManager linear = new LinearLayoutManager(this);
        this.r_recyclerView.setLayoutManager(linear);

        adapter = new HorizontalAdapter(getApplicationContext(),dataList,this);
        adapter.setOnItemClickListener(this);
        r_recyclerView.setAdapter(adapter);

        r_recyclerView.setOnTouchListener(this);
        c_horizontalScrollView.addOnScrollChangedListener(listener.setView(c_horizontalScrollView));
    }

    @Override
    public void onDataResult(CustomScrollView scrollView) {
        c_horizontalScrollView.addOnScrollChangedListener(listener.setView(scrollView));//讓Top可以連帶滑動其他欄位資料
        scrollView.addOnScrollChangedListener(listener.setView(null));//讓該列資料連帶滑動其他欄位

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*當在列頭和listView控件上touch時，將這個touch的事件分發給ScrollView*/
        c_horizontalScrollView.onTouchEvent(event);

        return false;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class OnScrollChangedListener implements CustomScrollView.OnScrollChangedListener {
        ArrayList<CustomScrollView> mScrollView = new ArrayList<>();

        public OnScrollChangedListener setView(CustomScrollView scrollViewar){
            /*不需要加入新的view*/
            if(scrollViewar == null){
                return this;
            }

            /*已加入的view不再重複加*/
            for(CustomScrollView bean:mScrollView){
                if(bean == scrollViewar){
                    return this;
                }
            }

            mScrollView.add(scrollViewar);
            return this;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt){
            /*包含標頭全部列都一起滑動*/
            for(CustomScrollView bean:mScrollView){
                bean.smoothScrollTo(l, t);
            }
        }
    }
}
