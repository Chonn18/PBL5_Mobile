package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.Model.Helper.FirebaseUsers;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.ViewModel.HistoryAdapter;
import com.project.pbl5_mobile.databinding.FragmentHistory1Binding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class History1Fragment extends Fragment {

    private FragmentHistory1Binding binding;
    private ArrayList<User> uList;
    private ArrayList<User> uListold;
    private HistoryAdapter historyAdapter;

    private Student s;


    public History1Fragment() {
        // Required empty public constructor
    }


    public static History1Fragment newInstance() {
        History1Fragment fragment = new History1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            s = (Student) getArguments().getSerializable("student");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_history1, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uList = new ArrayList<>();
        binding.rvHistory.setHasFixedSize(true);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        historyAdapter = new HistoryAdapter(uList);
        binding.rvHistory.setAdapter(historyAdapter);

        if(s==null){
            FirebaseUsers.getInstance().getAllUsers("Users").addOnCompleteListener(new OnCompleteListener<List<User>>() {
                @Override
                public void onComplete(@NonNull Task<List<User>> task) {
                    if(task.isSuccessful()) {
                        uList.addAll(task.getResult());
                        Collections.reverse(uList);
                        historyAdapter.notifyDataSetChanged();
                    }
                    else{
                        Exception ex = task.getException();
                    }
                }
            });
        }
        else{
            int id = s.getId();
            FirebaseUsers.getInstance().getUsersByID("Users",id).addOnCompleteListener(new OnCompleteListener<List<User>>() {
                @Override
                public void onComplete(@NonNull Task<List<User>> task) {
                    if(task.isSuccessful()) {
                        uList.addAll(task.getResult());
                        historyAdapter.notifyDataSetChanged();
                    }
                    else{
                        Exception ex = task.getException();
                    }
                }
            });
        }

        binding.search.clearFocus();
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });



    }

    private void filterList(String Text) {
        ArrayList<User> filterList = new ArrayList<>();
        if(Text == null){
            historyAdapter.updateList(uList);
        }else {
            for ( User u : uList){
//            if(u.getName() == null){
//                return;
//            }
                if(u.getName().toLowerCase().contains(Text.toLowerCase())){
                    filterList.add(u);
                }
                if(u.getTime().contains(Text)){
                    filterList.add(u);
                }
                if(u.getId().toString().contains(Text)){
                    filterList.add(u);
                }
            }
            if(filterList.isEmpty()){
                Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
                historyAdapter.updateList(filterList);
            }else{
                historyAdapter.updateList(filterList);
            }
        }

    }
}