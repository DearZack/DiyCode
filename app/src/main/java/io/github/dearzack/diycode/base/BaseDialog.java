package io.github.dearzack.diycode.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import io.github.dearzack.diycode.R;

/**
 * Created by Zack on 2017/7/19.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.dialog_progress_hint);
    }
}
