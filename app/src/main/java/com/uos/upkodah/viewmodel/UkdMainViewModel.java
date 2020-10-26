package com.uos.upkodah.viewmodel;

import android.content.DialogInterface;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.uos.upkodah.dialog.SelectEstateTypeDialog;
import com.uos.upkodah.dialog.SelectLimitTimeDialog;
import com.uos.upkodah.dialog.permission.PermissionRequiringOnClickListener;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.input.UserInputData;

public class UkdMainViewModel extends ViewModel {
    private UserInputData userInputData = UserInputData.getInstanceForDataBinding();

    public UserInputData getUserInputData() {
        return userInputData;
    }
    public void setUserInputData(UserInputData userInputData) {
        this.userInputData = userInputData;
    }

    /**
     * 자기 자신의 위치를 가져오는 버튼을 누를 때
     * @param view
     */
    private View.OnClickListener getMyLocationListener;
    public void onClickBringMyPosition(View view){
        getMyLocationListener.onClick(view);
    }
    public void initListener(FragmentActivity activity){
        getMyLocationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GPS 요청으로 positionInformation을 새로 설정한다.
                if(userInputData.getPosition() == null){
                    PositionInformation.ChangeListener listener = new PositionInformation.ChangeListener() {
                        @Override
                        public void onChange(PositionInformation position) {
                            // GPS 수신에 성공하면, 그냥 해당 객체가 가진 데이터바인딩에 신호를 보낸다.
                            userInputData.alertPositionChange();
                        }
                    };
                    PositionInformation positionInformation = new PositionInformation(view.getContext(), listener);
                    userInputData.setPosition(positionInformation);
                }
                else{
                    userInputData.getPosition().requestGPS(view.getContext());
                }
            }
        };
        getMyLocationListener = new PermissionRequiringOnClickListener(getMyLocationListener, activity, GPSPermissionManager.getPermissions());
    }


    private SelectLimitTimeDialog selectLimitTimeDialog;
    private SelectEstateTypeDialog selectEstateTypeDialog;
    // Dialog 초기화
    public void initDialog(FragmentActivity activity) {
        this.selectLimitTimeDialog = new SelectLimitTimeDialog(activity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = SelectLimitTimeDialog.indexToResult(i);
                userInputData.setLimitTimeMin(result);

                dialogInterface.dismiss();
            }
        });

        this.selectEstateTypeDialog = new SelectEstateTypeDialog(activity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userInputData.setEstateType(SelectEstateTypeDialog.indexToResult(i));

                dialogInterface.dismiss();
            }
        });
    }

    /**
     * 제한 시간을 설정하는 텍스트 박스를 눌렀을 때
     * @param view
     */
    public void onClickSetTimeLimit(View view){
        selectLimitTimeDialog.show("setLimitTime");
    }

    /**
     * 매물 타입을 설정하는 텍스트 박스를 눌렀을 때
     * @param view
     */
    public void onClickSetEstateType(View view){
        selectEstateTypeDialog.show("setEstateType");
    }
}
