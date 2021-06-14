package com.example.x_plan;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LevelTwo extends AppCompatActivity {

//    private final static String TAG = "UOfly Android Thread ==>";
    private Player player1 = new Player();
    private Player player2 = new Player();
    private Func f = new Func();
    private int count = 0;
    private boolean attackFlag = false;
    private String str;
    private boolean role1_run = false;
    private boolean role2_run = false;
    private boolean is_run = false;
    private Handler mHandler = new Handler();
    private Handler mHandler_ = new Handler();
    private int countRole1 = 0;
    private int countRole2 = 0;
    private Button a = null;
    private Button b = null;
    private TextView start1 = null;
    private TextView start2 = null;
    private ImageView end=null;
    private ImageView role1 = null;
    private ImageView role2 = null;
    private boolean is_Pop1 = false;
    private boolean is_Pop2 = false;
    final AnimatorSet[] animatorSet = new AnimatorSet[2];

    int[] draw1 = { R.drawable.role1_hp6,R.drawable.role1_hp5,R.drawable.role1_hp4,R.drawable.role1_hp3,R.drawable.role1_hp2,R.drawable.role1_hp1,R.drawable.role1_hp0};
    int[] draw2 = { R.drawable.role2_hp6,R.drawable.role2_hp5,R.drawable.role1_hp4,R.drawable.role2_hp3,R.drawable.role2_hp2,R.drawable.role1_hp1,R.drawable.role2_hp0};

    //玩家1的线程
    private Runnable mRunnable = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            role1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(is_Pop1 == false) {
                        initPopWindow(v, player1);
                        is_Pop1 = true;
                    }
                }
            });
            cal();

            if(is_run == true && role1_run == false){

                Func f = new Func();
                animatorSet[0] = f.Move_(player1.player,player1.views,150);
                role1_run = true;
            }
            ImageView enemy = findViewById(R.id.enemy);
            final ImageView[] enemyViews = new ImageView[]{enemy};
            if(f.FindEnemy(role1,enemyViews,383) == 0 && countRole1<draw1.length && attackFlag==false){
                role1.setImageDrawable(getResources().getDrawable(draw1[countRole1]));
                countRole1++;
                attackFlag = true;

            }else attackFlag = false;
            if(countRole1 == draw1.length) animatorSet[0].pause();


            mHandler.postDelayed(mRunnable, 1000);
        }
    };


    //玩家2的线程
    private Runnable mRunnable_ = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("LongLogTag")
        public void run() {

            role2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(is_Pop2 == false) {
                        initPopWindow(v, player2);
                        is_Pop2 = true;
                    }
                }
            });
            cal();
            if(is_run == true && role2_run == false){

                        Func f = new Func();
                        animatorSet[1] = f.Move_(player2.player,player2.views,150);
                        role2_run = true;
            }
            ImageView enemy = findViewById(R.id.enemy);
            final ImageView[] enemyViews = new ImageView[]{enemy};
            if(f.FindEnemy(role2,enemyViews,383) == 0 && countRole2 < draw2.length && attackFlag==false){
                role2.setImageDrawable(getResources().getDrawable(draw2[countRole2]));
                countRole2++;
                attackFlag = true;

            }else attackFlag = false;
            if(countRole2 == draw2.length) animatorSet[1].pause();
            System.out.println("什么情况"+f.victory(role2,end));

            if(f.victory(role2,end)) {
                Intent activity_change= new Intent(LevelTwo.this, SuccessActivity.class);    //切换 Activityanother至MainActivity
                Bundle bundle = new Bundle();// 创建Bundle对象
                bundle.putInt("data",2 );//  放入data值为int型

                activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
                startActivity(activity_change);//  开始跳转
            }

                mHandler.postDelayed(mRunnable_, 1000);
        }
    };


    private void cal(){
        for (int i=0;i<3;i++) {
            try {
                Iterator it = player1.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();

                    if(entry.getKey().equals("0")){//路径选择
                        for(int j=player1.views.size()-1;j>0;j--){
                            player1.views.remove(j);
                        }
                        for(int j=0;j<entry.getValue().toString().length();j++){
                            if(((entry.getValue().toString()).charAt(j))=='1') {
                                player1.views.add(a);
                            }
                            else if(((entry.getValue().toString()).charAt(j))=='2'){
                                player1.views.add(b);
                            }
//                            player1.time.add((long) 5000);
                        }
                        player1.views.add(end);
                    }
                }
            }catch (Exception e){ }
            try {
                Iterator it = player2.getInstructions(i).entrySet().iterator();
                while (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();

                    if(entry.getKey().equals("0")){
                        for(int j=player2.views.size()-1;j>0;j--){
                            player2.views.remove(j);
                        }
                        for(int j=1;j<=entry.getValue().toString().length();j++){
                            if(((entry.getValue().toString()).charAt(j-1))=='1') {
                                player2.views.add(a);
                            }
                            else if(((entry.getValue().toString()).charAt(j-1))=='2'){
                                player2.views.add(b);
                            }
//                            player2.time.add((long) 5000);
                        }
                        player2.views.add(end);
                    }
                }
            }catch (Exception e){}
        }
    }

    private void initPopWindow(View v, final Player player){
        View view= LayoutInflater.from(LevelTwo.this).inflate(R.layout.level2_pop1,null,false);
        Button back = view.findViewById(R.id.back);
        //指令1,2,3
        Button one = view.findViewById(R.id.ins_one);
        Button two = view.findViewById(R.id.ins_two);
        Button three = view.findViewById(R.id.ins_three);
        //指令123的内容
        TextView one_text=view.findViewById(R.id.ins_one_text);
        TextView two_text=view.findViewById(R.id.ins_two_text);
        TextView three_text=view.findViewById(R.id.ins_three_text);
        one_text.setText(player.getIns_text(0));
        two_text.setText(player.getIns_text(1));
        three_text.setText(player.getIns_text(2));

        final PopupWindow popupWindow = new PopupWindow(view, 1600, 1000, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,0,player);
            }
        });
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,1,player);
            }
        });
    }


    private void initPopWindow2(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelTwo.this).inflate(R.layout.level2_pop2, null, false);
        //通过view获取pop界面的组件
        Button clear = view.findViewById(R.id.clear);
        Button back=view.findViewById(R.id.back);
        Button move = view.findViewById(R.id.move);
        Button signal=view.findViewById(R.id.signal);
        final TextView ins1 = view.findViewById(R.id.ins1);
        if(player.getIns_text(ins)!= ""){
            ins1.setText(player.getIns_text(ins) + "\n");
        }
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
        //设置路径点
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("路径点选择:start->",ins);
                popupWindow.dismiss();
                initPopWindow3(v,ins,player);
            }
        });
        //清空该指令
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIns_text("",ins);
                player.getInstructions(ins).clear();
                popupWindow.dismiss();
                initPopWindow(v,player);
            }
        });
    }
    private void initPopWindow3(View v, final int ins, final Player player){
        View view = LayoutInflater.from(LevelTwo.this).inflate(R.layout.level2_pop3, null, false);
        Button back = view.findViewById(R.id.back);
        Button A = view.findViewById(R.id.A1);
        Button B = view.findViewById(R.id.B1);
        Button save = view.findViewById(R.id.save1);
        final TextView choose = view.findViewById(R.id.choose);
        final PopupWindow popupWindow = new PopupWindow(view, 1600, 900, true);
        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80808080));
        popupWindow.showAsDropDown(v, 100 - v.getLeft(), -v.getBottom());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initPopWindow2(v,ins,player);
            }
        });
        final String[] path = {""};
        final String[] path_text = {"选择的路径为：start->"};
        final String[] now = {""};
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "1") {
                    path_text[0]+="1->";
                    path[0] += "1";
                    now[0]="1";
                    choose.setText(path_text[0]);
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now[0] != "2"){
                    path_text[0]+="2->";
                    path[0] += "2";
                    now[0]="2";
                    choose.setText(path_text[0]);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path_text[0]+="end";
                player.setIns_text(path_text[0],ins);
                Map<String,String> map=new HashMap<>();
                map.put("0",path[0]);
                player.setInstructions(map,ins);
                popupWindow.dismiss();
                initPopWindow2(v,ins,player);
            }
        });
    }

    public void init(){
        a = findViewById(R.id.A);
        b = findViewById(R.id.B);
        end = findViewById(R.id.end);
        role1 = findViewById(R.id.role1);
        role2 = findViewById(R.id.role2);
        start1 = findViewById(R.id.start1);
        start2 = findViewById(R.id.start2);

        final ImageView enemy = findViewById(R.id.enemy);


        player1.player=(ImageView) findViewById(R.id.role1);
        player1.views.add(start1);
        player1.views.add(a);
        player1.views.add(b);
        player1.views.add(end);
        player2.player=(ImageView) findViewById(R.id.role2);
        player2.views.add(start2);
        player2.views.add(a);
        player2.views.add(b);
        player2.views.add(end);
    }




    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level2);


        init();

        // 通过Handler启动线程
        mHandler.post(mRunnable);  //玩家1
        mHandler_.post(mRunnable_); //玩家2

        Button run = findViewById(R.id.run);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_run = true;
            }
        });


    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandler.removeCallbacks(mRunnable);
        try {
            Thread.sleep(70000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler_.removeCallbacks(mRunnable_);
        super.onDestroy();
    }
}