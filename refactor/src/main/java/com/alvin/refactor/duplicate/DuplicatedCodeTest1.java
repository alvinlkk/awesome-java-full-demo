package com.alvin.refactor.duplicate;

/**
 * <p>描 述：</p>
 *
 * @author: cxw (332059317@qq.com)
 * @date: 2022/11/24  20:30
 * @version: 1.0.0
 */
public class DuplicatedCodeTest1 {

    public void say1() {
        String msg = "sadf" + "fadf";
        say(msg);
    }

    private void say(String msg) {
        System.out.println("***************");
        System.out.println(msg);
        System.out.println("***************");
    }

    public void say2(String arg) {
        String msg = "sadf" + "fadf" + "sdasdf";
        msg += "aa";
        say(msg);
    }
}
