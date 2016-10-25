package orange.com.viewpagerdemo.adapter;

import android.content.Context;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import orange.com.viewpagerdemo.R;

/**
 * Created by Administrator on 2016/10/23.
 */

public class PagerAdapter1 extends PagerAdapter {
    private List<Integer> idList;
    private ImageView imageView;
    private Pools.Pool<View> viewPool = new Pools.SimplePool<>(4);
    private Context context;

    public PagerAdapter1(List<Integer> idList) {
        this.idList = new ArrayList<>(idList);
        if (idList.size() > 2) {
            // 头尾各加一个
            this.idList.add(idList.get(0));
            this.idList.add(0, idList.get(idList.size() - 1));
        }
    }

    @Override
    public int getCount() {
        return idList == null ? 0 : idList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        context = container.getContext();
        View view = viewPool.acquire();
        if (view == null) {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_1, null);
        }
        imageView = ((ImageView) view.findViewById(R.id.item_image_1));
        imageView.setImageResource(idList.get(position));
        container.addView(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(container.getContext(), "我被点击了", Toast.LENGTH_SHORT).show();
//            }
//        });

        view.setOnTouchListener(new View.OnTouchListener() {
            long time_1 = 0;
            long time_2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context, "我被点击了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
//                    @Override
//                    public boolean onDown(MotionEvent e) {
//                        Log.e(TAG, "onDown() called with: e = [" + e + "]");
//                        return true;
//                    }
//
//                    @Override
//                    public void onShowPress(MotionEvent e) {
//                        Log.e(TAG, "onShowPress() called with: e = [" + e + "]");
//                    }
//
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        Log.e(TAG, "onSingleTapUp() called with: e = [" + e + "]");
//                        return true;
//                    }
//
//                    @Override
//                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                        Log.e(TAG, "onScroll() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
//                        return false;
//                    }
//
//                    @Override
//                    public void onLongPress(MotionEvent e) {
//                        Log.e(TAG, "onLongPress() called with: e = [" + e + "]");
//                    }
//
//                    @Override
//                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                        Log.e(TAG, "onFling() called with: e1 = [" + e1 + "], e2 = [" + e2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
//                        return false;
//                    }
//                });
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 从viewpager中移除
        container.removeView(((View) object));
        // 释放进池子中
        viewPool.release(((View) object));
    }


}
