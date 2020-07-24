package com.example.ps10389_lequangminh_mob201_assignment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.readrss.ReadRss;


public class FragmentNews extends Fragment {
    RecyclerView recyclerView;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_news, container,false);
        recyclerView =  view.findViewById(R.id.recyclerview);
        //Call Read rss asyntask to fetch rss
        ReadRss readRss = new ReadRss(getContext(), recyclerView);
        readRss.execute();

       return view;
    }

}
