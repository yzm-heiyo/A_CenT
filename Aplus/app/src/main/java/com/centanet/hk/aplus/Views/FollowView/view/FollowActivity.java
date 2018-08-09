package com.centanet.hk.aplus.Views.FollowView.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.CalendarDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FollowAddView.view.FollowAddActivity;
import com.centanet.hk.aplus.Views.FollowView.presenter.FollowPresenter;
import com.centanet.hk.aplus.Views.FollowView.presenter.IFollowPresenter;
import com.centanet.hk.aplus.Views.basic.BaseListAdapter;
import com.centanet.hk.aplus.Widgets.ClearEditText;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.PropertyAddDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.FollowDescription;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/10.
 */

public class FollowActivity extends AppCompatActivity implements IFollowView, View.OnClickListener {

    private TextView propertyNameChTxt, propertyNameEnTxt;
    private TextView startDateTxt, endDateTxt, cancelTxt;
    private ClearEditText keyWordEdit;
    private View addressView;
    private SmartRefreshLayout refreshLayout;
    private ListView listView;
    private FollowAdapter adapter;
    private List<PropertyFollow> propertyFollows;
    private TextView title;
    private String keyId;
    private ImageView statuImg;
    private ImageView favoImg;
    //    private boolean isFollowEnd;
    private IFollowPresenter presenter;
    private AHeaderDescription aHeaderDescription;
    private FollowDescription followDescription;
    private String thiz = getClass().getSimpleName();
    private ImageView addImg, backImg;
    private DetailHouse houseData;
    private View dateView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_follow);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        initView();
        init();
        initLisenter();
    }

    private void initLisenter() {

        startDateTxt.setOnClickListener(this);
        endDateTxt.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(refreshlayout -> {
//            isFollowEnd = false;
            followDescription.setPageIndex(1);
            refreshLayout.setEnableLoadmore(true);
            presenter.doFollowRequest(HttpUtil.URL_FOLLOWS, aHeaderDescription, followDescription);
        });

        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
//            if (isFollowEnd) return;
            followDescription.setPageIndex(followDescription.getPageIndex() + 1);
            presenter.doFollowRequest(HttpUtil.URL_FOLLOWS, aHeaderDescription, followDescription);
        });

        addImg.setOnClickListener(this);
        favoImg.setOnClickListener(this);
        backImg.setOnClickListener(this);
        addressView.setOnClickListener(this);

