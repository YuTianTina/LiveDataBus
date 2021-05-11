package com.yutiantina.livedatabus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

//        DataBindingUtil.setContentView(this, R.layout.activity_java);

        LiveDataBus.get()
                .of(LiveEvents.class)
                .testOberverForever()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String o) {

                    }
                });
    }

    private void sendEvent(){
        LiveDataBus
                .get()
                .of(LiveEvents.class)
                .event1()
                .post(true);
    }
}
