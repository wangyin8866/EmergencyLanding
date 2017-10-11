package com.zyjr.emergencylending.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseFragment<T extends BasePresenter<V>, V> extends Fragment {
    protected T mPresenter;
    protected String tag;
    protected Context mContext;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mPresenter = createPresenter();
        if (mPresenter != null) {

            mPresenter.attach((V) this);
            mPresenter.setLifeSubscription(new LifeSubscription() {
                @Override
                public void bindSubscription(Subscription subscription) {
                    if (mCompositeSubscription == null) {
                        mCompositeSubscription = new CompositeSubscription();
                    }
                    mCompositeSubscription.add(subscription);
                }
            });
        }
        tag = getClass().getSimpleName();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {

            mPresenter.detach();
        }
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.clear();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    protected abstract T createPresenter();

}

