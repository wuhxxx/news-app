package com.laioffer.tinnews.save;

import com.laioffer.tinnews.retrofit.response.News;

import java.util.List;

public class SavedNewsPresenter implements SavedNewsContract.Presenter{

    private final SavedNewsContract.Model model;
    private SavedNewsContract.View view; // can not be final, because this ref will change

    public SavedNewsPresenter() {
        // model and view has no lifecycle
        // so they can be created at the same time
        // and model is final, so it needed to be set at constructor
        // but not view
        model = new SavedNewsModel();
        model.setPresenter(this);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewAttached(SavedNewsContract.View view) {
        this.view = view;
        this.model.fetchData();
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void loadSavedNews(List<News> newsList) {
        if (view != null) {
            view.loadSavedNews(newsList);
        }
    }
}
