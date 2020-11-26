package com.uos.upkodah.fragment.searchbar;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentSearchBarBinding;

public class SearchBarFragment extends Fragment {
    private FragmentSearchBarBinding binding;
    private SearchBarData data = new SearchBarData();
    private boolean editable = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_bar, container, false);
        binding.setData(data);
        View view = binding.getRoot();

        if(!editable){
            EditText editText = (EditText) view.findViewById(R.id.edtxt_search);
            editText.setInputType(InputType.TYPE_NULL);
        }

        return view;
    }
    public void setData(SearchBarData data){
        this.data = data;
    }
    public void setEditDisable(){
        editable = false;

    }

    public interface BtnListener {
        public void onClickSearchBtn(View view, String searchText);
    }
}
