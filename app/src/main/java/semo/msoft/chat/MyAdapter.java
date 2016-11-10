package semo.msoft.chat;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MinwooPark on 2016-10-06.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private static final String TAG ="MyAdapter";
    private Context context;
    private String myID;
    private ArrayList<Message> list = new ArrayList<Message>();

    ViewHolder tempYholder;
    ViewHolder tempMholder;



    public MyAdapter(Context context,String id) {
        this.context = context;
        this.myID = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        //Log.d(TAG,"onCreateViewHolder()");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.d(TAG,"onBindViewHolder()");
        final Message message = list.get(position);


        //  ===== 자신이 보낸 메세지인 경우
        if(message.getSender().equals(myID)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.otherLayout.setVisibility(View.GONE);// 타인이 보낸 메세지 레이아웃 없앰
            if(position>0 && isMore(message,position)){ // 이전에 보낸 메세지와 시간, 작성자가 같을때
                holder.myMoreLayout.setVisibility(View.VISIBLE);
                holder.myNormarLayout.setVisibility(View.GONE);
                TextView tv = (TextView) holder.myMoreLayout.findViewById(R.id.my_more_text);
                tv.setText(message.getContent().toString());
                ((TextView)holder.myMoreLayout.findViewById(R.id.my_more_time)).setText(formatTime(message.getTime()));

                if(tempMholder.myNormarLayout.getVisibility()==View.VISIBLE){
                    ((TextView) tempMholder.myNormarLayout.findViewById(R.id.my_time)).setText("");
                    tempMholder = holder;
                }
                else{
                    ((TextView) tempMholder.myMoreLayout.findViewById(R.id.my_more_time)).setText("");
                    tempMholder =holder;
                }
            }
            else{
                holder.myNormarLayout.setVisibility(View.VISIBLE);
                holder.myMoreLayout.setVisibility(View.GONE);
                holder.myNormarLayout.setGravity(Gravity.RIGHT);
                TextView tv = (TextView) holder.myNormarLayout.findViewById(R.id.my_normal_text);
                tv.setText(message.getContent().toString());
                ((TextView)holder.myNormarLayout.findViewById(R.id.my_time)).setText(formatTime(message.getTime()));

                tempMholder = holder;

            }
        }

        //  ===== 다른사람이 보낸 메세지인 경우
        else{
            holder.otherLayout.setVisibility(View.VISIBLE);
            holder.myLayout.setVisibility(View.GONE); // 자신이 보낸 메세지 레이아웃 없앰
            if(position>0 && isMore(message,position)){ // 이전에 보낸 메세지와 시간, 작성자가 같을때

                holder.otherMoreLayout.setVisibility(View.VISIBLE);
                holder.otherNormalLayout.setVisibility(View.GONE);
                TextView tv = (TextView)holder.otherMoreLayout.findViewById(R.id.other_more_text);
                tv.setText(message.getContent().toString());
                ((TextView)holder.otherMoreLayout.findViewById(R.id.other_more_time)).setText(formatTime(message.getTime()));


                if(tempYholder.otherNormalLayout.getVisibility()==View.VISIBLE){
                    ((TextView) tempYholder.otherNormalLayout.findViewById(R.id.other_time)).setText("");
                    tempYholder = holder;
                }
                else{
                    ((TextView) tempYholder.otherMoreLayout.findViewById(R.id.other_more_time)).setText("");
                    tempYholder =holder;
                }

            }
            else{
                holder.otherNormalLayout.setVisibility(View.VISIBLE);
                holder.otherMoreLayout.setVisibility(View.GONE);
                TextView tv = (TextView)holder.otherNormalLayout.findViewById(R.id.other_normal_text);
                tv.setText(message.getContent().toString());
                ((TextView)holder.otherNormalLayout.findViewById(R.id.other_time)).setText(formatTime(message.getTime()));
                tempYholder = holder;
            }
        }

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Message msg) {
        list.add(msg);
    }




    private boolean isMore(Message message, int position){
        // 이전에 보낸 메세지와 작성자와 시간이 같은 경우를 확인하여 반환하는 함수

        if(formatTime(message.getTime()).equals(formatTime(list.get(position-1).getTime())) && message.getSender().equals(list.get(position-1).getSender())){
            // 이전에 보낸 메세지와 작성자와 시간이 같은 경우
            return true;
        }
        else{
            return false;
        }
    }

    private String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("a hh:mm");
        return format.format(date);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout otherLayout;
        LinearLayout otherNormalLayout;
        LinearLayout otherMoreLayout;
        LinearLayout myLayout;
        LinearLayout myNormarLayout;
        LinearLayout myMoreLayout;

        public ViewHolder(View v) {
            super(v);
             otherLayout = (LinearLayout)v.findViewById(R.id.other_layout); // 타인이 보낸 메세지 레이아웃
             otherNormalLayout = (LinearLayout)v.findViewById(R.id.other_normal_layout);
             otherMoreLayout = (LinearLayout)v.findViewById(R.id.other_more_layout);


             myLayout = (LinearLayout)v.findViewById(R.id.my_layout); // 자신이 보낸 메세지 레이아웃
            myNormarLayout = (LinearLayout)v.findViewById(R.id.my_normal_layout);
             myMoreLayout = (LinearLayout)v.findViewById(R.id.my_more_layout);
            //Log.d(TAG,"ViewHolder(생성자)");
        }
    }




}
