package com.laioffer.tinnews.save;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.laioffer.tinnews.R;
import com.laioffer.tinnews.common.TinBasicFragment;
import com.laioffer.tinnews.common.ViewModelAdapter;
import com.laioffer.tinnews.mvp.MvpFragment;
import com.laioffer.tinnews.retrofit.response.News;
import com.laioffer.tinnews.save.detail.SavedNewsDetailedFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedNewsFragment extends MvpFragment<SavedNewsContract.Presenter> implements SavedNewsContract.View {

//    use recycler view, abandon these
//    private TextView author;
//    private TextView description;

    //private SavedNewsAdapter savedNewsAdapter;
    // use view model with view type
    private ViewModelAdapter savedNewsAdapter;
    private TextView emptyState;



    public static SavedNewsFragment newInstance() {
        Bundle args = new Bundle();
        SavedNewsFragment fragment = new SavedNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_saved_news, container, false);
        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);

        //        view.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////                tinFragmentManager.doFragmentTransaction(SavedNewsDetailedFragment.newInstance());
////            }
////        });

//        author = view.findViewById(R.id.author);
//        description = view.findViewById(R.id.description);
//        description.setOnClickListener(v ->
//                tinFragmentManager.doFragmentTransaction(SavedNewsDetailedFragment.newInstance())
//        );

//        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);
        // set up recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyState = view.findViewById(R.id.empty_state);
        savedNewsAdapter = new ViewModelAdapter();
        recyclerView.setAdapter(savedNewsAdapter);
        return view;
    }

    @Override
    public SavedNewsContract.Presenter getPresenter() {
        return new SavedNewsPresenter();
    }

    @Override
    public void loadSavedNews(List<News> newsList) {
        // old code without recycler view
//        if (newsList.size() > 0) {
//            News news = newsList.get(newsList.size() - 1);
//            author.setText(news.getAuthor());
//            description.setText(news.getDescription());
//        }

        // new code using recycler view
//        if (newsList.size() == 0) {
//            emptyState.setVisibility(View.VISIBLE);
//        } else {
//            emptyState.setVisibility(View.GONE);
//        }
//        if (newsList != null)
//            savedNewsAdapter.setNewsList(newsList);

        if (newsList.size() == 0) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
        List<SavedNewsViewModel> models = new LinkedList<>();
        for (News news : newsList) {
            models.add(new SavedNewsViewModel(news, tinFragmentManager));
        }
        savedNewsAdapter.addViewModels(models);

    }
}
