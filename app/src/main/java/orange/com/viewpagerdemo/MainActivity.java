package orange.com.viewpagerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import orange.com.viewpagerdemo.view.PagerShufActivity1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            // 头尾加一的方式实现
            case R.id.btn_shuffling_1:
                intent.setClass(this, PagerShufActivity1.class);
                break;

            case R.id.btn_shuffling_2:

                break;
        }
        startActivity(intent);
    }
}
