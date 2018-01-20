package com.example.zhouwenbo.webview;

/**
 * Created by zhouwenbo on 2018/1/20.
 */

import android.app.Activity;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


public class MainActivity extends Activity {

    private WebView mWebView;
    private TextView urlPath;
    private static final String  URL = "http://api.22vi.com/geturl.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        urlPath = (TextView)findViewById(R.id.url_text);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        // Enable Javascript

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String  resArr = doGet(URL);

        String url_webview = "www.youku.com";
        try {
            JSONObject jsonObject = new JSONObject(resArr);
            url_webview = jsonObject.getString("url");
            url_webview = URLDecoder.decode(url_webview, "UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Use remote resource
        mWebView.loadUrl(url_webview);

        // Stop local links and redirects from opening in browser instead of WebView
        // mWebView.setWebViewClient(new MyAppWebViewClient());

        // Use local resource
        // mWebView.loadUrl("file:///android_asset/www/index.html");
    }

    // Prevent the back-button from closing the app
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private String doGet(String url){

        String result ="";
        try {
            URL httpUrl = new URL(url);
            //新建URL对象
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            //打开一个连接
             conn.setRequestMethod("GET");
            // 设置请求方法为GET
            conn.setReadTimeout(5000);
            //设置从服务器读取数据的超时限制为5秒
            BufferedReader reader = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            //获取服务器传递的数据输入流
            String str;
            StringBuffer sb = new StringBuffer();
            //存储读取的数据
            while((str = reader.readLine()) != null) {
                //读取数据
                sb.append(str);
            }
            result = sb.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
            catch (IOException e) {
            e.printStackTrace();
        }


        Log.d("My App", result);
        return result;
    }





}