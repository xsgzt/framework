package com.xsgzt.framework.ioc;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/29.
 */

public class CheckEditUtils {
    private ArrayList<EditText> editTexts;
    private Object target;
    private View mView;

    private CheckEditUtils(Object target, View view) {
        editTexts = new ArrayList<>();
        this.target = target;
        this.mView = view;
        bind(target, view);
    }

    public static CheckEditUtils bindEdit(Object target, View view) {
//        if(target instanceof Activity) {
//            //是activity
//
//        }
//
//        if(target instanceof Fragment) {
//            //是fragment
//
//        }
//
//        if(target instanceof View) {
//            //是View
//
//        }
        return new CheckEditUtils(target, view);

    }

    private void bind(Object target, View view) {
        editTexts.clear();


        //获取到
        final Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (mView == null) {
                ControlBtnIsEnable btnControl = declaredField.getAnnotation(ControlBtnIsEnable.class);
                if (btnControl != null) {
                    try {
                        mView = (View) declaredField.get(target);
                        mView.setEnabled(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }

            CheEdit annotation = declaredField.getAnnotation(CheEdit.class);
            if (annotation != null) {
                //说明是edi
                Log.e("bind: ", declaredField.getName());
                //获取属性值
                EditText editText = null;
                try {
                    editText = (EditText) declaredField.get(target);
                    editTexts.add(editText);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }



                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        boolean flag = true;
                        for (EditText et : editTexts) {
                            if (TextUtils.isEmpty(et.getText().toString())) {
                                flag = false;
                            }
                        }

                        if (flag) {
                            Log.e("onTextChanged: ", "doutianshangle");
                            mView.setEnabled(true);
                        } else {
                            mView.setEnabled(false);
                            Log.e("onTextChanged: ", "没有填");
                        }


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        }
    }

    public void unBind() {
        if(editTexts != null) {
            editTexts.clear();
            editTexts = null;
        }
        target = null;
        mView  = null;

    }
}
