package com.laioffer.tinnews.tin;

import android.annotation.SuppressLint;
import com.laioffer.tinnews.TinApplication;
import com.laioffer.tinnews.database.AppDatabase;
import com.laioffer.tinnews.retrofit.NewsRequestApi;
import com.laioffer.tinnews.retrofit.RetrofitClient;
import com.laioffer.tinnews.retrofit.response.News;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TinModel implements TinContract.Model {

    //keep the reference of TinContract.Presenter
    private TinContract.Presenter presenter;

    // hold a ref to http client for news
    private final NewsRequestApi newsRequestApi;

    // database ref
    private final AppDatabase db;

    TinModel() {
        newsRequestApi = RetrofitClient.getInstance().create(NewsRequestApi.class);
        db = TinApplication.getDataBase();
    }

    @Override
    public void setPresenter(TinContract.Presenter presenter) {
        // assign the presenter
        this.presenter = presenter;
    }

    @Override
    public void fetchData(String country) {
        newsRequestApi.getNewsByCountry(country)
                .subscribeOn(Schedulers.io())
                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    presenter.showNewsCard(baseResponse.articles);
                });
    }

    //implement the saveFavoriteNews
    @SuppressLint("CheckResult")
    @Override
    public void saveFavoriteNews(News news) {
        // save the liked news, using other thread to perform this task
        // if error happens when saving news, notify presenter
        // presenter then will call view's onError(), which will create a toast to show message
        Disposable disposable = Completable
                .fromAction(() -> db.newsDao().insertNews(news))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->{

                }, error -> {
                    presenter.onError();
                });
    }
}
