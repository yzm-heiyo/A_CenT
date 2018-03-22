package com.centanet.hk.aplus.Views.HouseDetailView.view;

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

import com.bigkoo.pickerview.TimePickerView;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FollowAddView.view.FollowAddActivity;
import com.centanet.hk.aplus.Views.MineView.present.FeedBackPresent;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.entity.http.FollowDescription;
import com.centanet.hk.aplus.entity.detail.PropertyFollow;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.ParameterDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.DialogType.REMARK;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.FOLLOW_ADD;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.SEARCH_ALL_NO;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public abstract class FollowFragmentAbst extends Fragment implements View.OnClickListener {

    private String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private BaseAdapter adapter;
    private TextView dateBeginTxt, dateEndTxt, defaultTxt;
    private IFollowFragment follow;
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
        follow = setFollow();
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

    protected abstract IFollowFragment setFollow();


    private void initViews() {
        lv = view.findViewById(R.id.fragment_follow_listview);
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

        refreshView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                description.setPageIndex(follows.size() / 5 + 1);
                onUpdateListener.onRefresh(HttpUtil.URL_FOLLOWS, headerDescription, description);
            }
        });

        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                description.setPageIndex(1);
                follows.clear();
                onUpdateListener.clearFlag();
                onUpdateListener.onRefresh(HttpUtil.URL_FOLLOWS, headerDescription, description);
            }
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
    public void onResume() {
        super.onResume();
//        openFreshView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            openFreshView();
        } else {
            //相当于Fragment的onPause
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        switch (messageEvent.getMsg()) {
            case DETAIL_FOLLOW:
                List<PropertyFollow> followList = (List<PropertyFollow>) messageEvent.getObject();
                if (followList != null) {
                    follows.addAll(followList);
                    adapter.notifyDataSetChanged();
                }
                closeRefresh();
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
        }
    }

    private void closeRefresh() {
        refreshView.finishRefresh();
        refreshView.finishLoadmore();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_follow_date_begin:
            case R.id.fragment_follow_date_end:
                setDate(v);
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
                break;
        }
    }

    private void trunToAddFollow() {
        if (ableToAddFollow)
            onUpdateListener.trunToActivity(new Intent(getActivity(), FollowAddActivity.class));
        else {
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
                viewHolder.followTimeTxt.setText(data.getFollowTime());
                viewHolder.followContentTxt.setText(data.getFollowContent());
            }
            return view;
        }

        private class ViewHolder {
            TextView followerTxt, followTimeTxt, followContentTxt;
        }
    }

    public interface IFollowFragment {
        void selectDate(Calendar start, Calendar end);
    }

    //返回时间
    private void setDate(final View clickView) {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR) - 20, 0, 1);

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                switch (clickView.getId()) {
                    case R.id.fragment_follow_date_begin:
                        dateBeginTxt.setText(getTime(date));
                        description.setFollowTimeFrom(getTime(date));
                        break;

                    case R.id.fragment_follow_date_end:
                        dateEndTxt.setText(getTime(date));
                        description.setFollowTimeTo(getTime(date));
                        break;
                    default:
                        break;
                }
                openFreshView();
            }

            private String getTime(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            }
        })
                .setCancelText("取消")
                .setSubmitText("確定")
                .setTitleText("請選擇日期")
                .setLabel("", "", "", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, false, false, false})
                .setRangDate(startDate, selectedDate)
                .isCenterLabel(false)
                .setContentSize(22)
                .setTitleBgColor(Color.WHITE)
                .setSubmitColor(getResources().getColor(R.color.colortheme))//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

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
