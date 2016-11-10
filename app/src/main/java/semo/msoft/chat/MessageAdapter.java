package semo.msoft.chat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MinwooPark on 2016-11-07.
 */

public class MessageAdapter extends BaseAdapter {



    private static final String TAG ="MessageAdapter";
    // Adapter 에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Message> list = new ArrayList<Message>();
    private String myID;
    private MainActivity context;


    // 생성자
    public MessageAdapter(String id, MainActivity context){
        myID = id;
        this.context = context;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return list.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG,"getVew()");
        final Context context = parent.getContext();

        // "message_item" Layout을 inflate하여 convertView 참조 획득.
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_item,parent,false);
        }

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Message message = list.get(position);


        LinearLayout otherLayout = (LinearLayout)convertView.findViewById(R.id.other_layout); // 타인이 보낸 메세지 레이아웃
        LinearLayout otherNormalLayout = (LinearLayout)convertView.findViewById(R.id.other_normal_layout);
        LinearLayout otherMoreLayout = (LinearLayout)convertView.findViewById(R.id.other_more_layout);


        LinearLayout myLayout = (LinearLayout)convertView.findViewById(R.id.my_layout); // 자신이 보낸 메세지 레이아웃
        LinearLayout myNormarLayout = (LinearLayout)convertView.findViewById(R.id.my_normal_layout);
        LinearLayout myMoreLayout = (LinearLayout)convertView.findViewById(R.id.my_more_layout);




        //  ===== 자신이 보낸 메세지인 경우
        if(message.getSender().equals(myID)){
            myLayout.setVisibility(View.VISIBLE);
            otherLayout.setVisibility(View.GONE);// 타인이 보낸 메세지 레이아웃 없앰
            if(position>0 && isMore(message,position)){ // 이전에 보낸 메세지와 시간, 작성자가 같을때
                myMoreLayout.setVisibility(View.VISIBLE);
                myNormarLayout.setVisibility(View.GONE);
                TextView tv = (TextView) convertView.findViewById(R.id.my_more_text);
                tv.setText(message.getContent());
                ((TextView)myMoreLayout.findViewById(R.id.my_more_time)).setText(formatTime(message.getTime()));

            }
            else{
                myNormarLayout.setVisibility(View.VISIBLE);
                myMoreLayout.setVisibility(View.GONE);
                TextView tv = (TextView) convertView.findViewById(R.id.my_normal_text);
                tv.setText(message.getContent());
                ((TextView)myNormarLayout.findViewById(R.id.my_time)).setText(formatTime(message.getTime()));

            }
        }


        //  ===== 다른사람이 보낸 메세지인 경우
        else{
            otherLayout.setVisibility(View.VISIBLE);
            myLayout.setVisibility(View.GONE); // 자신이 보낸 메세지 레이아웃 없앰
            if(position>0 && isMore(message,position)){ // 이전에 보낸 메세지와 시간, 작성자가 같을때
                otherMoreLayout.setVisibility(View.VISIBLE);
                otherNormalLayout.setVisibility(View.GONE);
                TextView tv = (TextView)convertView.findViewById(R.id.other_more_text);
                tv.setText(message.getContent());
                ((TextView)otherMoreLayout.findViewById(R.id.other_more_time)).setText(formatTime(message.getTime()));
                Log.d(TAG,((MainActivity)context).listView.getChildAt(position-1).toString());

            }
            else{
                otherNormalLayout.setVisibility(View.VISIBLE);
                otherMoreLayout.setVisibility(View.GONE);
                TextView tv = (TextView)convertView.findViewById(R.id.other_normal_text);
                tv.setText(message.getContent());
               // ((TextView)otherNormalLayout.findViewById(R.id.other_time)).setText(formatTime(message.getTime()));
            }
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Message getItem(int position) {
        return list.get(position);
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
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

}
