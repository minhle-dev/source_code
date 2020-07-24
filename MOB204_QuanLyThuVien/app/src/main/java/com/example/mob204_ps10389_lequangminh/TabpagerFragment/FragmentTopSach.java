package com.example.mob204_ps10389_lequangminh.TabpagerFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterSachTrongKho;
import com.example.mob204_ps10389_lequangminh.dao.SachDAO;
import com.example.mob204_ps10389_lequangminh.dao.TheLoaiDAO;
import com.example.mob204_ps10389_lequangminh.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FragmentTopSach extends Fragment {

    public static List<Sach> dsSach = new ArrayList<>();

    RecyclerView recyclerView_TopSach;
    RecyclerView.LayoutManager layoutManager;
    AdapterSachTrongKho adapter = null;
    SachDAO sachDAO;
    EditText edTopSach;
    Button btnSerchTopSach;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_sach, container, false);
        sachDAO = new SachDAO(getContext());
        edTopSach = view.findViewById(R.id.edtTopSach);
        btnSerchTopSach = view.findViewById(R.id.btnSerchTopSach);
        recyclerView_TopSach = view.findViewById(R.id.recycle_topSach);
        recyclerView_TopSach.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_TopSach.setLayoutManager(layoutManager);

        btnSerchTopSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edTopSach.getText().toString()) > 13 ||
                        Integer.parseInt(edTopSach.getText().toString()) < 0) {
                    Toast.makeText(getContext(), "Không đúng định dạng tháng (1- 12)", Toast.LENGTH_SHORT).show();
                } else {
                    sachDAO = new SachDAO(getContext());
                    dsSach = sachDAO.getSachTop10(edTopSach.getText().toString());
                    adapter = new AdapterSachTrongKho(getContext(), dsSach);
                    recyclerView_TopSach.setAdapter(adapter);
                }
            }
        });

        return view;
    }

}

