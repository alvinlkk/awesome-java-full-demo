package com.alvin.refactor.duplicate;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/24  20:49
 * @version: 1.0.0
 */
public class DupSon1 extends DupFather {

    public void say1() {
        String msg = "sadf" + "fadf";

        say(msg);
    }

}
