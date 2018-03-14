package com.centanet.hk.aplus.Views.abst;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.FeedBackView.view.FeedbackActivity;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;

import static com.centanet.hk.aplus.common.CommandField.DialogType.LOGOUT;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public abstract class MineFragmentAbst extends Fragment implements View.OnClickListener {

    private View view;
    private View question, logout;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_mine, null);
        question = view.findViewById(R.id.fragment_mine_question);
        question.setOnClickListener(this);
        logout = view.findViewById(R.id.fragment_mine_logout);
        logout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_mine_question:
                startActivity(new Intent(getContext(), FeedbackActivity.class));
                break;

            case R.id.fragment_mine_logout:
                DialogFragment dialog = DialogFactory.newInstance(LOGOUT, new DialogFactory.IGetClickItem() {
                    @Override
                    public void getClickItem(DialogFragment dialog, String... items) {
                        dialog.dismiss();
                        MyApplication.removeAllActiies();
                        startActivity(new Intent(getContext(), LoginActivity.class));

                    }
                });
                dialog.show(getFragmentManager(), "");
                break;
            default:
                break;
        }
    }
}
