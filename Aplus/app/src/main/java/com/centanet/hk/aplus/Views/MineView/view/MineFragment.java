package com.centanet.hk.aplus.Views.MineView.view;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.Dialog.LogoutDialog;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.MineView.present.FeedBackPresent;
import com.centanet.hk.aplus.Views.MineView.present.IFeedBackPresent;
import com.centanet.hk.aplus.bean.mine.Infomation;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.Views.Dialog.LogoutDialog.DIALOG_YES;
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
    private ImageView icoView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), Color.parseColor("#BB2E2D"), false);
    }

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
        icoView = view.findViewById(R.id.mine_img_ico);
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
                LogoutDialog logoutDialog = new LogoutDialog();
                logoutDialog.setOnDialogOnclikeLisenter(new LogoutDialog.OnDialogOnclikeLisenter() {
                    @Override
                    public void onClick(Dialog v, int clickID) {
                        v.dismiss();
                        if (clickID == DIALOG_YES) {
                            MyApplication.removeAllActiies();
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    }
                });
                logoutDialog.show(getActivity().getFragmentManager(), "");
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
        if(getActivity()==null)return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setText(infomation.getEmployeeName());
                number.setText(infomation.getLicense());
                fullname.setText(infomation.getFullName());
                departname.setText(infomation.getDepartmentName());
                position.setText(infomation.getPosition());
                if (infomation.getPhotoPath() != null && !infomation.getPhotoPath().equals(""))
                    Glide.with(getActivity()).load(infomation.getPhotoPath()).into(icoView);
            }
        });
    }
}
