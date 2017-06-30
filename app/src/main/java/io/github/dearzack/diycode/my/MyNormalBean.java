package io.github.dearzack.diycode.my;

/**
 * Created by Zack on 2017/6/29.
 */

public class MyNormalBean {
    private int logoRes;
    private String hint;
    private int count;
    private boolean needMargin;
    private boolean needHideCount;

    public int getLogoRes() {
        return logoRes;
    }

    public void setLogoRes(int logoRes) {
        this.logoRes = logoRes;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isNeedMargin() {
        return needMargin;
    }

    public void setNeedMargin(boolean needMargin) {
        this.needMargin = needMargin;
    }

    public boolean isNeedHideCount() {
        return needHideCount;
    }

    public void setNeedHideCount(boolean needHideCount) {
        this.needHideCount = needHideCount;
    }
}
