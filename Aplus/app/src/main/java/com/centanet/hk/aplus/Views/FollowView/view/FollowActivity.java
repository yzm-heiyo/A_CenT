package com.centanet.hk.aplus.Views.FollowView.view;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
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
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.MyApplication.getContext;

/**
 * Created by yangzm4 on 2018/7/10.
 */

public class FollowActivity extends AppCompatActivity implements IFollowView, View.OnClickListener {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    private TextView propertyNameChTxt, propertyNameEnTxt;
    private TextView startDateTxt, endDateTxt, confirmTxt;
    private ClearEditText keyWordEdit;
    private View addressView;
    private SmartRefreshLayout refreshLayout;
    private ListView listView;
    private FollowAdapter adapter;
    private List<PropertyFollow> propertyFollows;
    private TextView title;
    private String keyId;
    private ImageView statuImg;
    private ImageView favoImg, cancelImg;
    //    private boolean isFollowEnd;
    private IFollowPresenter presenter;
    private AHeaderDescription aHeaderDescription;
    private FollowDescription followDescription;
    private String thiz = getClass().getSimpleName();
    private ScreenShotListenManager screenShotListenManager;
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
        startScreenListen();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        screenShotListenManager.stopListen();
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
        cancelImg.setOnClickListener(this);

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
                confirmTxt.setVisibility(View.VISIBLE);
                confirmTxt.setOnClickListener(this);
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
        confirmTxt = findViewById(R.id.follow_txt_confirm);
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
        cancelImg = findViewById(R.id.follow_img_cancel);
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
//        presenter.doFollowRequest(HttpUtil.URL_FOLLOWS, aHeaderDescription, followDescription);
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
                if (v instanceof EditText) {
                    v.clearFocus();
                }
                return true;
            }
        }
        return false;
    }

    //关闭搜索Txt
    public boolean isShouldHideSearchTxt(View v, MotionEvent event) {
        if (v != null && (v instanceof TextView) && v.getId() == R.id.follow_txt_confirm) {
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

            if (!favo)
                DialogUtil.getSimpleDialog("已取消收藏").show(getSupportFragmentManager(), "");
            else
                DialogUtil.getSimpleDialog(" 成功加入收藏").show(getSupportFragmentManager(), "");

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

    private void showFavoriteDialog(boolean favo) {

        View view = View.inflate(getContext(), R.layout.item_dialog_double, null);

        SimpleTipsDialog dialog = new SimpleTipsDialog(view, 0.72, 0.21, R.id.dialog_content_txt);
        if (!favo) {
            dialog.setTipString("加入收藏");
            dialog.setRightButtonText(getString(R.string.add));
        } else
            dialog.setRightButtonText(getString(R.string.remove));

        dialog.setOnItemclickListener((dialog1, type) -> {
            if (type == SimpleTipsDialog.DIALOG_YES) {
                FavoriteDescription description = new FavoriteDescription();
                description.setKeyId(keyId);
                if (favoImg.isSelected())
                    presenter.doCancelFavoRequest(HttpUtil.URL_CANCELFAVO, aHeaderDescription, description);
                else
                    presenter.doFavoRequest(HttpUtil.URL_FAVORITE, aHeaderDescription, description);
            }
        });
        //todo dsdadasd

        if (!houseData.isUserIsShowDetailFloor()) {
            if (!houseData.getUserIsShowAddressDetail()) {
                dialog.setContentString(houseData.getDetailAddressChNoFoolrInfo());
            } else {
                dialog.setContentString(houseData.getDetailAddressChInfo());
            }
        } else {
            dialog.setContentString(houseData.getDetailAddressChInfo());
        }
        dialog.show(getSupportFragmentManager(), "");
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
                showFavoriteDialog(favoImg.isSelected());
//                FavoriteDescription description = new FavoriteDescription();
//                description.setKeyId(keyId);
//                if (favoImg.isSelected())
//                    presenter.doCancelFavoRequest(HttpUtil.URL_CANCELFAVO, aHeaderDescription, description);
//                else
//                    presenter.doFavoRequest(HttpUtil.URL_FAVORITE, aHeaderDescription, description);
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

            case R.id.follow_txt_confirm:
                followDescription.setKeyword(keyWordEdit.getText().toString());
                refreshLayout.autoRefresh();
                confirmTxt.setOnClickListener(null);
                confirmTxt.setVisibility(View.GONE);
                cancelImg.setVisibility(View.VISIBLE);
                break;

            case R.id.follow_txt_start_date:
            case R.id.follow_txt_end_date:

                CalendarDialog calendarDialog = new CalendarDialog();
                calendarDialog.setOnDialogOnclikeLisenter((dialog, view, start, end) -> {
                    dialog.dismiss();
                    followDescription.setPageIndex(1);
                    if (start != null) {
                        startDateTxt.setText(getShowDate(start));
                        followDescription.setFollowTimeFrom(getTime(start));
                    } else {
                        followDescription.setFollowTimeFrom(null);
                        startDateTxt.setText(getString(R.string.start_date));
                    }
                    if (end != null) {
                        endDateTxt.setText(getShowDate(end));
                        followDescription.setFollowTimeTo(getTime(end));
                    } else {
                        followDescription.setFollowTimeTo(null);
                        endDateTxt.setText(getString(R.string.end_state));
                    }
                    isShowCancelTxt(followDescription);
                    refreshLayout.autoRefresh();
                });
                Calendar startCanlen = Calendar.getInstance();
                Calendar endCanlen = Calendar.getInstance();
                if (!TextUtil.isEmply(followDescription.getFollowTimeFrom())) {
                    String[] year = followDescription.getFollowTimeFrom().split("-");
                    L.d("getFollowTimeFrom", year[0] + " " + year[1] + " " + year[2]);
                    startCanlen.set(Calendar.YEAR, Integer.parseInt(year[0]));
                    startCanlen.set(Calendar.MONTH, Integer.parseInt(year[1]) - 1);
                    startCanlen.set(Calendar.DAY_OF_MONTH, Integer.parseInt(year[2]));
                    calendarDialog.setStartCl(startCanlen);
                }

                if (!TextUtil.isEmply(followDescription.getFollowTimeTo())) {
                    String[] year = followDescription.getFollowTimeTo().split("-");
                    L.d("getFollowTimeTo", year[0] + " " + year[1] + " " + year[2]);
                    endCanlen.set(Calendar.YEAR, Integer.parseInt(year[0]));
                    endCanlen.set(Calendar.MONTH, Integer.parseInt(year[1]) - 1);
                    endCanlen.set(Calendar.DAY_OF_MONTH, Integer.parseInt(year[2]));
                    calendarDialog.setEndCl(endCanlen);
                }

                calendarDialog.show(getFragmentManager(), "");
                break;
            case R.id.follow_img_cancel:
                cancelImg.setVisibility(View.GONE);
                followDescription.setKeyword(null);
                followDescription.setFollowTimeFrom(null);
                followDescription.setFollowTimeTo(null);
                keyWordEdit.setText(null);
                startDateTxt.setText(getString(R.string.start_date));
                endDateTxt.setText(getString(R.string.end_state));
                refreshLayout.autoRefresh();
                break;
        }
    }

    private void isShowCancelTxt(FollowDescription followDescription) {
        if (followDescription.getFollowTimeTo() != null || followDescription.getFollowTimeFrom() != null) {
            cancelImg.setVisibility(View.VISIBLE);
        }
    }

    private String getShowDate(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
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

    private void startScreenListen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        screenShotListenManager = ScreenShotListenManager.newInstance(this);
        screenShotListenManager.setListener((imagePath) -> onScreenShot());
        screenShotListenManager.startListen();
    }

    private void onScreenShot() {
//        if (!TimeLimitUtil.isAchieveLimitTime(1000)) return;
        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        userBehaviorDescription.setPage(2);
        userBehaviorDescription.setExtras("{" + "\"PropertyKeyId\"" + ":" + "[" + "\"" + keyId + "\"" + "]}");
        L.d(thiz, "\"HouseShot\"" + "{" + "PropertyKeyId" + ":" + keyId + "}");
//        presenter.doAddRequest(HttpUtil.URL_USER_BEHAVIOR, headerDescription, userBehaviorDescription);
        HttpUtil.doPost(HttpUtil.URL_USER_BEHAVIOR, aHeaderDescription, userBehaviorDescription, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        //todo other Api
//        DetailListsDescription description = new DetailListsDescription();
//        description.setKeyId(keyId);
//        present.doGet(HttpUtil.URL_DETAILS_LIST, ((MyApplication) getContext().getApplicationContext()).getHeaderDescription(), description);
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
                viewHolder.timeTxt.setText(datas.get(position).getFollowTime().replace("T", " "));
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
