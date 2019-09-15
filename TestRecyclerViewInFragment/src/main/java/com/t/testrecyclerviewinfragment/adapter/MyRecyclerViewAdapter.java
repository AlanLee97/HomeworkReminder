package com.t.testrecyclerviewinfragment.adapter;

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


import com.t.testrecyclerviewinfragment.R;
import com.t.testrecyclerviewinfragment.entity.HomeData;
import com.t.testrecyclerviewinfragment.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    //上下文
    private Context context;
    //数据列表
    private List<HomeData> homeDataList = new ArrayList<>();

    public MyRecyclerViewAdapter(Context context, List<HomeData> homeDataList) {
        this.context = context;
        this.homeDataList = homeDataList;
    }

    //定义监听器
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        //获取通讯录数据
        HomeData homeData = homeDataList.get(position);
        myViewHolder.cardTouxiang.setImageResource(homeData.getImg());
        myViewHolder.cardNickname.setText(homeData.getNickname());
        myViewHolder.cardDate.setText(homeData.getDate());
        myViewHolder.cardTitle.setText(homeData.getTitle());
        myViewHolder.cardContent.setText(homeData.getContent());
        myViewHolder.cardTag.setText(homeData.getTag());

        //设置监听器的点击事件
        if (onItemClickListener != null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(myViewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homeDataList.size();
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
    }


}
