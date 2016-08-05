package com.oceancx.androidlib.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oceancx.androidlib.R;

/**
 * Created by oceancx on 2016/6/30.
 */
public class SpanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        TextView tv2 = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView(tv, params);
        ll.addView(tv2, params);
        setContentView(ll);
        //创建一个SpannableString对象
        SpannableString sStr = new SpannableString("最是那一低头的温柔，像一朵水莲花不胜凉风的娇羞，道一声珍重，道一声珍重，那一声珍重里有蜜甜的忧愁");
        //设置字体(default,default-bold,monospace,serif,sans-serif)
        sStr.setSpan(new TypefaceSpan("default"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new TypefaceSpan("default-bold"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new TypefaceSpan("monospace"), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new TypefaceSpan("serif"), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new TypefaceSpan("sans-serif"), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小（绝对值,单位：像素）,第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素
        sStr.setSpan(new AbsoluteSizeSpan(20), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new AbsoluteSizeSpan(20, true), 12, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍   ,0.5表示一半
        sStr.setSpan(new RelativeSizeSpan(0.5f), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体前景色
        sStr.setSpan(new ForegroundColorSpan(Color.RED), 16, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体背景色
        sStr.setSpan(new BackgroundColorSpan(Color.CYAN), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体样式: NORMAL正常，BOLD粗体，ITALIC斜体，BOLD_ITALIC粗斜体
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 20, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 21, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 23, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置下划线
        sStr.setSpan(new UnderlineSpan(), 24, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置删除线
        sStr.setSpan(new StrikethroughSpan(), 26, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置上下标
        sStr.setSpan(new SubscriptSpan(), 28, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr.setSpan(new SuperscriptSpan(), 30, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍 ,2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
        sStr.setSpan(new ScaleXSpan(2.0f), 32, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置项目符号
        sStr.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN), 0, sStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
        //设置图片
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        sStr.setSpan(new ImageSpan(drawable), 24, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(sStr);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString sStr2 = new SpannableString("电话邮件百度一下短信彩信进入地图");
        //超级链接（需要添加setMovementMethod方法附加响应）
        sStr2.setSpan(new URLSpan("tel:8008820"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话
        sStr2.setSpan(new URLSpan("mailto:email@echoecho.com?subject=SweetWords\n" + "&body=Please send me a copy of your new program!"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //邮件
        sStr2.setSpan(new ForegroundColorSpan(0xfffb7299), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sStr2.setSpan(new URLSpan("http://www.baidu.com"), 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络
        sStr2.setSpan(new URLSpan("sms:10086"), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //短信   使用sms:或者smsto:
        sStr2.setSpan(new URLSpan("mms:10086"), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //彩信   使用mms:或者mmsto:
        sStr2.setSpan(new URLSpan("geo:32.123456,-17.123456"), 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //地图

        tv2.setText(sStr2);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new AlertDialog.Builder(v.getContext())
                        .setMessage("激活码为：" + "askjdlsadlsajdlsajdlk")
                        .setPositiveButton("复制激活码", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager mClipboard = (ClipboardManager) getSystemService(Activity.CLIPBOARD_SERVICE);
                                mClipboard.setPrimaryClip(ClipData.newPlainText("Styled Text", "asdlkjlaksjdlsajdlsajd"));
                                Toast.makeText(v.getContext(), "已复制:" + mClipboard.getPrimaryClip().getItemAt(0).getText(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }
}
