package cn.bridge.reader.view;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 闪屏界面
 */
public class SplashActivity extends AppCompatActivity {
    public static final int CODE = 1001;
    public static final int TOTAL_TIME = 3000;
    public static final int INTERVAL_TIME = 1000;
    public static final String DELAY_TIP = "秒,点击跳过";
    private TextView myTV;

    public static class MyHandler extends Handler { /* 弱引用,如果未使用,便会回收回去*/
        public final WeakReference<SplashActivity> myWeakRef;

        public MyHandler(SplashActivity splashActivity) {
            myWeakRef = new WeakReference<>(splashActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity act = myWeakRef.get();
            if (msg.what == CODE)

                if (act != null) {
                    int time = msg.arg1;

                    //设置textview,更新UI
                    act.myTV.setText(time / INTERVAL_TIME + DELAY_TIP);

                    // 发送倒计时;obtain 获得
                    Message message = Message.obtain();
                    message.what = CODE;
                    message.arg1 = time - INTERVAL_TIME;
                    
                    if (time > 0) {
                        sendMessageDelayed(message, INTERVAL_TIME);
                    } else {
                        // 等待倒计时3秒钟,跳至书籍列表界面
                        BookListActivity.start(act);

                        // 跳转之后将闪屏页面关闭
                        act.finish();
                    }

                }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.bridge.reader.R.layout.activity_splash);
        myTV = (TextView) findViewById(cn.bridge.reader.R.id.time_text_view);

        final MyHandler handler = new MyHandler(this);
        Message msg = Message.obtain();
        msg.what = CODE;
        msg.arg1 = TOTAL_TIME;
        handler.sendMessage(msg);

        myTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击跳过3秒钟,跳至书籍列表界面
                BookListActivity.start(SplashActivity.this);

                // 跳转之后将闪屏页面关闭
                SplashActivity.this.finish();

                handler.removeMessages(CODE);
            }
        });
    }
}
