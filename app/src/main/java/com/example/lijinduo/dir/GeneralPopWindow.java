package com.example.lijinduo.dir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 版权：XXX公司 版权所有
 * 作者：lijinduo
 * 版本：2.0
 * 创建日期：2017/8/3
 * 描述：(重构)
 * 修订历史：
 * 参考链接：
 */
public class GeneralPopWindow  {

    private Context context;

    public GeneralPopWindow(Context context) {
        this.context=context;
        initView();
    }

    private void initView(){
         View view = LayoutInflater.from(context).inflate(R.layout.general_popwidow,null);





    }


}
