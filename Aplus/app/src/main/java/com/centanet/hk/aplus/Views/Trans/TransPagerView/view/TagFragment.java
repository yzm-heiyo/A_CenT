package com.centanet.hk.aplus.Views.Trans.TransPagerView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.bean.home.PropertyFastSearcherTag;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.eventbus.BUS_MESSAGE;
import com.centanet.hk.aplus.eventbus.BaseClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class TagFragment extends BaseFragment implements View.OnClickListener {

    private List<PropertyFastSearcherTag> tags;
    private ImageView icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8;
    private TextView icon1Txt, icon2Txt, icon3Txt, icon4Txt, icon5Txt, icon6Txt, icon7Txt, icon8Txt;
    private View view;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            if (tags != null && !tags.isEmpty()) refreshView(tags);

        } else {
            //相当于Fragment的onPause
            isVisible = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.item_fastsearch, null, false);
        initView(view);
        initLisenter();
        tags = (List<PropertyFastSearcherTag>) getArguments().get("TAGS");
        if (tags != null && !tags.isEmpty()) refreshView(tags);

        return view;
    }

    private void initLisenter() {

//        icon3.setOnClickListener(this);
//        icon4.setOnClickListener(this);
//        icon5.setOnClickListener(this);
//        icon6.setOnClickListener(this);
//        icon7.setOnClickListener(this);
//        icon8.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        L.d("TagFragment", "onResume:");
    }

    private void refreshView(List<PropertyFastSearcherTag> tags) {
        L.d("TagFragment", "refreshView Size:" + tags.size());
        for (int i = 0; i < tags.size(); i++)
            switch (i) {
                case 0:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon1);
                    icon1Txt.setText(tags.get(i).getItemText());
                    icon1.setTag(R.id.image_key, i);
                    icon1.setOnClickListener(this);

                    break;
                case 1:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon2);
                    icon2Txt.setText(tags.get(i).getItemText());
                    icon2.setTag(R.id.image_key, i);
                    icon2.setOnClickListener(this);

                    break;
                case 2:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon3);
                    icon3Txt.setText(tags.get(i).getItemText());
                    icon3.setTag(R.id.image_key, i);
                    icon3.setOnClickListener(this);

                    break;
                case 3:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon4);
                    icon4Txt.setText(tags.get(i).getItemText());
                    icon4.setTag(R.id.image_key, i);
                    icon4.setOnClickListener(this);

                    break;
                case 4:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon5);
                    icon5Txt.setText(tags.get(i).getItemText());
                    icon5.setTag(R.id.image_key, i);
                    icon5.setOnClickListener(this);

                    break;
                case 5:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon6);
                    icon6Txt.setText(tags.get(i).getItemText());
                    icon6.setTag(R.id.image_key, i);
                    icon6.setOnClickListener(this);

                    break;
                case 6:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon7);
                    icon7Txt.setText(tags.get(i).getItemText());
                    icon7.setTag(R.id.image_key, i);
                    icon7.setOnClickListener(this);

                    break;
                case 7:
                    Glide.with(getActivity()).load(tags.get(i).getItemImgUrl()).into(icon8);
                    icon8Txt.setText(tags.get(i).getItemText());
                    icon8.setTag(R.id.image_key, i);
                    icon8.setOnClickListener(this);

                    break;
            }
    }


    private void initView(View view) {
        L.d("TagFragment", "initView");
        icon1 = view.findViewById(R.id.fast_img_icon1);
        icon2 = view.findViewById(R.id.fast_img_icon2);
        icon3 = view.findViewById(R.id.fast_img_icon3);
        icon4 = view.findViewById(R.id.fast_img_icon4);
        icon5 = view.findViewById(R.id.fast_img_icon5);
        icon6 = view.findViewById(R.id.fast_img_icon6);
        icon7 = view.findViewById(R.id.fast_img_icon7);
        icon8 = view.findViewById(R.id.fast_img_icon8);

        icon1Txt = view.findViewById(R.id.fase_txt_icontext1);
        icon2Txt = view.findViewById(R.id.fase_txt_icontext2);
        icon3Txt = view.findViewById(R.id.fase_txt_icontext3);
        icon4Txt = view.findViewById(R.id.fase_txt_icontext4);
        icon5Txt = view.findViewById(R.id.fase_txt_icontext5);
        icon6Txt = view.findViewById(R.id.fase_txt_icontext6);
        icon7Txt = view.findViewById(R.id.fase_txt_icontext7);
        icon8Txt = view.findViewById(R.id.fase_txt_icontext8);

    }

    @Override
    public void onStart() {
        super.onStart();
        L.d("TagFragment", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.d("TagFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("TagFragment", "onDestroy");
    }

    @Override
    public void onClick(View v) {
        PropertyFastSearcherTag s = tags.get((Integer) v.getTag(R.id.image_key));
        String itemValue = s.getItemValue().substring(1, s.getItemValue().length() - 1).replace("\"", "");
        L.d("setParams_", itemValue);
        try {
            HouseDescription houseDescription = new HouseDescription();
            if (itemValue.indexOf(",") != -1) {
                for (String s1 : itemValue.split(",")) {
                    String[] param = s1.split(":");
                    houseDescription = setParams(houseDescription, param[0], param[1]);
                }
            } else {
                String[] param = itemValue.split(":");
                houseDescription = setParams(houseDescription, param[0], param[1]);
            }
            L.d("Tag_HouseDes", houseDescription.toString());
            new BaseClass().notifyBusMessage(BUS_MESSAGE.HomeFastSearch.FAST_SEARCH, houseDescription);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public <T> T setParams(T data, String key, String value) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            L.d("setParams", field.getName() + " key:" + key);
            String type = field.getGenericType().toString();

            if (key.substring(0, 2).equals("Is") || key.substring(0, 3).equals("Has")) {
                type = Boolean.class.toString();
            }

            if (field.getName().equals(key)) {

                if (type.equals("class java.lang.Boolean")) {
                    try {
                        Method method = null;
                        try {
                            method = data.getClass().getMethod("set" + field.getName().replace("Is", ""), String.class);
                            if(method!=null)
                                L.d("method",method.toString());
                            method.invoke(data, true + "");

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                if (type.equals("class java.lang.String")) {
                    try {
                        Method method = null;
                        try {
                            method = data.getClass().getMethod("set" + field.getName(), String.class);
                            method.invoke(data, value);

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                if (type.equals("java.util.List<java.lang.String>")) {
                    try {
                        Method method = null;
                        L.d("List<String>", value);
                        try {
                            method = data.getClass().getMethod("set" + field.getName(), List.class);
                            List<String> list = new ArrayList<>();
                            list.add(value);
                            method.invoke(data, list);

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return data;
    }


}
