package com.alvin.refactor.duplicate;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/24  20:50
 * @version: 1.0.0
 */
public class DupSon2 extends DupFather {

    public void say1() {
        String msg = "sadf" + "fadf" + "sdasdf";
        msg += "aa";

        say(msg);
    }
}
