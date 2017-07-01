package io.github.dearzack.diycode;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.gcssloop.diycode_sdk.api.Diycode;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;

/**
 * Created by Zack on 2017/6/20.
 */

public class APP extends Application {

    public static final String client_id = "bf4af74f";
    public static final String client_secret = "55162883298661457dd8ce48f39077aa56fd90f88fc471dc25a6aca041d93aae";
    public static UserDetail userDetail;


    @Override
    public void onCreate() {
        super.onCreate();
        Diycode.init(this, client_id, client_secret);
        Utils.init(this);
    }
}
