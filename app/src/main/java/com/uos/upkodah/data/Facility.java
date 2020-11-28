package com.uos.upkodah.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.uos.upkodah.BR;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.fragment.facilities.FacilitiesFragment;
import com.uos.upkodah.server.extern.KakaoAPIRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Facility extends BaseObservable implements Parcelable {
    // 이 정적 필드는 앱 내에서 공통적으로 사용하는 편의시설 목록
    private static HashMap<String, Facility> globalList = new HashMap<>();
    public static void setGlobalList(Facility...facilities){
        setGlobalList(Arrays.asList(facilities));
    }public static void setGlobalList(List<Facility> facilities){
        for(Facility f : facilities){
            globalList.put(f.code, f);
        }
    }
    public static List<Facility> getGlobalList(String...selectedCodes){
        HashMap<String, Facility> resultMap = new HashMap<>(globalList);
        for(String code : selectedCodes){
            resultMap.put(code, new Facility(resultMap.get(code), true));
        }
        return new ArrayList<>(resultMap.values());
    }


    public final String code;
    public final int type;
    public final String name;
    public final String imgUrl;
    private boolean isSelected = false;
    public boolean isSelected() {
        return isSelected;
    }
    protected void setSelected(boolean selected){
        this.isSelected = selected;
    }

    @Bindable
    public float getBtnAlpha(){
        if(isSelected) return 1.0f;
        else return 0.35f;
    }

    // type이 1이면 code로, type이 0이면 name으로 필터링
    public Facility(String code, int type, String name, String imgUrl){
        this.code = code;
        this.type = type;
        this.name = name;
        this.imgUrl = imgUrl;
    }public Facility(Facility f, boolean isSelected){
        this.code = f.code;
        this.type = f.type;
        this.name = f.name;
        this.imgUrl = f.imgUrl;
        this.isSelected = isSelected;
    }
    public Facility(Parcel parcel){
        this.code = parcel.readString();
        this.name = parcel.readString();
        this.imgUrl = parcel.readString();
        this.type = parcel.readInt();
    }

    public void requestNearFacilities(Context context, GeoCoordinate coordinate, Response.Listener<String> listener){
        switch(type){
            case 0:
                // name으로 검색한다.
                KakaoAPIRequest.getSearchKeywordRequest(code, coordinate, listener, null)
                        .request(context);
            case 1:
                // code로 검색한다.
                KakaoAPIRequest.getSearchCategoryRequest(code, coordinate, listener, null)
                        .request(context);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }


    // 여기부터는 Intent에 실어 보내기 위한 Parcelable 처리
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(imgUrl);
        parcel.writeInt(type);
    }
    public static final Creator<Facility> CREATOR = new Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel parcel) {
            Facility f = new Facility(parcel);
            return f;
        }

        @Override
        public Facility[] newArray(int i) {
            return new Facility[i];
        }
    };

    public void onClickFacility(View view){
    }
    public Facility toEditable(){
        return new Editable();
    }

    @BindingAdapter("android:ukdImgUrl")
    public static void setImage(ImageButton imageButton, String url){
        Glide.with(imageButton).load(url).into(imageButton);
    }

    private class Editable extends Facility {
        public Editable() {
            super(Facility.this.code, Facility.this.type, Facility.this.name, Facility.this.imgUrl);
        }
        @Override
        protected void setSelected(boolean selected){
            super.setSelected(selected);
            notifyPropertyChanged(BR.btnAlpha);
        }

        @Override
        public void onClickFacility(View view){
            if(isSelected()){
                // 선택되어있으면 선택되지 않은 상태로
                setSelected(false);
            }
            else{
                setSelected(true);
            }
        }
    }

}
