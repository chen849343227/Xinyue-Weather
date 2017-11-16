package com.chen.xinyueweather.utils;

import com.chen.xinyueweather.R;

import java.util.HashMap;
import java.util.Map;


/**
 * @date Created:17-11-13
 * @author along
 * @Description
 */
public class Constant {

    public final static Map<String, Integer> ZHISHU = new HashMap();

    static {
        ZHISHU.put("化妆指数",R.drawable.ic_hzzs);
        ZHISHU.put("感冒指数", R.drawable.ic_gmzs);
        ZHISHU.put("洗车指数", R.drawable.ic_xichezhishu_white);
        ZHISHU.put("穿衣指数", R.drawable.ic_chuanyizhishu_white);
        ZHISHU.put("紫外线强度指数", R.drawable.ic_ziwaixian_white);
        ZHISHU.put("运动指数", R.drawable.ic_yundongzhishu_white);
    }


}
