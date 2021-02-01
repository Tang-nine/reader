package cn.bridge.reader.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cn.bridge.reader.R;
import cn.bridge.reader.response.BookListResult;
import cz.msebera.android.httpclient.Header;

/**
 * 书籍列表界面
 * Created by user on 21-1-21.
 */

public class BookListActivity extends AppCompatActivity {
    private static final String TAG = "BookListActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_list);

        String url = "https://www.imooc.com/api/teacher?type=10";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final String resultStr = new String(responseBody);

                Gson gson = new Gson();

                BookListResult bookListResult = gson.fromJson(resultStr, BookListResult.class);

                List<BookListResult.Book> books = bookListResult.getData();

                Log.i(TAG, "onSuccess: " + resultStr);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {

            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, BookListActivity.class);
        context.startActivity(intent);
    }
}
