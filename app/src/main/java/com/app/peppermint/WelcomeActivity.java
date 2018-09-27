package com.app.peppermint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loadData();
    }

    private void loadData() {

        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        /*Flowable.just(0)
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.print("integer--"+integer);
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.print("integer--t");
                    }

                    @Override
                    public void onComplete() {
                        System.out.print("integer--onComplete");
                    }
                });*/
    }


}
