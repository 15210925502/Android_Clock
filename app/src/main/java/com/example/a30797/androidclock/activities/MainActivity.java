package com.example.a30797.androidclock.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.a30797.androidclock.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //num用于确定背景图
    private int num;
    //index用于确定字体样式
    private int index;
    //count用月确定字体颜色
    private int count;
    private SimpleDraweeView imageView;
    private AdaptionSizeTextView textView;
    private AdaptionSizeTextView textViewDate;
    private AdaptionSizeTextView textViewOther;
    private StyleSpan styleSpan_B;
    private float dateFontSize;
    private float timeFontSize;
    private float otherFontSize;
    private RelativeSizeSpan sizeSpan01;
    private RelativeSizeSpan sizeSpan02;
    //创建自带语音对象
    private android.speech.tts.TextToSpeech textToSpeech;

    private final int[] imageIds = new int[]{
            R.drawable.bac_3,
            R.drawable.bac_2,
            R.drawable.bac_4,
            R.drawable.bac_5
    };

    private final int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.colorBlack,
            R.color.colorTextWhite,
            R.color.colorTextGray
    };

    private final String[] fontTypes = new String[]{
            "LESLIE.ttf",
            "PUTHIAfont.ttf",
            "FX-LED.ttf",
            "Lets-go-Digital.ttf",
            "DS-Digital.ttf",
            "AppleSDGothicNeo-Regular.otf",
            "AppleSDGothicNeo-SemiBold.otf",
            "AppleSDGothicNeo-Thin.otf",
            "AppleSDGothicNeoBold.otf",
            "AppleSDGothicNeoMedium.otf",
            "Arial.ttf",
            "ArialBold.ttf",
            "ArialBoldItalic.ttf",
            "ArialHB.ttc",
            "ArialItalic.ttf",
            "ArialRoundedMTBold.ttf",
            "Copperplate.ttc",
            "DB_LCD_Temp-Black.ttf",
            "DevanagariSangamMN.ttc",
            "DFPGB_H3.ttf",
            "DFPGB_H5.ttf",
            "DFPGB_H7.ttf",
            "DFPGB_S5.ttf",
            "DFPGB_S9.ttf",
            "DFPGB_Y5.ttf",
            "digital-7.ttf",
            "DINAlternate-bold.ttf",
            "DINCondensed-bold.ttf",
            "EuphemiaCAS.ttc",
            "Fallback.ttf",
            "FZCSK.ttf",
            "FZLTHYS-GB18030.ttf",
            "GeezaPro.ttf",
            "GeezaProBold.ttf",
            "GeezaProLight.ttf",
            "Georgia.ttf",
            "GeorgiaBold.ttf",
            "GeorgiaBoldItalic.ttf",
            "GeorgiaItalic.ttf",
            "GujaratiSangamMN.ttc",
            "GurmukhiMN.ttc",
            "HiraginoKakuGothicW1.ttc",
            "HiraginoKakuGothicW2.ttc",
            "HiraginoKakuGothicW3.ttc",
            "HiraginoKakuGothicW6.ttc",
            "HiraginoMinchoProNW3.otf",
            "HiraginoMinchoProNW6.otf",
            "HKGPW3UI.ttf",
            "IowanOldStyle.ttc",
            "Kailasa.ttc",
            "KannadaSangamMN.ttc",
            "Keycaps.ttc",
            "KeycapsPad.ttc",
            "LCD-BQ.ttf",
            "ledfont.ttf",
            "LiquidCrystal.ttf",
            "LockClock.ttf",
            "MalayalamSangamMN.ttc",
            "Mishafi.ttc",
            "MTC-7-Segment.ttf",
            "OriyaSangamMN.ttc",
            "Papyrus.ttc",
            "PhoneKeyCaps.ttf",
            "PhoneKeyCapsThree.ttf",
            "PhoneKeyCapsTwo.ttf",
            "PhonepadTwo.ttf",
            "Savoye.ttc",
            "SinhalaSangamMN.ttc",
            "STHeiti-Light.ttc",
            "STHeiti-Medium.ttc",
            "SukhumvitSetUI.ttc",
            "SuperClarendon.ttc",
            "Symbol.ttf",
            "TeluguSangamMN.ttc",
            "Thonburi.ttc",
            "TimesNewRoman.ttf",
            "TimesNewRomanBold.ttf",
            "TimesNewRomanBoldItalic.ttf",
            "TimesNewRomanItalic.ttf",
            "TrebuchetMS.ttf",
            "TrebuchetMSBold.ttf",
            "TrebuchetMSBoldItalic.ttf",
            "TrebuchetMSItalic.ttf",
            "Verdana.ttf",
            "VerdanaBold.ttf",
            "VerdanaBoldItalic.ttf",
            "VerdanaItalic.ttf",
            "YournameD7CentralNarrow.ttf",
            "YournameD7GeneralHalf.ttf",
            "YournameD7GeneralNarrow.ttf",
            "YournameD7HomeHalf.ttf",
            "Zapfino.ttf",
            "AlNile.ttc",
            "_H_AmericanTypewriter.ttc",
            "_H_Avenir.ttc",
            "_H_AvenirNext.ttc",
            "_H_AvenirNextCondensed.ttc",
            "_H_Baskerville.ttc",
            "_H_Bodoni72-Book-SmallCaps.ttf",
            "_H_Bodoni72-OldStyle.ttc",
            "_H_Bodoni72.ttc",
            "_H_ChalkboardSE.ttc",
            "_H_Cochin.ttc",
            "_H_Didot.ttc",
            "_H_HelveticaNeue.ttc",
            "_H_HelveticaNeueExtras.ttc",
            "_H_HelveticaNeueInterface.ttc",
            "_H_HelveticaNeueLights.ttc",
            "_H_HoeflerText.ttc",
            "_H_Marion.ttc",
            "_H_MarkerFeltThin.ttf",
            "_H_MarkerFeltWide.ttf",
            "_H_Menlo.ttc",
            "_H_Noteworthy.ttc",
            "_H_Optima.ttc",
            "_H_Palatino.ttc",
            "_H_PartyLET.ttf",
            "_H_SnellRoundhand.ttc",
            "_H_ZapfDingbats.ttf"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        设置屏幕为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        监听屏幕旋转方向
        ScreenOrientationListener listener = new ScreenOrientationListener(this);
        listener.enable();

        //设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        iniViews();
        initTTS();
        refreshTime();
        initTimePrompt();
    }

    private void iniViews() {
        num = -1;
        index = -1;
        count = -1;
        dateFontSize = 50;
        timeFontSize = 30.f;
        otherFontSize = 40.f;
        textToSpeech = null;

        textView = findViewById(R.id.txt);
        imageView = findViewById(R.id.background);
        textViewDate = findViewById(R.id.date);
        textViewOther = findViewById(R.id.other);

        textView.setOnClickListener(this);
        textViewDate.setOnClickListener(this);
        textViewOther.setOnClickListener(this);

        textFont();
        textColor();

//        设置粗体
//        TextPaint tp = textView.getPaint();
//        tp.setFakeBoldText(true);
        styleSpan_B = new StyleSpan(Typeface.BOLD);

        settingFontType();
        change();
    }

    // 设置系统菜单为透明
    private void transparency() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT < 16) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    // 事件刷新线程
    private void refreshTime() {
        new Thread() {//每秒更新时间
            @Override
            public void run() {
                while (true) {
                    Message meg = new Message();
                    handler.sendMessage(meg);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());

            textViewDate.setText(Lunar.getYangLiLunar(date, "yyy-MM-dd      EEEE") + "      " + Lunar.getLunar1(date));

            String timeStr = Lunar.getYangLiLunar(date, "HH:mm:ss");
            SpannableString spannableString = new SpannableString(timeStr);

            spannableString.setSpan(sizeSpan01, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan02, 5, timeStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(styleSpan_B, 0, timeStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            textView.setText(spannableString);
            textViewOther.setText(Lunar.getLunar2(date));

            textView.setClickable(true);
            textViewDate.setClickable(true);
            textViewOther.setClickable(true);
        }
    };

    // 更具屏幕配适字体
    public static int adjustFontSize(float screenWidth) {
        if (screenWidth <= 240) {        // 240X320 屏幕
            return 40;
        } else if (screenWidth <= 320) {    // 320X480 屏幕
            return 56;
        } else if (screenWidth <= 480) {    // 480X800 或 480X854 屏幕
            return 96;
        } else if (screenWidth <= 540) {    // 540X960 屏幕
            return 104;
        } else if (screenWidth <= 800) {    // 800X1280 屏幕
            return 120;
        } else if (screenWidth <= 854) {
            return 200;
        } else {                            // 大于 800X1280
            return 240;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt:
                settingFontType();
                break;
            case R.id.other:
                change();
                break;
            case R.id.date:
                textColor();
                break;
            default:
                break;
        }
    }

    //更换背景
    public void change() {
        transparency();
        num += 1;
        if (num == imageIds.length) {
            imageView.setImageURI("");
            imageView.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        } else if (num == imageIds.length + 1) {
            imageView.setBackgroundColor(getResources().getColor(R.color.colorTextWhite));
            num = -1;
        } else {
            num %= imageIds.length;
            Uri uri = Uri.parse("res://com.example.a30797.androidclock/" + imageIds[num]);
            imageView.setImageURI(uri);
        }
        textViewOther.setClickable(false);
    }

    //    设置字体大小
    public void textFont() {
//        float width = getWindowManager().getDefaultDisplay().getWidth();
//        textView.setTextSize(adjustFontSize(width));
        sizeSpan01 = new RelativeSizeSpan(timeFontSize);
        sizeSpan02 = new RelativeSizeSpan(timeFontSize / 3);
    }

    // 更换字体颜色
    public void textColor() {
        transparency();
        count += 1;
        count %= colors.length;

        textView.setTextColor(getResources().getColor(colors[count]));
        textViewDate.setTextColor(getResources().getColor(colors[count]));
        textViewOther.setTextColor(getResources().getColor(colors[count]));

        textViewDate.setClickable(false);
    }

    // 设置字体样式
    public void settingFontType() {
        transparency();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeFontSize);
        textViewDate.setTextSize(dateFontSize);
        textViewOther.setTextSize(otherFontSize);

        index += 1;
        index %= fontTypes.length;
        AssetManager assets = this.getAssets();
        String fontType = "fonts" + File.separator + fontTypes[index];
//        Log.d("-------------",fontType);
        final Typeface font = Typeface.createFromAsset(assets, fontType);
        textView.setTypeface(font);
        textViewDate.setTypeface(font);
        textViewOther.setTypeface(font);
        textView.setClickable(false);
    }

    /**
     * 整点报时
     */
    private void initTimePrompt() {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mTimeReceiver, timeFilter);
    }

    //    此方法一分钟调用一次
    private final android.content.BroadcastReceiver mTimeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);
            if (hour >= 8 && hour <= 22){
                if (min == 0) {
                    startAuto("现在是北京时间" + hour + "点整");
                } else if (min == 30) {
                    startAuto("现在是北京时间" + hour + "点三十分");
                }
//                else {
//                    startAuto("现在是北京时间" + hour + "点" + min + "分");
//                }
            }
        }
    };

    private void initTTS() {
//实例化自带语音对象
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
// Toast.makeText(MainActivity.this,"成功输出语音",
// Toast.LENGTH_SHORT).show();
// Locale loc1=new Locale("us");
// Locale loc2=new Locale("china");
                    textToSpeech.setPitch(1.0f);//方法用来控制音调，值越大声音越尖(女生)，值越小则变成男声,1.0是常规
                    float speechRate = Settings.Secure.getInt(getContentResolver(), Settings.Secure.TTS_DEFAULT_RATE, 100) / 100.0f;
                    textToSpeech.setSpeechRate(speechRate);//用来控制语速
//判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA ||
                            result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA ||
                            result2 == TextToSpeech.LANG_NOT_SUPPORTED);
//                    Log.i("zhh_tts", "US支持否？--》" + a + "\nzh-CN支持否》--》" + b);
                } else {
                    Toast.makeText(MainActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startAuto(String content) {
        //输入中文，若不支持的设备则不会读出来
        textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}