package com.centanet.hk.aplus.Views.MineView.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.centanet.hk.aplus.bean.http.FeedBackDescription;
import com.centanet.hk.aplus.bean.mine.FeedBack;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class FeedBackActivity extends AppCompatActivity {


    private TitleBar titleBar;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        setContentView(R.layout.activity_feedback);
        editText = findViewById(R.id.feedback_edit_record);
        titleBar = findViewById(R.id.feedback_titlebar);
        titleBar.setTitleContent(getString(R.string.fragment_mine_question));

        titleBar.setOnItemClickListener(new TitleBar.OnItemClickListener() {
            @Override
            public void onClick(View v, final int type) {
                if (type == TitleBar.TYPE_BACK)
                    finish();
                if (type == TitleBar.TYPE_PUT) {
                    //todo 傳參類型
                    SimpleTipsDialog dialog = new SimpleTipsDialog();
                    final String feedStr = editText.getText().toString();
                    if (feedStr.length() > 0) {
                        dialog.setContentString(getString(R.string.dialog_tips_put));
                    } else {
                        dialog.setContentString("内容不能爲空");
                        dialog.setLeftBtnVisibility(false);
                    }

                    dialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                        @Override
                        public void onClick(DialogFragment v, int dialogType) {
                            v.dismiss();

                            switch (dialogType) {
                                case TitleBar.TYPE_BACK:
                                    if (feedStr.length() <= 0) return;
                                    if (dialogType == SimpleTipsDialog.DIALOG_YES)

                                        putFollowData();
                                    break;
                            }
                        }

                        private void putFollowData() {
                            FeedBackDescription description = new FeedBackDescription();
                            description.setContent(editText.getText().toString());
                            L.d("SSoHeaer", ApplicationManager.getApplication().getSsoHeaderDescription().toString());
                            HttpUtil.doPost(HttpUtil.URL_SSO_FEEDBACK, description, ApplicationManager.getApplication().getSsoHeaderDescription(), new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    L.d("FeedBack_put", e.toString());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String net = response.body().string().toString();
                                    L.d("FeedBack_put", net);
                                    if (response.code() == 200) {
                                        FeedBack feedBack = null;
                                        try {
                                            feedBack = GsonUtil.parseJson(net, FeedBack.class);
                                            if (feedBack != null) {
                                                SimpleTipsDialog dialog = new SimpleTipsDialog();
                                                if (feedBack != null && feedBack.getRCode().equals("200")) {
                                                    dialog.setContentString("提交成功");
                                                    FeedBackActivity.this.finish();
                                                } else dialog.setContentString("提交失敗");
                                                dialog.setLeftBtnVisibility(false);
                                                dialog.show(getSupportFragmentManager(), "");
                                            }
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InstantiationException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "");
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 給所有組建分發事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
