package com.alvin.refactor.duplicate;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/24  20:57
 * @version: 1.0.0
 */
public class DupCode1 {

    public void say1() {
        String msg = "sadf" + "fadf";
        msg += "ccasdf";

        new DupCode().say(msg);
    }
}
