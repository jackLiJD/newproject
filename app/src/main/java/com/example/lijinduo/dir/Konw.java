package com.example.lijinduo.dir;

/**
 * 版权：XXX公司 版权所有
 * 作者：lijinduo
 * 版本：2.0
 * 创建日期：2017/7/24
 * 描述：(重构)
 * 修订历史：
 * 参考链接：
 */
public class Konw implements Face{

    @Override
    public void face() {

    }

    public interface lijiduo{
       void lijinduo();
    }

    public Konw() {
    }

    public void init() {
        lijiduo lijinduo=new lijiduo() {
            @Override
            public void lijinduo() {

            }
        };
        lijinduo.lijinduo();
//        Face face =new Face() {
//            @Override
//            public void face() {
//
//            }
//        };
//        face.face();
    }
}
