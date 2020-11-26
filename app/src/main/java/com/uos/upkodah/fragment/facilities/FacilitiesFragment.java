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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 해당 클래스는 편의시설 아이콘 목록을 표출시키는 Fragment
 */
public class FacilitiesFragment extends Fragment {
    private boolean selectable = false;

    private RecyclerView recyclerView;
    private List<Facility> facilities;

    public void setFacilitiesData(List<Facility> facilities){
        this.facilities = new ArrayList<>(facilities);
    }
    public void setFacilitiesData(Facility...facilities){
        this.facilities = new ArrayList<>(Arrays.asList(facilities));
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
    @Override
    public void onResume() {
        super.onResume();
//        ImageButton i = getActivity().findViewById(R.id.btn_fac1);
//        String url = "https://icons-for-free.com/iconfiles/png/512/mario+mario+bros+mario+world+mushroom+toad+videogame+icon-1320196400529338074.png";
//        Glide.with(this).load(url).into(i);
    }
    public void setSelectable(boolean selectable){
        this.selectable = selectable;
    }

    private class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesViewHolder>{
        @NonNull
        @Override
        public FacilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_facility, parent,false);

            return new FacilitiesViewHolder(v);
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
        private final ImageButton btn;
        private final TextView txt;

        public FacilitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_facility);
            txt = itemView.findViewById(R.id.txt_facility_name);
        }
        public void setFacilityData(Facility facility){
            Glide.with(FacilitiesFragment.this).load(facility.imgUrl).into(btn);
            txt.setText(facility.name);
        }
    }
}
