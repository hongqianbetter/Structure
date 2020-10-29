package com.example.structure;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/27
 */
public class SkinFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater from = LayoutInflater.from(getContext());
        Log.e("sss",from.toString()+" "+getActivity().getResources().toString()+"---"+getActivity().getResources().getAssets().toString()+"fragment");
        Log.e("sss",getActivity().toString()+"---"+getContext().toString()+"fragment");

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_skin, container, false);
        return root;
    }


    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        return super.onGetLayoutInflater(savedInstanceState);
    }
}
