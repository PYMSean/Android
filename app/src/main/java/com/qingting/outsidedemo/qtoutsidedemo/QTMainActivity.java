package com.qingting.outsidedemo.qtoutsidedemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QTMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "QTMainActivity";


    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    Unbinder unbinder;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    private Fragment fragment_now = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtmain);
        unbinder = ButterKnife.bind(this);
//        init();

//        getData();


    }


    public void getData(){
        /*okHttp*/
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.sosoapi.com/pass/mock/12003/test/gettest").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                ResultBeans resultBeans = gson.fromJson(string,ResultBeans.class);

            }
        });



    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        //默认选中第一个item
        bottomNavigationView.setSelectedItemId(R.id.item_1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    /*navitaionItemSelected事件监听*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        changePageFragment(item.getItemId());
        return true;
    }


    /*点击导航栏改变fragment*/
    public void changePageFragment(int id) {

        switch (id) {
            case R.id.item_1:
                if (fragment1 == null) {
                    fragment1 = Fragment1.newInstance();
                }

                switchFragment(fragment_now,fragment1);
                break;

            case R.id.item_2:
                if (fragment2==null) {
                    fragment2 = Fragment2.newInstance();
                }
                switchFragment(fragment_now,fragment2);
                break;

            case R.id.item_3:
                if (fragment3 ==null) {
                    fragment3 = Fragment3.newInstance();
                }
                switchFragment(fragment_now,fragment3);
                break;

            default:
                 if (fragment3 ==null) {
                     fragment3 = Fragment3.newInstance();
                 }
                 switchFragment(fragment_now,fragment3);
                 break;

        }

    }


    /*切换页面(fragment)*/
    public void switchFragment(Fragment from,Fragment to) {
        if (to == null) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from==null) {
                transaction.add(R.id.fl_fragment,to).show(to).commit();
            }else {
                //掩藏当前的fragment，add下一个fragment到Activity中
                transaction.hide(from).add(R.id.fl_fragment,to).commitAllowingStateLoss();
            }
        }else {
            transaction.hide(from).show(to).commit();
        }

        fragment_now = to;
    }



}
