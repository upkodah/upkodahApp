package com.uos.upkodah.fragment.searchbar;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentSearchBarBinding;

import static android.content.Context.INPUT_METHOD_SERVICE;

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


        final EditText editText = (EditText) view.findViewById(R.id.edtxt_search);
        final ImageButton btn = (ImageButton) view.findViewById(R.id.btn_do_search);
        if(data.isFocused()){
            editText.requestFocus();
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        if(!editable){
            editText.setInputType(InputType.TYPE_NULL);

        }

        // 엔터 리스너
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    btn.callOnClick();
                    editText.clearFocus();
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

                    manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        EditText editText = (EditText) binding.getRoot().findViewById(R.id.edtxt_search);
        if(data.isFocused()){
            editText.requestFocus();
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

            manager.showSoftInput(getActivity().getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
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
