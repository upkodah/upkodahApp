package com.upcodot.uos.user.input.test;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.upcodot.uos.UcdMainActivity;
import com.upcodot.uos.user.input.UserSearchAction;
import com.upcodot.uos.user.input.UserSearchInput;

public class TempUserSearchAction implements UserSearchAction {
    private Context context;

    public TempUserSearchAction(Context context){
        this.context = context;
    }

    @Override
    public void action(UserSearchInput input) {
        Toast.makeText(context, input.getPositionInformation().getPostalAddress()
                        +input.getPositionInformation().getLongitude()+"\n"
                        +input.getPositionInformation().getLatitude()+"\n"
                        +input.getKeyword()+"\n"
                        +input.getLimitTimeMin()+"\n"
                , Toast.LENGTH_LONG).show();
    }
}
