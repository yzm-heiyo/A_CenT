package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.FollowView.view.FollowActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchActivity;
import com.centanet.hk.aplus.Views.basic.BaseListAdapter;
import com.centanet.hk.aplus.bean.district.DistrictItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/1/25.
 * 提示dialog
 * shape的實現需要
 * getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 * 設置dialogfragment需要設置
 * getDialog().getWindow().setLayout()
 */

public class AreaDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private ImageView imageView;
    private TextView yesTxt;
    private ListView listView;
    private OnItemClickLisenter onItemClickLisenter;
    private List<DistrictItem> data = new ArrayList<>();
    private List<String> selects = new ArrayList<>();
    private String thiz = getClass().getSimpleName();
    private DirstricAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
    }

    private void initLisenter() {

    }

    private void init() {
//        data = new ArrayList<>();
//        selects = new ArrayList<>();

        adapter = new DirstricAdapter(getContext(), data, selects, R.layout.item_list_area);
//        adapter.setOnAdapterItemClickLisenter((v, postion) -> {
//            if (v.isSelected()) {
//                selects.add(data.get(postion).getDistrictKeyId());
//            } else {
//                selects.add(data.get(postion).getDistrictKeyId());
//            }
////            adapter.notifyDataSetChanged();
//            L.d("DialogSelect", selects.toString());
//        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            DirstricAdapter.ViewHolder holder = (DirstricAdapter.ViewHolder) view.getTag();
            holder.box.setChecked(!holder.box.isChecked());
            boolean isSelect = holder.box.isChecked();
            if (isSelect) {
                selects.add(data.get(position).getDistrictKeyId());
            } else selects.remove(data.get(position).getDistrictKeyId());
            adapter.notifyDataSetChanged();
            L.d("thiz", "setOnItemClickListener");
        });

    }

    public void setItem(List<DistrictItem> items, List<String> selects) {

        if (data != null) data.clear();
        if (this.selects != null) this.selects.clear();
        data.addAll(items);
        if (selects != null)
            this.selects.addAll(selects);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_area);

        imageView = dialog.findViewById(R.id.close);
        imageView.setOnClickListener(this);
        yesTxt = dialog.findViewById(R.id.area_txt_yes);
        yesTxt.setOnClickListener(this);
        listView = dialog.findViewById(R.id.search_list_area);


        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.area_txt_yes:
                if (onItemClickLisenter != null) onItemClickLisenter.onClick(dialog, v, selects);
                dismiss();
                break;
        }
    }


    private class DirstricAdapter extends BaseListAdapter<DistrictItem> {

        private List<String> selects;
        private OnAdapterItemClickLisenter onAdapterItemClickLisenter;

        public DirstricAdapter(Context context, List<DistrictItem> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        public DirstricAdapter(Context context, List<DistrictItem> datas, List<String> selects, int layoutId) {
            super(context, datas, layoutId);
            this.selects = selects;
        }

        public void setOnAdapterItemClickLisenter(OnAdapterItemClickLisenter onAdapterItemClickLisenter) {
            this.onAdapterItemClickLisenter = onAdapterItemClickLisenter;
        }

        @Override
        protected View getItemView(View view, View convertView, int position, List<DistrictItem> datas) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_area, null, false);
                viewHolder = new ViewHolder();
                viewHolder.box = view.findViewById(R.id.item_area_checkbox);
                viewHolder.title = view.findViewById(R.id.item_area_txt_title);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            L.d(thiz, "Adapter Position: " + position);

            if (datas != null && !datas.isEmpty()) {
                viewHolder.box.setChecked(false);
                if (selects != null && selects.contains(datas.get(position).getDistrictKeyId()))
                    viewHolder.box.setChecked(true);

                viewHolder.title.setText(datas.get(position).getDistrictName());
            }
            return view;
        }

        class ViewHolder {
            CheckBox box;
            TextView title;
        }
    }

    public interface OnAdapterItemClickLisenter {
        void onClick(View v, int postion);
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }


    public interface OnItemClickLisenter {
        void onClick(Dialog dialog, View v, List<String> list);
    }
}
