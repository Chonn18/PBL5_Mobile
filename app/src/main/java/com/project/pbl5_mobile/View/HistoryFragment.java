package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.FragmentHistoryBinding;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
//        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_history, container , false);
//        return binding.getRoot();
    }


}