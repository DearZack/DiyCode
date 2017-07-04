package io.github.dearzack.diycode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import static android.text.TextUtils.isEmpty;

/**
 * Created by zack on 2017-7-4.
 */

public class ZWebView extends WebView {

    public ZWebView(Context context) {
        this(context, null);
    }

    public ZWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(false);
        setFocusable(false);

        setHorizontalScrollBarEnabled(false);

        WebSettings settings = getSettings();
        settings.setDefaultFontSize(14);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
    }

    //此处代码来自 https://github.com/plusend/DiyCode
    public static String setupWebContent(String content, boolean isShowHighlight, boolean isShowImagePreview, String css) {
        if (isEmpty(content) || isEmpty(content.trim())) return "";

        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        //if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
        //        || TDevice.isWifiOpen()) {
        // 过滤掉 img标签的width,height属性
        content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

        // 添加点击图片放大支持
        if (isShowImagePreview) {
            content = content.replaceAll("<img[^>]+src=\"([^\"\'\\s]+)\"[^>]*>",
                    "<img src=\"$1\" onClick=\"javascript:mWebViewImageListener.showImagePreview('$1')\"/>");
            content = content.replaceAll(
                    "<a\\s+[^<>]*href=[\"\']([^\"\']+)[\"\'][^<>]*>\\s*<img\\s+src=\"([^\"\']+)\"[^<>]*/>\\s*</a>",
                    "<a href=\"$1\"><img src=\"$2\"/></a>");
        }
        //} else {
        //    // 过滤掉 img标签
        //    content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*/>", "");
        //}

        // 过滤table的内部属性
        content = content.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");

        return String.format("<!DOCTYPE html>"
                + "<html><head>"
                + (isShowHighlight
                ? "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/html/css/front.css\">"
                : "")
                + (isShowHighlight
                ? "<script type=\"text/javascript\" src=\"file:///android_asset/html/js/d3.min.js\"></script>"
                : "")
                + "%s"
                + "</head>"
                + "<body data-controller-name=\"topics\">"
                + "<div class=\"row\"><div class=\"col-md-9\"><div class=\"topic-detail panel panel-default\"><div class=\"panel-body markdown\">"
                + "<article>"
                + "%s"
                + "</article>"
                + "</div></div></div></div>"
                + "</body></html>", (css == null ? "" : css), content);
    }

}
