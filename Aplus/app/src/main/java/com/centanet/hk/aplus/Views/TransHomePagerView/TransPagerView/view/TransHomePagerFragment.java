package com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.FileUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseFragment;
import com.centanet.hk.aplus.Views.SearchView.view.SearchFragment;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.presenter.TransHomePreaenter;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.presenter.ITransHomePresenter;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.view.TransSearchFragment;
import com.centanet.hk.aplus.Views.TransListFragment.view.TransListFragment;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseRecycleAdapter;
import com.centanet.hk.aplus.Views.basic.BaseViewHolder;
import com.centanet.hk.aplus.Widgets.TransUserDesignView;
import com.centanet.hk.aplus.Widgets.UserDesignView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.home.FastSearchRequest;
import com.centanet.hk.aplus.bean.home.PropertyFastSearcherTag;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.bean.userdesign_option.TransSearchHistory;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.AppNetViewDataManager;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.TransRequestParamsManager;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.SHOW;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class TransHomePagerFragment extends BaseFragment implements IHomeView {

    public static final String FRAGMENT_TAG_HOMEPAGER = "HOMEPAGER";
    private RecyclerView recyclerView;
    private List<PropertyParamHints> historys;
    private TextView hisClearTxt, userDesignClearTxt;
    private HistoryAdapter adapter;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ITransHomePresenter presenter;
    private AHeaderDescription header;
    private List<PropertyFastSearcherTag> searcherTags;
    private RadioGroup group;
    private TextView searchTxt;
    private ImageView micImg;
    private OnHomePagerRequestLisenter requestLisenter;
    private List<TransSearchHistory> transSearchHistories;
    public static final int HOME_SEARCH = 1;
    public static final int HOME_FAST_SEARCH = 2;
    public static final int HOME_HISTORY_SEARCH = 3;
    public static final int HOME_USERDESIGN_SEARCH = 4;
    private boolean hasLoad;
    private TransUserDesignView userDesignView;
    private TextView noRecodeTxt, nouseDesignTxt;
    private boolean isFirst = true;
    private ScrollView content;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_homepage, null, false);
        initView(view);
        init();
        initLisenter();

        return view;
    }

    private void init() {

        header = ApplicationManager.getApplication().getHeaderDescription();

        presenter = new TransHomePreaenter(this);

        L.d("TransHomePagerFragment", "init");

    }

    private void initLisenter() {
        L.d("TransHomePagerFragment", "initLisenter");
        hisClearTxt.setOnClickListener(v -> {
            LitePal.deleteAll(PropertyParamHints.class);
            historys.clear();
            hisClearTxt.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            refreshSearchHisState(false);
        });

//        adapter.setOnItemClickLisenter((v, position) -> {
//            L.d("RecycleView", "" + position);
//        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkRGindex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        searchTxt.setOnClickListener(v -> {
            L.d("TransHomePagerFragment", "searchTxt OnClick");
//            FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.hide(this);
//            ft.add(R.id.fragment_content, new SearchFragment(), SearchFragment.FRAGMENT_TAG_SEARCH).commit();
            turnToFragment(new TransSearchFragment(), TransSearchFragment.FRAGMENT_TAG_SEARCH);
        });

        micImg.setOnClickListener(v -> {
            TransSearchFragment searchFragment = new TransSearchFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("mic", true);
            searchFragment.setArguments(bundle);
            turnToFragment(searchFragment, TransSearchFragment.FRAGMENT_TAG_SEARCH);
        });

        userDesignView.setOnClickListener((v, position) -> {
            TransListFragment houseFragment = new TransListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("HOUSE_REQUEST", transSearchHistories.get(position).getHouseDescription());
            TransRequestParamsManager.setParams(transSearchHistories.get(position).getManager());
            houseFragment.setArguments(bundle);
//            PropertyRequestParamsManager.setParams(transSearchHistories.get(position).getManager());
            turnToFragment(houseFragment, TransListFragment.FRAGMENT_TAG_TRANSLIST);
        });

        userDesignClearTxt.setOnClickListener(v -> {
            String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "tranUserDesign.txt";
            File file = new File(path);
            if (file.exists()) file.delete();
            transSearchHistories.clear();
            userDesignClearTxt.setVisibility(View.GONE);
            nouseDesignTxt.setVisibility(View.VISIBLE);
            userDesignView.removeAllViews();
        });
    }

    private void refreshSearchHisState(boolean isDataExist) {
        hisClearTxt.setVisibility(isDataExist ?View.VISIBLE:View.GONE);
        recyclerView.setVisibility(isDataExist ?View.VISIBLE:View.GONE);
        noRecodeTxt.setVisibility(isDataExist ?View.GONE:View.VISIBLE);
    }

    private void turnToFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.hide(this);
        ft.add(R.id.fragment_content, fragment, tag).commit();
    }

    private void initView(View view) {
        L.d("TransHomePagerFragment", "initView");
        hisClearTxt = view.findViewById(R.id.home_txt_clearhistory);
        recyclerView = view.findViewById(R.id.home_rv_searchhistory);
        micImg = view.findViewById(R.id.mic);
        userDesignView = view.findViewById(R.id.uerdesignview);
        userDesignClearTxt = view.findViewById(R.id.home_txt_clearuserdesign);
        content = view.findViewById(R.id.pager_sl_content);
        nouseDesignTxt = view.findViewById(R.id.home_txt_usedesignnorecord);
        noRecodeTxt = view.findViewById(R.id.home_txt_searchnorecord);

        pager = view.findViewById(R.id.home_vp);
        group = view.findViewById(R.id.home_rg_tags);

        searchTxt = view.findViewById(R.id.home_txt_edit);

//        pagerAdapter = new PagerAdapter(getFragmentManager(), getFragments());
//        pager.setAdapter(pagerAdapter);

        refreshData();

//        propertySearchHistories = new ArrayList<>();

        adapter = new HistoryAdapter(historys);
        adapter.setOnItemClickLisenter((v, position) -> {
            L.d("RecycleView", "" + position);

            L.d("HistoryAdapter", "position: " + position);
            PropertyParamHints s = historys.get(position);
            List<PropertyParamHints> propertyParamHints = new ArrayList<>();
            propertyParamHints.add(s);
            if (PropertyRequestParamsManager.getParams() == null) {
                TransRequestParamsManager.setParams(new TransRequestSaveParams());
            }
            TransRequestParamsManager.getParams().setAddress(propertyParamHints);
            String add = s.getKeyIdType() + ":" + s.getKeyId();
            List<String> address = new ArrayList<>();
            address.add(add);
            TransListRequest request = new TransListRequest();
            request.setSearcherAddress(address);
            TransListFragment houseFragment = new TransListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("HOUSE_REQUEST", request);
            houseFragment.setArguments(bundle);
//            PropertyRequestParamsManager.setParams(propertySearchHistories.get(position).getManager());
            turnToFragment(houseFragment, TransListFragment.FRAGMENT_TAG_TRANSLIST);

        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TransTagFragment());
        fragments.add(new TransTagFragment());
        fragments.add(new TransTagFragment());
        return fragments;
    }

    public void scrollToTop() {
        content.scrollTo(0,0);
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            refreshData();
//        } else {
//            //相当于Fragment的onPause
//
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            // 发起网络请求, 刷新界面数据
            refreshData();
            getUserDesignList();
            if (isFirst) {
                if (AppNetViewDataManager.getTranTagList() == null) {
                    FastSearchRequest request = new FastSearchRequest(16);
                    L.d("TransPager", request.toString());
                    presenter.doGet(HttpUtil.URL_FAST_SEARCH, header, request);
                } else regfreshFastTag();
                L.d("isShowFront", "");
                isFirst = false;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 这里的 isResumed() 判断就是为了避免与 onResume() 方法重复发起网络请求
        if (isVisible() && isResumed()) {
            MessageEventBus msg = new MessageEventBus();
            msg.setMsg(SHOW);
            EventBus.getDefault().post(msg);
            refreshData();
//            getUseDesign();
            getUserDesignList();

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            refreshData();
        }
    }

    private void refreshData() {
        if (historys == null) historys = new ArrayList<>();
        if (historys != null) historys.clear();
        historys.addAll(LitePal.findAll(PropertyParamHints.class));
        if (adapter != null) adapter.notifyDataSetChanged();

        if(historys.isEmpty()){
            refreshSearchHisState(false);
        }else refreshSearchHisState(true);

        if (transSearchHistories == null) transSearchHistories = new ArrayList<>();
        if (transSearchHistories != null) transSearchHistories.clear();
    }

    private void getUserDesignList() {
        String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "tranUserDesign.txt";
        File file = new File(path);
        if (file.exists()) {
            new Thread(() -> {
                List<TransSearchHistory> searchHistories = new ArrayList<>();
                searchHistories = GsonUtil.getObjectList(FileUtil.getFile(file), TransSearchHistory.class);
                L.d("TransSaveOption", searchHistories.toString());
                transSearchHistories.addAll(searchHistories);
                getActivity().runOnUiThread(() -> {
                    if (transSearchHistories.isEmpty()) {
                        refreshUseDesignState(false);
                    } else refreshUseDesignState(true);
                    userDesignView.removeAllViews();
                    userDesignView.addItem(transSearchHistories);
                });
            }).start();
        } else {
            refreshUseDesignState(false);
        }
    }

    private void refreshUseDesignState(boolean isDataExist) {
        userDesignView.setVisibility(isDataExist ?View.VISIBLE:View.GONE);
//        userDesignClearTxt.setVisibility(isDataExist ?View.VISIBLE:View.GONE);
        nouseDesignTxt.setVisibility(isDataExist ?View.GONE:View.VISIBLE);
    }


    @Override
    public void regfreshFastTag() {
        if (getActivity() == null) return;
        getActivity().runOnUiThread(() -> {
            searcherTags = AppNetViewDataManager.getTranTagList();
            L.d("TagSize", searcherTags.size() + "");

            int size = searcherTags.size();

            List<PropertyFastSearcherTag> tags = new ArrayList<>();

            List<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < size; i++) {


                tags.add(searcherTags.get(i));

                if (tags.size() == 8) {
                    TransTagFragment tagFragment = new TransTagFragment();
                    Bundle bundle = new Bundle();
                    List<PropertyFastSearcherTag> fragmentTags = new ArrayList<>();
                    fragmentTags.addAll(tags);
                    bundle.putSerializable("TAGS", (Serializable) fragmentTags);
                    tagFragment.setArguments(bundle);
                    fragments.add(tagFragment);
                    if (fragments.size() != 1) group.addView(getSpace());
                    group.addView(getRB());
                    tags.clear();
                }
            }

            if (tags.size() > 0) {
                TransTagFragment tagFragment = new TransTagFragment();
                Bundle bundle = new Bundle();
                List<PropertyFastSearcherTag> fragmentTags = new ArrayList<>();
                fragmentTags.addAll(tags);
                bundle.putSerializable("TAGS", (Serializable) fragmentTags);
                tagFragment.setArguments(bundle);
                fragments.add(tagFragment);
//                tags.clear();
                if (fragments.size() != 1) group.addView(getSpace());
                group.addView(getRB());
            }

            checkRGindex(0);
            pagerAdapter = new PagerAdapter(getFragmentManager(), fragments);
            pager.setAdapter(pagerAdapter);
            L.d("ReLoadPager", "");
        });
    }

    private RadioButton getRB() {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setButtonDrawable(null);
        radioButton.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(getContext(), 7), DensityUtil.dip2px(getContext(), 7)));
        radioButton.setBackground(getResources().getDrawable(R.drawable.selector_circle_red_grey));
        return radioButton;
    }

    private void checkRGindex(int index) {
        int count = group.getChildCount();
        int currentItem = 0;
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof RadioButton) {
                if (currentItem == index)
                    ((RadioButton) view).setChecked(true);
                currentItem++;
            }
        }
    }

    private Space getSpace() {
        Space space = new Space(getContext());
        space.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(getContext(), 10), ViewGroup.LayoutParams.WRAP_CONTENT));
        return space;
    }

    public void getUseDesign() {
        List<PropertySearchHistory> data = LitePal.findAll(PropertySearchHistory.class);
        L.d("getUseDesign", LitePal.findAll(PropertySearchHistory.class).toString());
    }

    class HistoryAdapter extends BaseRecycleAdapter<PropertyParamHints> {

        public OnItemClickLisenter lisenter;

        public HistoryAdapter(List<PropertyParamHints> datas) {
            super(datas);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_list_homesearch;
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position) {

            holder.getView(R.id.address).setVisibility(View.GONE);
            holder.getView(R.id.street).setVisibility(View.VISIBLE);
            String add = datas.get(position).getAddressName();
            if (add != null && !add.equals("")) {
                setItemText(holder.getView(R.id.address), datas.get(position).getAddressName());
                holder.getView(R.id.address).setVisibility(View.VISIBLE);
            }

            String title = null;
            if (datas.get(position).getDistrictName() != null) {
                title = datas.get(position).getDistrictName() + " ";
            }
            holder.getView(R.id.content).setTag(position);
            holder.getView(R.id.content).setOnClickListener(v -> {
                if (lisenter != null) lisenter.onClick(v, (Integer) v.getTag());
            });
            setItemText(holder.getView(R.id.street), title + datas.get(position).getAreaName());
        }

        public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
            this.lisenter = lisenter;
        }

        public OnItemClickLisenter getLisenter() {
            return lisenter;
        }
    }

    public interface OnHomePagerRequestLisenter {
        void onHomePagerRequest(int type, String... data);
    }

}
