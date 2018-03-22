package com.centanet.hk.aplus.Views.FollowAddView.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.centanet.hk.aplus.Views.FollowAddView.present.FollowAddPresent;
import com.centanet.hk.aplus.Views.FollowAddView.present.IFollowAddPresent;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.centanet.hk.aplus.entity.detail.DetailHouse;
import com.centanet.hk.aplus.entity.http.FollowAddDescription;
import com.centanet.hk.aplus.entity.http.DetailsDescription;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_FOLLOW_ADD;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public class FollowAddActivity extends BasicActivty implements View.OnClickListener, IFollowAddView {

    private String thiz = getClass().getSimpleName();
    private TitleBar titleBar;
    private TextView noListenerTxt, voicemailMsgText, cutlineText, wrongNumTxt, lineBusyTxt;
    private EditText recordEdit;
    private ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo, icoStatu;
    private TextView clineNameTxt, houseChNameTxt, houseEnNameTxt, ssdTxt, addressTxt;
    private String labelContentString;
    private IFollowAddPresent present;
    private String keyId;
    private FollowAddDescription description;
    private AHeaderDescription headerDescription;
    private View addressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followadd);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        DetailHouse houseData = (DetailHouse) intent.getSerializableExtra("DetailData");

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
        clineNameTxt = findViewById(R.id.feedback_txt_client);
        houseChNameTxt = findViewById(R.id.feedback_txt_ch_housename);
        houseEnNameTxt = findViewById(R.id.feedback_txt_en_housename);
        ssdTxt = findViewById(R.id.item_icon_ssd);
        addressTxt = findViewById(R.id.feedback_txt_address);
        addressTxt.setOnClickListener(this);

        icoStatu = findViewById(R.id.feedback_icon_statu);
        iconHot = findViewById(R.id.item_icon_hot);
        iconKey = findViewById(R.id.item_icon_key);
        iconO = findViewById(R.id.item_icon_o);
        iconL = findViewById(R.id.item_icon_l);
        iconD = findViewById(R.id.item_icon_d);
        iconSingle = findViewById(R.id.item_icon_medal);
        iconFavo = findViewById(R.id.item_icon_favo);

        if(houseData.getUserIsShowAddressDetail())
            addressTxt.setVisibility(View.VISIBLE);
        iconSingle.setSelected(houseData.isHasOnlyTrust());
        iconFavo.setSelected(houseData.isFavorite());
        iconO.setSelected(houseData.isODish());
        iconKey.setImageLevel(houseData.getPropertyKeyType());
        iconHot.setSelected(houseData.getHotList() == null ? false : true);
        iconL.setSelected(houseData.isConfirmed());
        iconD.setSelected(houseData.getDevelopmentEndCredits() == null ? false : true);
        clineNameTxt.setText(houseData.getPropertyBuildingOwner() == "" ? getString(R.string.detail_no_owner) : houseData.getPropertyBuildingOwner());
        houseChNameTxt.setText(houseData.getDetailAddressChNoFoolrInfo());
        houseEnNameTxt.setText(houseData.getDetailAddressEnNoFoolrInfo());
        titleBar.setTitleContent(getString(R.string.house_umber) + ":" + houseData.getPropertyNo());
        setIconViewLevel(0, houseData.getPropertyStatus());
        if (houseData.getSSDType() != 0) {
            ssdTxt.setVisibility(View.VISIBLE);
            int per = 5 * houseData.getSSDType();
            if (houseData.getSSDType() == 1) per = 0;
            ssdTxt.setText(per + "%");
        }

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
        headerDescription = ((MyApplication)getApplicationContext()).getHeaderDescription();
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
                L.d(thiz, "A");
            } else {
                newString = oldString.substring(0, startIndex);
                L.d(thiz, "S");
            }
        } else if (isMiddle) {
            int endIndex = oldString.indexOf("】", position);
            newString = oldString.substring(0, startIndex) + oldString.substring(endIndex + 1);
            L.d(thiz, "中間");
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
                L.d(thiz,follow);
                    if (type == TitleBar.TYPE_BACK) {
                        dialog.setContentString(getString(R.string.dialog_tips_back));
                    } else if (type == TitleBar.TYPE_PUT) {
                        if (follow.length()>0) {
                            dialog.setContentString(getString(R.string.dialog_tips_put));
                        }else {
                            dialog.setContentString("跟進内容不能爲空");
                            dialog.setLeftBtnVisibility(false);
                        }
                    }

                dialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                    @Override
                    public void onClick(DialogFragment v, int dialogType) {
                        v.dismiss();
                        switch (type) {
                            case TitleBar.TYPE_BACK:
                                if (dialogType == SimpleTipsDialog.DIALOG_YES)
                                    finish();
                                break;
                            case TitleBar.TYPE_PUT:
                                if(follow.length()<=0)return;
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
        finish();
    }


    private void addContentLabel(String labelString) {
        L.d(getClass().getSimpleName(), labelString);
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
                addressView.setVisibility(View.GONE);
                DetailsDescription description = new DetailsDescription();
                description.setKeyId(keyId);
                present.doPost(HttpUtil.URL_ADDRESS_DETAIL, headerDescription, description);
                break;
            default:
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

    @Override
    public void reFreshAddress(final String address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                houseChNameTxt.setText(address);
            }
        });
    }
}
