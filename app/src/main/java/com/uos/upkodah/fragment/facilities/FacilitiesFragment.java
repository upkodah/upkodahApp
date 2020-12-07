package com.uos.upkodah.fragment.facilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.uos.upkodah.R;
import com.uos.upkodah.data.Facility;
import com.uos.upkodah.databinding.FragmentFacilitiesBinding;
import com.uos.upkodah.databinding.ListItemFacilityBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 해당 클래스는 편의시설 아이콘 목록을 표출시키는 Fragment
 */
public class FacilitiesFragment extends Fragment {
    private boolean editable = false;
    public void setEditable(boolean editable){
        this.editable = editable;
    }

    private RecyclerView recyclerView;
    private List<Facility> facilities;

    public void setFacilitiesData(List<Facility> facilities){
        if(editable){
            this.facilities = new ArrayList<>();
            for(Facility f : facilities){
                this.facilities.add(f.toEditable());
            }
        }
        else{
            this.facilities = new ArrayList<>(facilities);
        }
        if(recyclerView!=null){
            recyclerView.setAdapter(new FacilitiesAdapter());
        }

    }
    public void setFacilitiesData(Facility...facilities){
        this.setFacilitiesData(Arrays.asList(facilities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentFacilitiesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facilities, container, false);
        View mainView = binding.getRoot();
        recyclerView = mainView.findViewById(R.id.list_facility);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new FacilitiesAdapter());

        return mainView;
    }

    public String[] getSelectedFacilities(){
        ArrayList<String> list = new ArrayList<>();

        for(Facility f : facilities){
            if(f.isSelected()) list.add(f.code);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

    private class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesViewHolder>{
        @NonNull
        @Override
        public FacilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ListItemFacilityBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_facility, parent,false);

            return new FacilitiesViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull FacilitiesViewHolder holder, int position) {
            holder.setFacilityData(facilities.get(position));
        }

        @Override
        public int getItemCount() {
            return facilities!=null ? facilities.size() : 0;
        }
    }
    private class FacilitiesViewHolder extends RecyclerView.ViewHolder{
        private final ListItemFacilityBinding binding;

        public FacilitiesViewHolder(ListItemFacilityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setFacilityData(Facility facility){
            binding.setData(facility);
        }
    }
}
