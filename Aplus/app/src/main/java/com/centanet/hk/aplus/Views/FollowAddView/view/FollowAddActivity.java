package com.centanet.hk.aplus.Views.FollowAddView.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.voice.VoiceInputPanel;
import com.centanet.hk.aplus.Views.FollowAddView.present.FollowAddPresent;
import com.centanet.hk.aplus.Views.FollowAddView.present.IFollowAddPresent;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.http.FollowAddDescription;
import com.centanet.hk.aplus.bean.http.PropertyAddDescription;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_FOLLOW_ADD;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW_SUCCESS;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public class FollowAddActivity extends BasicActivty implements View.OnClickListener, IFollowAddView, VoiceInputPanel.EventListener {

    private String thiz = getClass().getSimpleName();
    private TitleBar titleBar;
    private TextView noListenerTxt, voicemailMsgText, cutlineText, wrongNumTxt, lineBusyTxt;
    private EditText recordEdit;
    private ImageView icoStatu;
    private TextView houseChNameTxt, houseEnNameTxt, addressTxt;
    private String labelContentString;
    private IFollowAddPresent present;
    private String keyId;
    private FollowAddDescription description;
    private AHeaderDescription headerDescription;
    private View addressView, mic;
    private DetailHouse houseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followadd);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        initViews();
        EventBus.getDefault().register(this);
    }

    private void initViews() {
        Intent intent = getIntent();
        houseData = (DetailHouse) intent.getSerializableExtra("DetailData");

        mic = findViewById(R.id.follow_img_mic);
        mic.setOnClickListener(this);
        addressView = findViewById(R.id.feedback_view_address);
        titleBar = this.findViewById(R.id.activity_feedback_titlebar);
        titleBar.setOnClickListener(this);
        titleBar.setTitleContent(houseData.getPropertyNo());
        noListenerTxt = findViewById(R.id.feedback_text_no_listen);
        noListenerTxt.setOnClickListener(this);
        voicemailMsgText = findViewById(R.id.feedback_text_voicemailmessage);
        voicemailMsgText.setOnClickListener(this);
        cutlineText = findViewById(R.id.feedback_text_cutline);
        cutlineText.setOnClickListener(this);
        wrongNumTxt = findViewById(R.id.feedback_text_wrong_numb);
        wrongNumTxt.setOnClickListener(this);
        lineBusyTxt = findViewById(R.id.feedback_text_line_busy);
        lineBusyTxt.setOnClickListener(this);

        houseChNameTxt = findViewById(R.id.feedback_txt_ch_housename);
        houseEnNameTxt = findViewById(R.id.feedback_txt_en_housename);

        addressTxt = findViewById(R.id.feedback_txt_address);
        addressTxt.setOnClickListener(this);

        icoStatu = findViewById(R.id.feedback_icon_statu);

        if (!houseData.isUserIsShowDetailFloor()) {
            if (!houseData.getUserIsShowAddressDetail()) {
                addressView.setVisibility(View.VISIBLE);
                houseChNameTxt.setText(houseData.getDetailAddressChNoFoolrInfo());
                houseEnNameTxt.setText(houseData.getDetailAddressEnNoFoolrInfo());
            } else {
                houseChNameTxt.setText(houseData.getDetailAddressChInfo());
                houseEnNameTxt.setText(houseData.getDetailAddressEnInfo());
                addressView.setVisibility(View.GONE);
            }
        } else {
            addressView.setVisibility(View.GONE);
            houseChNameTxt.setText(houseData.getDetailAddressChInfo());
            houseEnNameTxt.setText(houseData.getDetailAddressEnInfo());
        }

        titleBar.setTitleContent(getString(R.string.house_umber) + ":" + houseData.getPropertyNo());
        setIconViewLevel(0, houseData.getPropertyStatus());


        recordEdit = findViewById(R.id.feedback_edit_record);
        recordEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!TimeLimitUtil.isAchieveLimitTime(100)) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    return deleteLabelString(v);
                }
                return false;
            }
        });

        present = new FollowAddPresent(this);
        description = new FollowAddDescription();
        keyId = intent.getStringExtra("keyId");
        description.setPropertyKeyId(keyId);
        headerDescription = ((MyApplication) getApplicationContext()).getHeaderDescription();
        setListeners();
    }


    private void setIconViewLevel(int level, String properties) {
        switch (properties.substring(0, 1)) {
            case "N":
                level = 1;
                break;
            case "P":
                level = 2;
                break;
            case "S":
                level = 6;
                break;
            case "T":
                level = 4;
                break;
            case "G":
                level = 5;
                break;
            default:
                level = 3;
                break;
        }
        icoStatu.setImageResource(R.drawable.level_list_propertystatus);
        icoStatu.setImageLevel(level);
    }

    private boolean deleteLabelString(View v) {
        int position = ((EditText) v).getSelectionStart() - 1;
        String oldString = ((EditText) v).getText().toString();
        String newString = "";
        boolean isMargin = oldString.lastIndexOf("【", position) < oldString.lastIndexOf("】", position);
        boolean isMiddle = oldString.lastIndexOf("【", position) > oldString.lastIndexOf("】", position);
        int startIndex = 0;

        //説明刪除的是文字不是標簽
        if (oldString.lastIndexOf("】") < position - 1) {
            return false;
        }
        //説明刪除的標簽之間的文字
        if (isMargin && oldString.lastIndexOf("】", position) != position) {
            return false;
        }
        //當光標處於第一位的時候
        if (position == -1) {
            return false;
        }

        startIndex = oldString.lastIndexOf("【", position);

        //判斷是在標簽文字中間還是在標簽兩邊
        if (isMargin) {
            if (oldString.length() >= position) {
                newString = oldString.substring(0, startIndex) + oldString.substring(position + 1);
            } else {
                newString = oldString.substring(0, startIndex);
            }
        } else if (isMiddle) {
            int endIndex = oldString.indexOf("】", position);
            newString = oldString.substring(0, startIndex) + oldString.substring(endIndex + 1);
        }
        ((EditText) v).setText(newString);
        ((EditText) v).setSelection(newString.length());
        return true;
    }


    private void setListeners() {
        titleBar.setOnItemClickListener(new TitleBar.OnItemClickListener() {

            @Override
            public void onClick(View v, final int type) {

                SimpleTipsDialog dialog = new SimpleTipsDialog();
                final String follow = recordEdit.getText().toString();
                if (type == TitleBar.TYPE_BACK) {
                    dialog.setContentString(getString(R.string.dialog_tips_back));
                } else if (type == TitleBar.TYPE_PUT) {
                    if (follow.length() > 0) {
                        dialog.setContentString(getString(R.string.dialog_tips_put));
                    } else {
                        dialog.setContentString(getString(R.string.feedback_null));
                        dialog.setLeftBtnVisibility(false);
                    }
                }

                dialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                    @Override
                    public void onClick(DialogFragment v, int dialogType) {
                        v.dismiss();
                        switch (type) {
                            case TitleBar.TYPE_BACK:
                                if (dialogType == SimpleTipsDialog.DIALOG_YES) {
                                    finish();
                                }
                                break;
                            case TitleBar.TYPE_PUT:
                                if (follow.length() <= 0) return;
                                if (dialogType == SimpleTipsDialog.DIALOG_YES)
                                    putFollowData();
                                break;
                        }
                    }
                });
                dialog.show(getSupportFragmentManager(), "");
            }
        });
    }

    /**
     * 上傳客戶跟進反饋
     */
    private void putFollowData() {
        String follow = recordEdit.getText().toString();
//        if (follow != "") {
        description.setFollowContent(follow);
        present.doPost(URL_FOLLOW_ADD, headerDescription, description);
        FollowAddActivity.this.setResult(3);
    }


    private void addContentLabel(String labelString) {
        String contentString = recordEdit.getText().toString();
        int lastIndex = contentString.lastIndexOf("】");
        if (lastIndex != -1) {
            contentString = contentString.substring(0, lastIndex + 1) + "【" + labelString + "】" + contentString.substring(lastIndex + 1);
        } else {
            contentString = "【" + labelString + "】" + contentString.trim();
        }
        recordEdit.setText(contentString);
        recordEdit.setSelection(contentString.length());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_text_cutline:
                addContentLabel(getResources().getString(R.string.cutline));
                break;
            case R.id.feedback_text_line_busy:
                addContentLabel(getResources().getString(R.string.line_busy));
                break;
            case R.id.feedback_text_no_listen:
                addContentLabel(getResources().getString(R.string.nolistener));
                break;
            case R.id.feedback_text_voicemailmessage:
                addContentLabel(getResources().getString(R.string.voicemailmessage));
                break;
            case R.id.feedback_text_wrong_numb:
                addContentLabel(getResources().getString(R.string.wrongnumb));
                break;
            case R.id.feedback_txt_address:
                PropertyAddDescription description = new PropertyAddDescription();
                description.setKeyId(keyId);
                present.doPost(HttpUtil.URL_ADDRESS_DETAIL, headerDescription, description);

                break;
            case R.id.follow_img_mic:
                showVoiceInputPanel();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        switch (messageEvent.getMsg()) {
            case DETAIL_FOLLOW_SUCCESS:
                finish();
                break;
        }
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

    public void showVoiceInputPanel() {
        if (Build.VERSION.SDK_INT >= 23)
            if (ContextCompat.checkSelfPermission(FollowAddActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        1);
            }
        VoiceInputPanel.show(this, false, this);
    }


    @Override
    public void reFreshAddress(final DetailAddressResponse address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                L.d(thiz, address.toString());
                if (address.getErrorMsg() != null && !address.getErrorMsg().equals("")) {
                    showPermissionTipDialog(address.getErrorMsg());
                    return;
                }
                addressView.setVisibility(View.GONE);
                houseChNameTxt.setText(address.getDetailAddressChInfo());
                houseEnNameTxt.setText(address.getDetailAddressEnInfo());
                houseData.setDetailAddressChInfo(address.getDetailAddressChInfo());
                houseData.setDetailAddressEnInfo(address.getDetailAddressEnInfo());
                houseData.setUserIsShowDetailFloor(true);

                Intent dbIntent = new Intent();
                dbIntent.putExtra("FollowBackData", houseData);
                FollowAddActivity.this.setResult(2, dbIntent);

            }
        });
    }

    private void showPermissionTipDialog(String per) {
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setContentString(per);
        simpleTipsDialog.setLeftBtnVisibility(false);
        simpleTipsDialog.show(getSupportFragmentManager(), "");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onConfirmClick(String msg) {
        recordEdit.setText(recordEdit.getText() + msg);
        recordEdit.setSelection(recordEdit.getText().toString().length());
    }

    @Override
    public void onCancel() {

    }
}
