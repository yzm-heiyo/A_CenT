package com.centanet.hk.aplus.Views.HouseDetailView.view;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FollowAddView.view.FollowAddActivity;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.bean.http.FollowDescription;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.DialogType.REMARK;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.FOLLOW_ADD;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.SEARCH_ALL_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_END;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public class FollowFragment extends Fragment implements View.OnClickListener {

    private String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private BaseAdapter adapter;
    private TextView dateBeginTxt, dateEndTxt, defaultTxt;
    private ImageView addRemark_img, searchRemark_img, clearDataImg;
    private LineBreakLayout searchLabelLayout;
    private View followLableView;
    private SmartRefreshLayout refreshView;
    private OnUpdateListener onUpdateListener;
    private List<PropertyFollow> follows;
    private FollowDescription description = new FollowDescription();
    private AHeaderDescription headerDescription;
    private boolean ableToSearchFollow = true;
    private boolean ableToAddFollow = false;
    private boolean isEnd = false;
    private boolean isFirst = true;
    private boolean isVisible = false;
    private int refreshType = 0;
    private boolean isDataSearch = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpdateListener) {
            onUpdateListener = (OnUpdateListener) context;
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_follow, null);
        initViews();
        initDatas();
        return view;
    }


    private void initViews() {
        lv = view.findViewById(R.id.fragment_follow_listview);
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.list_footview, null, false);
        footView.setClickable(false);
        lv.addFooterView(footView, null, false);
        refreshView = view.findViewById(R.id.follow_smartLayout);
        dateBeginTxt = view.findViewById(R.id.fragment_follow_date_begin);
        dateEndTxt = view.findViewById(R.id.fragment_follow_date_end);
        defaultTxt = view.findViewById(R.id.fragment_follow_txt_default);

        addRemark_img = view.findViewById(R.id.img_add_remark);
        searchRemark_img = view.findViewById(R.id.img_search_remark);

        searchLabelLayout = view.findViewById(R.id.follow_label_group);
        int Resources = R.layout.item_label_remark;
        searchLabelLayout.setItemContentLayoutID(Resources);
        searchLabelLayout.setItemOnclickListener(mOnItemOnclickListener);

        followLableView = view.findViewById(R.id.follow_label_view);

        clearDataImg = view.findViewById(R.id.fragment_follow_img_data_clear);
        clearDataImg.setOnClickListener(this);

        dateBeginTxt.setOnClickListener(this);
        dateEndTxt.setOnClickListener(this);
        addRemark_img.setOnClickListener(this);
        searchRemark_img.setOnClickListener(this);

        refreshView.setOnLoadmoreListener(refreshlayout -> {
            description.setPageIndex(follows.size() / 5 + 1);
            refreshType = 1;
            onUpdateListener.onRefresh(HttpUtil.URL_FOLLOWS, headerDescription, description);
        });

        refreshView.setOnRefreshListener(refreshlayout -> {
            description.setPageIndex(1);
            refreshType = 0;
            onUpdateListener.clearFlag();
            refreshlayout.setEnableLoadmore(true);
            onUpdateListener.onRefresh(HttpUtil.URL_FOLLOWS, headerDescription, description);
        });
    }

    public LineBreakLayout.onItemOnclickListener mOnItemOnclickListener = new LineBreakLayout.onItemOnclickListener() {
        @Override
        public void onItemClick(View view, ViewGroup contentView, int position) {
            contentView.removeView(view);
            if (contentView.getChildCount() == 0) {
                followLableView.setVisibility(View.GONE);
                description.setKeyword(null);
                openFreshView();
            }
        }
    };

    private void initDatas() {
        headerDescription = ((MyApplication) getActivity().getApplicationContext()).getHeaderDescription();
        follows = new ArrayList<>();
        adapter = new InnerAdapter(getContext(), follows);
        lv.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (isFirst) {
                openFreshView();
                isFirst = false;
            }
        } else {
            //相当于Fragment的onPause
            isVisible = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        switch (messageEvent.getMsg()) {
            case DETAIL_FOLLOW:
                List<PropertyFollow> followList = (List<PropertyFollow>) messageEvent.getObject();
                if (followList != null && !followList.isEmpty()) {
                    if (refreshType == 0) follows.clear();
                    follows.addAll(followList);
                    adapter.notifyDataSetChanged();
                    L.d("followSize", follows.size() + "");
                } else {
                    L.d("followSizeNo", follows.size() + "");
                    if (follows.isEmpty() || isDataSearch) {
                        if (!isVisible) {
                            closeRefresh();
                            break;
                        }
                        follows.clear();
                        adapter.notifyDataSetChanged();
                        SimpleTipsDialog dialog = DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_permission_follow_no));
                        dialog.show(getFragmentManager(), "");
                    }
                }
                closeRefresh();
                break;
            case DATA_END:
                isEnd = false;
                closeRefresh();
                refreshView.setEnableLoadmore(false);
                showEndTips();
                break;
            case SEARCH_ALL_NO:
                ableToSearchFollow = false;
                break;
            case FOLLOW_ADD:
                ableToAddFollow = true;
                break;
            case DETAIL_FOLLOW_SUCCESS:
                openFreshView();
                break;
            case NETWORK_STATE_FAIL:
                onFailure();
                break;
        }
    }

    private void showEndTips() {
        Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
    }

    private void closeRefresh() {
        refreshView.finishRefresh();
        refreshView.finishLoadmore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_follow_date_begin:
            case R.id.fragment_follow_date_end:
//                setDate(v);
                break;
            case R.id.img_add_remark:
                trunToAddFollow();
                break;
            case R.id.img_search_remark:
                showSearchEditView();
                break;

            case R.id.fragment_follow_img_data_clear:
                if (dateBeginTxt.getText().toString() != "" || dateEndTxt.getText().toString() != "")
                    openFreshView();
                dateBeginTxt.setText("");
                dateEndTxt.setText("");
                description.setFollowTimeFrom(null);
                description.setFollowTimeTo(null);
                defaultTxt.setVisibility(View.VISIBLE);
                isDataSearch = false;
                break;
        }
    }

    public void onFailure() {
        closeRefresh();
        if (!isVisible) return;
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(getString(R.string.network_unenable));
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getFragmentManager(), "");
    }

    private void trunToAddFollow() {

        if (refreshView.isRefreshing() || refreshView.isLoading()) return;

        if (ableToAddFollow)
            onUpdateListener.trunToActivity(new Intent(getActivity(), FollowAddActivity.class));
        else {
            if (!isVisible) return;
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setContentString(getString(R.string.dialog_tips_permission_follow_no_add));
            simpleTipsDialog.show(getFragmentManager(), "");
        }
    }

    private void showSearchEditView() {
        if (ableToSearchFollow) {
            DialogFragment dialog = DialogFactory.newInstance(REMARK, new DialogFactory.IGetClickItem() {
                @Override
                public void getClickItem(android.support.v4.app.DialogFragment dialog, String... items) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(items[0].trim())) return;
                    followLableView.setVisibility(View.VISIBLE);
                    description.setKeyword(items[0]);
                    searchLabelLayout.removeAllViews();
                    searchLabelLayout.addItem(items[0] + "  X");
                    openFreshView();
                }
            });
            dialog.show(getFragmentManager(), "");
        } else {
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setContentString(getString(R.string.dialog_tips_permission_follow_no_look));
            simpleTipsDialog.show(getFragmentManager(), "");
        }
    }

    public void openFreshView() {
        if (refreshView.isRefreshing()) return;
        if (refreshView.isLoading()) return;
        refreshView.autoRefresh();
    }

    private static class InnerAdapter extends BaseAdapter {

        private Context context;
        private List<PropertyFollow> dataList;

        InnerAdapter(Context context, List<PropertyFollow> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_follow_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.followerTxt = view.findViewById(R.id.follow_txt_follower);
                viewHolder.followTimeTxt = view.findViewById(R.id.follow_txt_time);
                viewHolder.followContentTxt = view.findViewById(R.id.follow_txt_content);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            if (dataList != null) {
                PropertyFollow data = dataList.get(position);
                viewHolder.followerTxt.setText(data.getFollower());
                viewHolder.followTimeTxt.setText(formatData(data.getFollowTime()));
                viewHolder.followContentTxt.setText(data.getFollowContent());
            }
            return view;
        }

        private String formatData(String data) {
            if (data == null) return null;
            data = data.replaceAll("[A-Z]+", " ").trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formaData = null;
            try {
                formaData = sdf.format(sdf.parse(data));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formaData;
        }

        private class ViewHolder {
            TextView followerTxt, followTimeTxt, followContentTxt;
        }
    }


    public interface IFollowFragment {
        void selectDate(Calendar start, Calendar end);
    }

    //返回时间
//    private void setDate(final View clickView) {
//
//        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(selectedDate.get(Calendar.YEAR) - 20, 0, 1);
//
//        //时间选择器
//        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                description.setPageIndex(1);
//                switch (clickView.getId()) {
//                    case R.id.fragment_follow_date_begin:
//                        dateBeginTxt.setText(getTime(date));
//                        description.setFollowTimeFrom(getTime(date));
//                        break;
//
//                    case R.id.fragment_follow_date_end:
//                        dateEndTxt.setText(getTime(date));
//                        description.setFollowTimeTo(getTime(date));
//                        break;
//                    default:
//                        break;
//                }
//                openFreshView();
//            }
//
//            private String getTime(Date date) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                isDataSearch = true;
//                return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//            }
//        })
//                .setCancelText("取消")
//                .setSubmitText("確定")
//                .setTitleText("請選擇日期")
//                .setLabel("", "", "", "时", "分", "秒")
//                .setType(new boolean[]{true, true, true, false, false, false})
//                .setRangDate(startDate, selectedDate)
//                .isCenterLabel(false)
//                .setContentSize(22)
//                .setTitleBgColor(Color.WHITE)
//                .setSubmitColor(getResources().getColor(R.color.colortheme))//确定按钮文字颜色
//                .setCancelColor(Color.BLACK)//取消按钮文字颜色
//                .build();
//        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
//        pvTime.show();
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public interface OnUpdateListener {

        void onRefresh(String address, AHeaderDescription AHeaderDescription, FollowDescription body);

        void trunToActivity(Intent intent);

        void clearFlag();
    }

}
