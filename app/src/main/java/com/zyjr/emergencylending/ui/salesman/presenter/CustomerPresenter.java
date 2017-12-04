package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.CustomerBean;
import com.zyjr.emergencylending.entity.RankBean;
import com.zyjr.emergencylending.entity.WaitApplyBean;
import com.zyjr.emergencylending.ui.salesman.model.SalesmanModel;
import com.zyjr.emergencylending.ui.salesman.view.CustomerView;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/28.
 * @description :
 */

public class CustomerPresenter extends BasePresenter<CustomerView> {
    public CustomerPresenter(Context context) {
        super(context);
    }


    public void fetch(String router, String type, String router2, String router3, String type2, String isHome, String currentPage, String pageSize) {
        invokeMerge(new ProgressSubscriber<Object>(new SubscriberOnNextListener<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (o instanceof CustomerBean) {
                            CustomerBean customerBean = (CustomerBean) o;
                            if (Config.CODE_SUCCESS.equals(customerBean.getFlag())) {
                                getView().requestSuccess();
                                getView().myPerformance(customerBean);
                            } else {
                                ToastAlone.showShortToast(mContext, customerBean.getPromptMsg());
                            }

                        } else if (o instanceof RankBean) {
                            RankBean rankBean = (RankBean) o;
                            if (Config.CODE_SUCCESS.equals(rankBean.getFlag())) {
                                getView().requestSuccess();
                                getView().rankList(rankBean);
                            } else {
                                ToastAlone.showShortToast(mContext, rankBean.getPromptMsg());
                            }
                        } else if (o instanceof WaitApplyBean) {
                            WaitApplyBean waitApplyBean = (WaitApplyBean) o;
                            if (Config.CODE_SUCCESS.equals(waitApplyBean.getFlag())) {
                                getView().requestSuccess();
                                getView().waitApply(waitApplyBean);
                            } else {
                                ToastAlone.showShortToast(mContext, waitApplyBean.getPromptMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().requestError();
                    }
                }, mContext), SalesmanModel.getInstance().myPerformance(router, type)
                , SalesmanModel.getInstance().rankList(router2)
                , SalesmanModel.getInstance().waitApply(router3, type2, isHome, currentPage, pageSize));

    }

    public void fetch(String router, String type, String router2) {
        invokeMerge(new ProgressSubscriber<Object>(new SubscriberOnNextListener<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (o instanceof CustomerBean) {
                            CustomerBean customerBean = (CustomerBean) o;
                            if (Config.CODE_SUCCESS.equals(customerBean.getFlag())) {
                                getView().requestSuccess();
                                getView().myPerformance(customerBean);
                            } else {
                                ToastAlone.showShortToast(mContext, customerBean.getPromptMsg());
                            }

                        } else if (o instanceof RankBean) {
                            RankBean rankBean = (RankBean) o;
                            if (Config.CODE_SUCCESS.equals(rankBean.getFlag())) {
                                getView().requestSuccess();
                                getView().rankList(rankBean);
                            } else {
                                ToastAlone.showShortToast(mContext, rankBean.getPromptMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().requestError();
                    }
                }, mContext), SalesmanModel.getInstance().myPerformance(router, type)
                , SalesmanModel.getInstance().rankList(router2)
        );

    }

    public void myPerformance(String router, String type) {
        invoke(SalesmanModel.getInstance().myPerformance(router, type), new ProgressSubscriber<CustomerBean>(new SubscriberOnNextListener<CustomerBean>() {
            @Override
            public void onNext(CustomerBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {

                    getView().requestSuccess();
                    getView().myPerformance(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));

    }

    public void rankList(String router) {
        invoke(SalesmanModel.getInstance().rankList(router), new ProgressSubscriber<RankBean>(new SubscriberOnNextListener<RankBean>() {
            @Override
            public void onNext(RankBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().requestSuccess();
                    getView().rankList(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));

    }

    public void waitApply(String router, String type, String isHome, String currentPage, String pageSize) {
        invoke(SalesmanModel.getInstance().waitApply(router, type, isHome, currentPage, pageSize), new ProgressSubscriber<WaitApplyBean>(new SubscriberOnNextListener<WaitApplyBean>() {
            @Override
            public void onNext(WaitApplyBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().requestSuccess();
                    getView().waitApply(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));

    }

    public void getWaitApplyMore(String router, String type, String isHome, String currentPage, String pageSize) {
        invoke(SalesmanModel.getInstance().waitApply(router, type, isHome, currentPage, pageSize), new ProgressSubscriber<WaitApplyBean>(new SubscriberOnNextListener<WaitApplyBean>() {
            @Override
            public void onNext(WaitApplyBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().requestSuccess();
                    getView().waitApplyMore(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));

    }
}
