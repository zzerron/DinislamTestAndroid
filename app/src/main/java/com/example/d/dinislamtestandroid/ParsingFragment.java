package com.example.d.dinislamtestandroid;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.d.dinislamtestandroid.parsing.JsonDeserial;
import com.example.d.dinislamtestandroid.parsing.PostsAdapter;
import com.example.d.dinislamtestandroid.parsing.Quote;
import com.example.d.dinislamtestandroid.parsing.Something;
import com.example.d.dinislamtestandroid.parsing.Support;

import java.util.ArrayList;
import java.util.List;

public class ParsingFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Quote> posts;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_parsing, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        posts = new ArrayList<>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.posts_recycle_view);

        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        MyTask myTask = new MyTask();
        myTask.execute();

        return rootView;
    }
//   Выполняем Асинхронные задачи: 1 прогресс бар, до тех пор пока идет парсинг с интернета
    class MyTask extends AsyncTask<Void, Void, Void> {


//        Во вермя
        @Override
        protected Void doInBackground(Void... result) {
            List<Quote> quotes = new ArrayList<>();
//            Возврощает уже декодировааную строку, равную body of response
            String  string = Support.getText();
//            Получаем из строки формата JSON, объект Something
            Something something = JsonDeserial.read(string);
            for (Quote quote : something.getQuotes()){
                posts.add(quote);
            }
            return null;
        }


//        По завершению
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//          Делаем прогресс бар невидимым
            progressBar.setVisibility(ProgressBar.INVISIBLE);
//          Заполняем список данными
            PostsAdapter adapter = new PostsAdapter(posts);
            recyclerView.setAdapter(adapter);
        }
    }
}