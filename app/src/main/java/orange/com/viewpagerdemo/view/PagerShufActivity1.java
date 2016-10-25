package orange.com.viewpagerdemo.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import orange.com.viewpagerdemo.R;
import orange.com.viewpagerdemo.adapter.PagerAdapter1;

public class PagerShufActivity1 extends AppCompatActivity implements ViewPager.OnPageChangeListener, Handler.Callback, View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = PagerShufActivity1.class.getSimpleName();
    private ViewPager pager;
    private List<Integer> idList;
    private PagerAdapter1 adapter1;
    private LinearLayout point_layout;
    private ImageView focus_point;
    private Handler handler = new Handler(this);
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_shuf1);
        initView();
        initData();
        adapter1 = new PagerAdapter1(idList);
        pager.setAdapter(adapter1);
        for (int i = 0; i < idList.size(); i++) {
            // 构造好这个小白点
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.point);
            int v = (int) getResources().getDimension(R.dimen.point_padding);
            pointView.setPadding(v, v, v, v);
            // 加进去
            point_layout.addView(pointView);

        }
        pager.addOnPageChangeListener(this); // 设置滑动监听

        pager.setOnTouchListener(this); // 注册触摸事件,将handler取消
        pager.setCurrentItem(1); // 由于adapter中前面加了一个,所以将它移动到真正的第一个


        pager.post(new Runnable() { // 设置小白点的位置,注意一定要用 post 方法
            @Override
            public void run() {  // 这里 point_layout 的宽必须是wrap_content 否则错位
                ViewCompat.setTranslationX(focus_point, point_layout.getLeft());
            }
        });
        handler.obtainMessage(0, 1, 0).sendToTarget();
    }

    private void initData() {
        idList = new ArrayList<>();
        idList.add(R.mipmap.girl_1);
        idList.add(R.mipmap.girl_2);
        idList.add(R.mipmap.girl_3);
        idList.add(R.mipmap.girl_4);
        idList.add(R.mipmap.girl_5);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        point_layout = (LinearLayout) findViewById(R.id.point_layout);
        focus_point = (ImageView) findViewById(R.id.focus_point);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 当pager滑动时,让小白点跟着滑动
        int value = point_layout.getLeft() + (position - 1 + positionOffsetPixels) * focus_point.getWidth();
//        value = Math.max(value, point_layout.getLeft());
//        value = Math.min(value, point_layout.getRight() - point_layout.getWidth());
        ViewCompat.setTranslationX(focus_point, value);
    }

    @Override
    public void onPageSelected(int position) {
        //  第一个时跳到最后一个 , 最后一个时跳到第一个
        if (position == 0) {
            pager.setCurrentItem(adapter1.getCount() - 2, false);
        } else if (position == adapter1.getCount() - 1) {
            pager.setCurrentItem(1, false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                int arg1 = msg.arg1;
                if (arg1 > adapter1.getCount() - 2) { // 到达最后一个跳到最开始
                    arg1 = 1;
                }
                // 只有跳回第一个时 直接跳回
                pager.setCurrentItem(arg1, 1 != arg1);
                Message message = handler.obtainMessage(0, ++arg1, 0);
                handler.sendMessageDelayed(message, 5000);
                break;
        }
        return true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "onTouch: ");
        int currentItem = pager.getCurrentItem();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(0);
                break;
            case MotionEvent.ACTION_MOVE:
                currentItem = pager.getCurrentItem();
                break;
            case MotionEvent.ACTION_UP:
                Message message = handler.obtainMessage(0, currentItem, 0);
                handler.sendMessageDelayed(message, 5000);
                break;
        }
        return false;

        // 交给手势处理
//        gestureDetector = new GestureDetectorCompat(this, this);
//        gestureDetector.setOnDoubleTapListener(this); // 注册双击事件
//        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {  // 只是按下
        Log.e(TAG, "onDown: ");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) { // 按下没有动,注意强调的是没有松开,或者拖动
        Log.e(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) { // 点击行为
        Log.e(TAG, "onSingleTapUp: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { // 拖动行为
        Log.e(TAG, "onScroll() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) { //长按事件
        Log.e(TAG, "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { //快速滑动
        Log.e(TAG, "onFling() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { // 严格的单击,即这个单击触发后 后面不可能再跟着一个单击
        Log.e(TAG, "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) { //双击
        Log.e(TAG, "onDoubleTap: ");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) { // 表示发生了双击事件
        Log.e(TAG, "onDoubleTapEvent: ");
        return false;
    }
}
