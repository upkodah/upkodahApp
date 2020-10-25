package com.uos.upkodah.viewmodel;

import android.content.DialogInterface;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.uos.upkodah.dialog.SelectLimitTimeDialog;
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

    public void onClickBringMyPosition(View view){
        PositionInformation.ChangeListener listener = new PositionInformation.ChangeListener() {
            @Override
            public void onChange(PositionInformation position) {
                // GPS 수신에 성공하면, 그냥 해당 객체가 가진 데이터바인딩에 신호를 보낸다.
                userInputData.alertPositionChange();
                userInputData.setPosition(position);
            }
        };
        // GPS 요청으로 positionInformation을 새로 설정한다.
        PositionInformation positionInformation = new PositionInformation(view.getContext(), listener);
    }

    private SelectLimitTimeDialog selectLimitTimeDialog;
    public void setSelectLimitTimeDialog(FragmentActivity activity) {
        this.selectLimitTimeDialog = new SelectLimitTimeDialog(activity, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = SelectLimitTimeDialog.indexToResult(i);
                userInputData.setLimitTimeMin(result);

                dialogInterface.dismiss();
            }
        });
    }
    public void onClickSetTimeLimit(View view){
        selectLimitTimeDialog.show("setLimitTime");
    }
}
