package com.uos.upkodah.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.uos.upkodah.BR;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.util.BitmapIconManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Facility extends BaseObservable implements Parcelable {
    // 이 정적 필드는 앱 내에서 공통적으로 사용하는 편의시설 목록
    private final static HashMap<String, Facility> globalList = new HashMap<>();
    public static void setGlobalList(Facility...facilities){
        setGlobalList(Arrays.asList(facilities));
    }public static void setGlobalList(List<Facility> facilities){
        Log.d("MAP", "설정된 편의시설 정보 : "+ Arrays.toString(facilities.toArray()));
        for(Facility f : facilities){
            Log.d("MAP", "편의시설 상세 : "+"CODE="+f.code+", NAME="+f.name+", URL="+f.imgUrl);
            globalList.put(f.code, f);
        }
    }
    public static List<Facility> getGlobalList(String...selectedCodes){
        HashMap<String, Facility> resultMap = new HashMap<>(globalList);

        Log.d("MAP", "선택된 편의시설(CODE) : "+ Arrays.toString(selectedCodes));

        for(String code : selectedCodes){
            Log.d("MAP", "코드="+code);
            resultMap.put(code, new Facility(globalList.get(code), true));
        }
        return new ArrayList<>(resultMap.values());
    }


    public final String code;
    public final int type;
    public final String name;
    public final String imgUrl;
    private boolean selected = false;
    public boolean isSelected() {
        return selected;
    }
    protected void setSelected(boolean selected){
        this.selected = selected;
    }

    @Bindable
    public float getBtnAlpha(){
        if(selected) return 1.0f;
        else return 0.35f;
    }

    @Bindable
    public String getTint(){
        if(selected) return "#86d2e4";
        else return "#000000";
    }

    // type이 1이면 code로, type이 0이면 name으로 필터링
    public Facility(String code, int type, String name, String imgUrl){
        this.code = code;
        this.type = type;
        this.name = name;
        this.imgUrl = imgUrl;
        this.iconBitmapKey = code;
    }public Facility(Facility f, boolean isSelected){
        this.code = f.code;
        this.type = f.type;
        this.name = f.name;
        this.imgUrl = f.imgUrl;
        this.selected = isSelected;
        this.iconBitmapKey = f.iconBitmapKey;
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
                KakaoAPIRequest.getSearchKeywordRequest(code, coordinate, 300, listener, null)
                        .request(context);
            case 1:
                // code로 검색한다.
                KakaoAPIRequest.getSearchCategoryRequest(code, coordinate, 300, listener, null)
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
    public static void setImage(final ImageButton imageButton, final String code){
        final Facility self = globalList.get(code);
        Glide.with(imageButton).asBitmap().load(self.imgUrl).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageButton.setImageBitmap(resource);

                Log.d("MAP", self.code+"의 비트맵 설정됨"+resource);
                BitmapIconManager.getInstance().put(self.code, resource);
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }
    @BindingAdapter("android:ukdBtnTint")
    public static void setTint(final ImageButton imageButton, final String tint){
        imageButton.setColorFilter(Color.parseColor(tint));
    }

    private String iconBitmapKey = null;
    @Nullable
    public String getIconBitmapKey() {
        return this.iconBitmapKey;
    }

    private class Editable extends Facility {
        public Editable() {
            super(Facility.this.code, Facility.this.type, Facility.this.name, Facility.this.imgUrl);
        }
        @Override
        protected void setSelected(boolean selected){
            super.setSelected(selected);
            notifyPropertyChanged(BR.btnAlpha);
            notifyPropertyChanged(BR.tint);
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
