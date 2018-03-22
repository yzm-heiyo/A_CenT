package com.centanet.hk.aplus.Views.MineView.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.MineView.present.FeedBackPresent;
import com.centanet.hk.aplus.Views.MineView.present.IFeedBackPresent;
import com.centanet.hk.aplus.Views.MineView.view.AboutActivity;
import com.centanet.hk.aplus.Views.MineView.view.FeedBackActivity;
import com.centanet.hk.aplus.Views.MineView.view.IMineView;
import com.centanet.hk.aplus.Views.MineView.view.UseClauseActivity;
import com.centanet.hk.aplus.entity.mine.Infomation;
import com.centanet.hk.aplus.manager.ApplicationManager;

import retrofit2.http.HTTP;

import static com.centanet.hk.aplus.common.CommandField.DialogType.LOGOUT;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public class MineFragment extends Fragment implements IMineView, View.OnClickListener {

    private View view;
    private View question, logout;
    private View about, clause;
    private IFeedBackPresent present;
    private TextView name, fullname, position, departname, number;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mine, null);
        question = view.findViewById(R.id.fragment_mine_question);
        question.setOnClickListener(this);
        logout = view.findViewById(R.id.fragment_mine_logout);
        logout.setOnClickListener(this);
        about = view.findViewById(R.id.mine_txt_about);
        about.setOnClickListener(this);
        clause = view.findViewById(R.id.mine_txt_clause);
        clause.setOnClickListener(this);
        name = view.findViewById(R.id.mine_txt_name);
        fullname = view.findViewById(R.id.mine_txt_fullname);
        number = view.findViewById(R.id.mine_txt_number);
        position = view.findViewById(R.id.mine_txt_position);
        departname = view.findViewById(R.id.mine_txt_departname);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            present = new FeedBackPresent(this);
            present.doPose(HttpUtil.URL_USERINFO, ApplicationManager.getApplication().getHeaderDescription(), "");
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_mine_question:
                startActivity(new Intent(getContext(), FeedBackActivity.class));
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
            case R.id.mine_txt_about:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.mine_txt_clause:
                startActivity(new Intent(getContext(), UseClauseActivity.class));
            default:
                break;
        }
    }

    @Override
    public void refreshView(final Infomation infomation) {
        L.d("InfoMation","!!!");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setText(infomation.getEmployeeName());
                number.setText(infomation.getEmployeeNo());
                departname.setText(infomation.getDepartmentName());
                position.setText(infomation.getPosition());
            }
        });
    }
}