//        keyWordEdit.setOnEditorActionListener((v, actionId, event) -> {
//            L.d("Edit", "ActionId: " + actionId + " Sys:" + EditorInfo.IME_ACTION_SEND);
//            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                followDescription.setKeyword(keyWordEdit.getText().toString());
//                refreshLayout.autoRefresh();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                keyWordEdit.clearFocus();
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//                cancelTxt.setVisibility(View.GONE);
//                cancelTxt.setOnClickListener(null);
//                return true;
//            }
//            return false;
//        });

        keyWordEdit.setOnItemClickLisenter(hasFous -> {
            if (hasFous) {
                cancelTxt.setVisibility(View.VISIBLE);
                cancelTxt.setOnClickListener(this);
                ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
                animator.addUpdateListener(animation -> {
                    dateView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (Float) animation.getAnimatedValue()));
                });
                animator.setInterpolator(new DecelerateInterpolator());
                animator.setDuration(200);
                animator.start();
            } else {
//                cancelTxt.setOnClickListener(null);
                ValueAnimator animator = ValueAnimator.ofFloat(0, 3);
                animator.addUpdateListener(animation -> {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (Float) animation.getAnimatedValue());
                    params.setMargins(DensityUtil.dip2px(this, 10), 0, 0, 0);
                    dateView.setLayoutParams(params);
                });
                animator.setInterpolator(new DecelerateInterpolator());
                animator.setDuration(200);
                animator.start();
            }
        });
    }

    private void initView() {
        propertyNameChTxt = findViewById(R.id.follow_txt_ch_housename);
        propertyNameEnTxt = findViewById(R.id.follow_txt_en_housename);
        startDateTxt = findViewById(R.id.follow_txt_start_date);
        endDateTxt = findViewById(R.id.follow_txt_end_date);
        cancelTxt = findViewById(R.id.follow_txt_cancel);
        keyWordEdit = findViewById(R.id.follow_edit_keyword);
        addressView = findViewById(R.id.detail_view_address);
        refreshLayout = findViewById(R.id.follow_smartLayout);
        listView = findViewById(R.id.follow_listview);
        title = findViewById(R.id.title_txt_title);
        statuImg = findViewById(R.id.follow_icon_statu);
        addImg = findViewById(R.id.follow_img_add);
        favoImg = findViewById(R.id.title_img_favo);
        backImg = findViewById(R.id.title_backicon);
        dateView = findViewById(R.id.follow_ll_date);
    }

    private void init() {

        propertyFollows = new ArrayList<>();

        adapter = new FollowAdapter(this, propertyFollows, R.layout.item_follow_item);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        houseData = (DetailHouse) intent.getSerializableExtra("DetailData");
        keyId = intent.getStringExtra("keyId");
        L.d(thiz, "keyId" + keyId);
        initViewData(houseData);

        aHeaderDescription = ApplicationManager.getApplication().getHeaderDescription();

        followDescription = new FollowDescription();
        followDescription.setPageIndex(1);
        followDescription.setPropertyKeyId(keyId);

        //初始化跟进数据
        presenter = new FollowPresenter(this);
        presenter.doFollowRequest(HttpUtil.URL_FOLLOWS, aHeaderDescription, followDescription);
    }

    private void initViewData(DetailHouse houseData) {

        title.setText(getString(R.string.house_umber) + ":" + houseData.getPropertyNo());

        if (!houseData.isUserIsShowDetailFloor()) {
            if (!houseData.getUserIsShowAddressDetail()) {
                addressView.setVisibility(View.VISIBLE);
                propertyNameChTxt.setText(houseData.getDetailAddressChNoFoolrInfo());
                propertyNameEnTxt.setText(houseData.getDetailAddressEnNoFoolrInfo());
            } else {
                propertyNameChTxt.setText(houseData.getDetailAddressChInfo());
                propertyNameEnTxt.setText(houseData.getDetailAddressEnInfo());
                addressView.setVisibility(View.GONE);
            }
        } else {
            addressView.setVisibility(View.GONE);
            propertyNameChTxt.setText(houseData.getDetailAddressChInfo());
            propertyNameEnTxt.setText(houseData.getDetailAddressEnInfo());
        }
        favoImg.setSelected(houseData.isFavorite());
        setIconViewLevel(houseData.getPropertyStatus());
    }

    private void setIconViewLevel(String properties) {
        int level;
        if (properties == null) return;
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
        statuImg.setImageResource(R.drawable.level_list_propertystatus);
        statuImg.setImageLevel(level);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                isShouldHideSearchTxt(v, ev);
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

    //关闭键盘
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
                if (v instanceof EditText)
                    v.clearFocus();
                return true;
            }
        }
        return false;
    }

    //关闭搜索Txt
    public boolean isShouldHideSearchTxt(View v, MotionEvent event) {
        if (v != null && (v instanceof TextView) && v.getId() == R.id.follow_txt_cancel) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是SearchTxt 保留位置
                return false;
            }
        } else {
//            cancelTxt.setVisibility(View.INVISIBLE);
//            cancelTxt.setOnClickListener(null);
            return true;
        }
        return false;
    }

    @Override
    public void reFreshFollow(List<PropertyFollow> follows) {
        if (follows == null || follows.isEmpty()) {
            SimpleTipsDialog dialog = DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_permission_follow_no));
            dialog.show(getSupportFragmentManager(), "");
        }
        if (propertyFollows != null && refreshLayout.isRefreshing()) propertyFollows.clear();
        propertyFollows.addAll(follows);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
        closeRefresh();
    }

    private void closeRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void reFreshFavoImg(boolean favo) {
        runOnUiThread(() -> {
            favoImg.setSelected(favo);
            houseData.setFavorite(favo);
        });
    }

    @Override
    public void reFreshAddTxt(DetailAddressResponse address) {
        runOnUiThread(() -> {

            L.d(thiz, address.toString());
            if (address.getErrorMsg() != null && !address.getErrorMsg().equals("")) {
                showPermissionTipDialog(address.getErrorMsg());
                return;
            }

            propertyNameChTxt.setText(address.getDetailAddressChInfo());
            propertyNameEnTxt.setText(address.getDetailAddressEnInfo());
            addressView.setVisibility(View.GONE);
            houseData.setUserIsShowDetailFloor(true);
            houseData.setDetailAddressEnInfo(address.getDetailAddressEnInfo());
            houseData.setDetailAddressChInfo(address.getDetailAddressChInfo());

//            Intent dbIntent = new Intent();
//            dbIntent.putExtra("DetailAddressChInfo", address.getDetailAddressChInfo());
//            dbIntent.putExtra("DetailAddressEnInfo", address.getDetailAddressEnInfo());
//            FollowActivity.this.setResult(2, dbIntent);
        });
    }

    private void showPermissionTipDialog(String per) {
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setContentString(per);
        simpleTipsDialog.setLeftBtnVisibility(false);
        simpleTipsDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void toastFollowsEnd() {
//        isFollowEnd = true;
        refreshLayout.setEnableLoadmore(false);
        Toast.makeText(this, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.follow_img_add:
                Intent intent = new Intent(this, FollowAddActivity.class);
                intent.putExtra("keyId", keyId);
                intent.putExtra("DetailData", houseData);
                startActivityForResult(intent, 0);
                break;
            case R.id.title_img_favo:
                FavoriteDescription description = new FavoriteDescription();
                description.setKeyId(keyId);
                if (favoImg.isSelected())
                    presenter.doCancelFavoRequest(HttpUtil.URL_CANCELFAVO, aHeaderDescription, description);
                else
                    presenter.doFavoRequest(HttpUtil.URL_FAVORITE, aHeaderDescription, description);
                break;
            case R.id.title_backicon:

                Intent dbIntent = new Intent();
                dbIntent.putExtra("FollowBackData", houseData);
                FollowActivity.this.setResult(2, dbIntent);
                FollowActivity.this.finish();

                break;
            case R.id.detail_view_address:
                PropertyAddDescription detailsDescription = new PropertyAddDescription();
                detailsDescription.setKeyId(keyId);
                presenter.doAddRequest(HttpUtil.URL_ADDRESS_DETAIL, aHeaderDescription, detailsDescription);
                break;

            case R.id.follow_txt_cancel:
                followDescription.setKeyword(keyWordEdit.getText().toString());
                refreshLayout.autoRefresh();
                cancelTxt.setOnClickListener(null);
                cancelTxt.setVisibility(View.INVISIBLE);
                break;

            case R.id.follow_txt_start_date:
            case R.id.follow_txt_end_date:

                CalendarDialog calendarDialog = new CalendarDialog();
                calendarDialog.setOnDialogOnclikeLisenter((dialog, view, start, end) -> {
                    dialog.dismiss();
                    followDescription.setPageIndex(1);
                    if (start != null) {
                        startDateTxt.setText(getTime(start));
                        followDescription.setFollowTimeFrom(getTime(start));
                    } else {
                        followDescription.setFollowTimeFrom(null);
                        startDateTxt.setText(getString(R.string.start_date));
                    }
                    if (end != null) {
                        endDateTxt.setText(getTime(end));
                        followDescription.setFollowTimeTo(getTime(end));
                    } else {
                        followDescription.setFollowTimeTo(null);
                        endDateTxt.setText(getString(R.string.end_state));
                    }
                    refreshLayout.autoRefresh();
                });
                calendarDialog.show(getFragmentManager(), "");

                break;
        }
    }

    private String getTime(Calendar calendar) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        isDataSearch = true;
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
            System.gc();
        }
    }

    @Override
    public void onBackPressed() {
        Intent dbIntent = new Intent();
        dbIntent.putExtra("FollowBackData", houseData);
        this.setResult(2, dbIntent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
                addressView.setVisibility(View.GONE);
                houseData.setUserIsShowDetailFloor(true);
                houseData = (DetailHouse) data.getSerializableExtra("DetailData");

                if (houseData.isUserIsShowDetailFloor()) {
                    propertyNameChTxt.setText(houseData.getDetailAddressChInfo());
                    propertyNameEnTxt.setText(houseData.getDetailAddressEnInfo());
                }

                break;
            case 3:
                refreshLayout.autoRefresh();
                break;
        }
    }

    class FollowAdapter extends BaseListAdapter<PropertyFollow> {

        public FollowAdapter(Context context, List<PropertyFollow> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        protected View getItemView(View view, View convertView, int position, List<PropertyFollow> datas) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_follow_item, null, false);
                viewHolder = new ViewHolder();
                viewHolder.timeTxt = view.findViewById(R.id.follow_txt_time);
                viewHolder.personTxt = view.findViewById(R.id.follow_txt_follower);
                viewHolder.contentTxt = view.findViewById(R.id.follow_txt_content);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            L.d(thiz, "Adapter Position: " + position);

            if (datas != null && !datas.isEmpty()) {
                viewHolder.timeTxt.setText(datas.get(position).getFollowTime());
                viewHolder.contentTxt.setText(datas.get(position).getFollowContent());
                viewHolder.personTxt.setText(datas.get(position).getFollower());
            }
            return view;
        }

        private class ViewHolder {
            TextView timeTxt, personTxt, contentTxt;
        }
    }
}
