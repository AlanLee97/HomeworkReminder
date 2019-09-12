package com.homeworkreminder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.homeworkreminder.R;
import com.homeworkreminder.entity.HomeData;
import com.homeworkreminder.entity.HomeworkData;
import com.homeworkreminder.interfaces.MyRecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeDataRecyclerViewAdapter extends RecyclerView.Adapter<HomeDataRecyclerViewAdapter.MyViewHolder> {
    //上下文
    private Context context;
    //数据列表
    private List<HomeData> homeDataList = new ArrayList<>();
    private List<HomeworkData> homeworkDataList = new ArrayList<>();
    private List<HomeworkData.DataBean> homeworkDataBeanList = new ArrayList<>();
    private LayoutInflater layoutInflater;


    /*
    public HomeDataRecyclerViewAdapter(Context context, List<HomeData> homeDataList) {
        this.context = context;
        this.homeDataList = homeDataList;
    }

     //*/

    //*
    public HomeDataRecyclerViewAdapter(Context context, List<HomeworkData.DataBean> homeworkDataBeanList) {
        this.context = context;
        this.homeworkDataBeanList = homeworkDataBeanList;
    }

     //*/

    //定义监听器
    private MyRecyclerViewOnItemClickListener myRecyclerViewOnItemClickListener;

    public void setMyRecyclerViewOnItemClickListener(MyRecyclerViewOnItemClickListener myRecyclerViewOnItemClickListener) {
        this.myRecyclerViewOnItemClickListener = myRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //填充布局列表项
        View itemView = LayoutInflater.from(context).inflate(R.layout.home_homework_list_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
    //填充onCreateViewHolder返回的控件
        //获取数据
        /*
        HomeData homeData = homeDataList.get(position);
        myViewHolder.cardTouxiang.setImageResource(homeData.getImg());
        myViewHolder.cardNickname.setText(homeData.getNickname());
        myViewHolder.cardDate.setText(homeData.getDate());
        myViewHolder.cardTitle.setText(homeData.getTitle());
        myViewHolder.cardContent.setText(homeData.getContent());
        myViewHolder.cardTag.setText(homeData.getTag());
//*/
        /*
        HomeworkData homeworkData = homeworkDataList.get(position);
        myViewHolder.cardTitle.setText(homeworkData.getData().get(position).getTitle());
        myViewHolder.cardContent.setText(homeworkData.getData().get(position).getContent());
        myViewHolder.cardTag.setText(homeworkData.getData().get(position).getTag());
        myViewHolder.cardDate.setText(homeworkData.getData().get(position).getDate());


         */

        //更新数据到RecyclerView
        myViewHolder.updateData();


        //设置监听器的点击事件
        if (myRecyclerViewOnItemClickListener != null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRecyclerViewOnItemClickListener.onItemClickListener(myViewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homeworkDataBeanList.size();
    }


    /**
     * 自定义内部类
     * 我的理解是，在MyViewHolder中将填充布局文件recycler_view_item.xml的内容加载进来，
     * 类似于在Activity中初始化组件
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        //定义对应的列表项

        private LinearLayout homeworkListLinearLayout;
        private CardView cardView;
        private ImageView cardTouxiang;
        private TextView cardNickname;
        private TextView cardDate;
        private TextView cardTitle;
        private TextView cardContent;
        private TextView cardTag;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //获取对应的列表项
            homeworkListLinearLayout = (LinearLayout) itemView.findViewById(R.id.homework_list_linearLayout);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardTouxiang = (ImageView) itemView.findViewById(R.id.card_touxiang);
            cardNickname = (TextView) itemView.findViewById(R.id.card_nickname);
            cardDate = (TextView) itemView.findViewById(R.id.card_date);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            cardContent = (TextView) itemView.findViewById(R.id.card_content);
            cardTag = (TextView) itemView.findViewById(R.id.card_tag);
        }

        public void updateData(){
            int position = this.getLayoutPosition();
            HomeworkData.DataBean dataBean = homeworkDataBeanList.get(position);
            cardTitle.setText(dataBean.getTitle());
            cardDate.setText(dataBean.getDate());
            cardContent.setText(dataBean.getContent());
            cardTag.setText(dataBean.getTag());
        }
    }







}
