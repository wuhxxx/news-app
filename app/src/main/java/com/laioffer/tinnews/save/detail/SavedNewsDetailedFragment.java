package com.laioffer.tinnews.save.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.WebViewActivity;
import com.laioffer.tinnews.common.BaseViewModel;
import com.laioffer.tinnews.common.TinBasicFragment;
import com.laioffer.tinnews.common.Util;
import com.laioffer.tinnews.common.ViewModelAdapter;
import com.laioffer.tinnews.retrofit.response.News;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedNewsDetailedFragment extends TinBasicFragment {


    private static final String NEWS = "news";
    private ViewModelAdapter viewModelAdapter;

    public static SavedNewsDetailedFragment newInstance(News news) {
        // serialize the news and put to bundle
        Bundle args = new Bundle();
        args.putSerializable(NEWS, news);
        // call default constructor and set bundle to args
        // so that when rotate screen, news can be saved to the newly created activity
        SavedNewsDetailedFragment fragment = new SavedNewsDetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_news_detailed, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModelAdapter = new ViewModelAdapter();
        recyclerView.setAdapter(viewModelAdapter);
        return view;
    }

    private void loadNews(News news) {
        List<BaseViewModel> viewModels = new LinkedList<>();

        // news title
        if (!Util.isStringEmpty(news.title)) {
            viewModels.add(new TitleViewModel(news.title));
        }

        // news author
        if (!Util.isStringEmpty(news.author) || !Util.isStringEmpty(news.time)) {
            viewModels.add(new AuthorViewModel(news.author, news.time));
        }

        // news image
        if (!Util.isStringEmpty((news.image))) {
            viewModels.add(new ImageViewModel(news.image));
        }

        // news description
        if (!Util.isStringEmpty(news.description)) {
            viewModels.add(new DescriptionViewModel(news.description));
        }

        // read more
        if (!Util.isStringEmpty(news.url)) {
            viewModels.add(new ReadmoreViewModel(news.url, tinFragmentManager));
        }


        viewModelAdapter.addViewModels(viewModels);
    }

    @Override
    public void onResume() {
        super.onResume();
        // use the arg bundle set in newInstance() of this class
        loadNews((News) getArguments().getSerializable(NEWS));
    }



}
