package com.example.x_plan;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class Func {

    public Func() {
    }

    //属性动画，role移动的图片，View需要经过的路径点,time表示每次移动的时间
    public AnimatorSet Move(ImageView role, View[] views, long[] time) {
        //属性动画，X是水平移动的动画，y是垂直移动的动画
        ObjectAnimator[] aniSetX = new ObjectAnimator[views.length];
        ObjectAnimator[] aniSetY = new ObjectAnimator[views.length];
        //将所有需要移动的路径生成动画，并放入数组
        for (int i = 0; i < views.length; i++) {
            //获取坐标
            int[] rolePos = new int[2];
            int[] enemyPos = new int[2];
            role.getLocationOnScreen(rolePos);
            views[i].getLocationOnScreen(enemyPos);
            //生成属性动画
            ObjectAnimator aniX = ObjectAnimator.ofFloat(role, "translationX", (float) (enemyPos[0] - rolePos[0]));
            ObjectAnimator aniY = ObjectAnimator.ofFloat(role, "translationY", (float) (enemyPos[1] - rolePos[1]));
            //设置属性动画时间
            aniX.setDuration(time[i]);
            aniY.setDuration(time[i]);
            //添加进数组
            aniSetX[i] = aniX;
            aniSetY[i] = aniY;
        }
        //属性动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        //每个动画执行的顺序,play表示本次移动，with表示一起移动，before表示在play后面移动
        for (int i = 0; i < views.length - 1; i++) {
            animatorSet.play(aniSetX[i]).with(aniSetY[i]).before(aniSetX[i + 1]).before(aniSetY[i + 1]);
        }
        //启动动画
        animatorSet.start();
        return animatorSet;
    }

    //属性动画，role移动的图片，View需要经过的路径点,time表示每次移动的时间
    public AnimatorSet Move_(ImageView role, List<View> views, int speed) {
        //属性动画，X是水平移动的动画，y是垂直移动的动画
        ObjectAnimator[] aniSetX = new ObjectAnimator[views.size()];
        ObjectAnimator[] aniSetY = new ObjectAnimator[views.size()];
        //计算运动时间
        long[] times = new long[views.size()];
        times[0] = 500;
        for (int i = 0; i < views.size() - 1; i++) {
            long time = (long) (Math.sqrt(Math.abs(Math.pow((views.get(i).getX() - views.get(i + 1).getX()), 2) + Math.pow((views.get(i).getY() - views.get(i + 1).getY()), 2))) / speed) * 1000;
            times[i + 1] = time;
        }
        //将所有需要移动的路径生成动画，并放入数组
        for (int i = 0; i < views.size(); i++) {
            //生成属性动画
            ObjectAnimator aniX = ObjectAnimator.ofFloat(role, "translationX", views.get(i).getLeft() - role.getLeft());
            ObjectAnimator aniY = ObjectAnimator.ofFloat(role, "translationY", views.get(i).getBottom() - role.getBottom());
            //设置属性动画时间
            aniX.setDuration(times[i]);
            aniY.setDuration(times[i]);
            //添加进数组
            aniSetX[i] = aniX;
            aniSetY[i] = aniY;
        }
        //属性动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        //每个动画执行的顺序,play表示本次移动，with表示一起移动，before表示在play后面移动
        for (int i = 0; i < views.size() - 1; i++) {
            animatorSet.play(aniSetX[i]).with(aniSetY[i]).before(aniSetX[i + 1]).before(aniSetY[i + 1]);
        }
//        //每个动画执行的时间
//        animatorSet.setDuration(3000);
        //启动动画
        animatorSet.start();
        return animatorSet;
    }

    //识别与敌人之间的距离，role识别主体即人物，enemy所有敌人的数组，roleRange人物发现敌人的距离
    //发现敌人，返回对应敌人的id，否则返回-1
    public int FindEnemy(ImageView role, ImageView[] enemy, double roleRange) {
        //获取坐标
        int[] rolePos = new int[2];
        int[] enemyPos = new int[2];
        role.getLocationOnScreen(rolePos);
        for (int i = 0; i < enemy.length; i++) {
            enemy[i].getLocationOnScreen(enemyPos);

            double distance = Math.sqrt(Math.abs(Math.pow((enemyPos[0] - rolePos[0]), 2) + Math.pow((enemyPos[1] - rolePos[1]), 2)));
            if (distance <= roleRange) {
                return i;
            }
        }
        return -1;
    }

    //识别与敌人之间的距离，role识别主体即人物，enemy所有敌人的数组，roleRange人物发现敌人的距离
    public boolean FindEnemy_(ImageView role, View[] enemy, double roleRange) {
        float roleLeft = role.getX();
        float roleTop = role.getY();
        for (int i = 0; i < enemy.length; i++) {
            int enemyLeft = enemy[i].getLeft();
            int enemyTop = enemy[i].getTop();
            double distance = Math.sqrt(Math.abs(Math.pow((enemyLeft - roleLeft), 2) + Math.pow((enemyTop - roleTop), 2)));
            if (distance <= roleRange) {
                return true;
            }
        }
        return false;
    }

    //设置信号
    public void setSignal(int number, boolean[] signal, boolean b) {
        signal[number] = b;
    }

    //识别信号
    public boolean identifySignal(int number, boolean[] signal, boolean b) {
        if (signal[number] == b) {
            return true;
        }
        return false;
    }

    //胜利条件判断
    public boolean victory(ImageView role, ImageView EndPoint) {
        //获取坐标
        if (role.getLeft() == EndPoint.getLeft() && role.getBottom() == EndPoint.getBottom()) {
            return true;
        } else
            return false;
    }
}