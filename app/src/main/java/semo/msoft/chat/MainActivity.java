package semo.msoft.chat;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private InputMethodManager im; // 키보드 제어를 위함
    private TextView msgInput;
    private Button sendBT;
    private Button emoji;
    private Button more;
    private LinearLayout moreLayout;
    private LinearLayout emojiLayout;


    public LinearLayoutManager mLinearLayoutManager;
    public RecyclerView listView;
    public MyAdapter adapter;



    //  == OnCreate() == //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        msgInput = (TextView)findViewById(R.id.messageInput);
        msgInput.setOnClickListener(this);
        sendBT = (Button)findViewById(R.id.sendBT);
        sendBT.setOnClickListener(this);
        emoji = (Button)findViewById(R.id.emoji);
        emoji.setOnClickListener(this);
        more = (Button)findViewById(R.id.moreBT);
        more.setOnClickListener(this);
        moreLayout = (LinearLayout)findViewById(R.id.moreLayout);
        emojiLayout = (LinearLayout) findViewById(R.id.emojiLayout);



        //  == 리스트뷰 관련 == //
        adapter = new MyAdapter(MainActivity.this,"21111768");
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        listView = (RecyclerView)findViewById(R.id.list);
        listView.setLayoutManager(mLinearLayoutManager);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        //  == 리스트뷰 관련 끝 == //


        //  == msgInput을 벗어난 터치시 키보드 숨기기 == //
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                im.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if(moreLayout.getVisibility() == View.VISIBLE){
                    ObjectAnimator.ofFloat(more,"rotation",45,0).start();
                    moreLayout.setVisibility(View.GONE);
                }
                else{
                    emojiLayout.setVisibility(View.GONE);
                    emoji.setBackgroundResource(R.drawable.emoji);
                }
                return false;
            }
        });
        //  == msgInput을 벗어난 터치시 키보드 숨기기 끝 == //



        //  == 툴바 == //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  ========== //

        //  == Draswer == //
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setStatusBarBackgroundColor(Color.parseColor("#88a7c4"));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                im.hideSoftInputFromWindow(msgInput.getWindowToken(), 0);

            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //  ========== //


    }

    //  == OnCreate() 끝== //




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.drawer) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case (R.id.sendBT):{
                scrollToBottom();
                if(msgInput.getText().toString().equals("")){
                }
                else {
                    Message msg = new Message();
                    msg.setContent(msgInput.getText().toString());
                    msg.setSender("21111768");
                    msg.setTime(new Date());
                    adapter.addItem(msg);
                    msgInput.setText("");
                }

                break;
            }


            case(R.id.messageInput):{

                if(moreLayout.getVisibility() == View.VISIBLE){
                    ObjectAnimator.ofFloat(more,"rotation",45,0).start();
                    moreLayout.setVisibility(View.GONE);
                }

                emojiLayout.setVisibility(View.GONE);
                emoji.setBackgroundResource(R.drawable.emoji);

                scrollToBottom();
                break;

            }

            case(R.id.emoji):{
                im.hideSoftInputFromWindow(msgInput.getWindowToken(), 0);
                if(moreLayout.getVisibility() == View.VISIBLE){
                    ObjectAnimator.ofFloat(more,"rotation",45,0).start();
                    moreLayout.setVisibility(View.GONE);
                }

                if(emojiLayout.getVisibility() == View.GONE){
                    emojiLayout.setVisibility(View.VISIBLE);
                    emoji.setBackgroundResource(R.drawable.emoji_select);
                }
                else{
                    emojiLayout.setVisibility(View.GONE);
                    emoji.setBackgroundResource(R.drawable.emoji);
                }

                break;
            }
            case(R.id.moreBT):{
                emoji.setBackgroundResource(R.drawable.emoji);
                im.hideSoftInputFromWindow(msgInput.getWindowToken(), 0);
                emojiLayout.setVisibility(View.GONE);
                if(moreLayout.getVisibility()==View.GONE){
                    moreLayout.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(more,"rotation",0,45).start();
                }
                else{
                    moreLayout.setVisibility(View.GONE);
                    ObjectAnimator.ofFloat(more,"rotation",45,0).start();
                }

                break;
            }
            default:break;
        }
    }

    public void removeTime(int position){
        Log.d(TAG, " removeTime() position: " + position);

    }

    public void scrollToBottom(){
        mLinearLayoutManager.scrollToPositionWithOffset(listView.getAdapter().getItemCount(),100);
    }

}
