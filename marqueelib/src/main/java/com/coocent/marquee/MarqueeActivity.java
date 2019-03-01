package com.coocent.marquee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import static com.coocent.marquee.MarqueeConstant.MARQUEE_COLOR_NAME;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_COLOR_NAME_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_ENABLE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_ENABLE_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH_VALUE;
import com.coocent.marquee.R;

public class MarqueeActivity extends AppCompatActivity implements MarqueeRecyclerAdapter.OnListener{

    private MarqueeSweepGradientView sweepView;
    private RelativeLayout mainRelLayout;
    private MarqueeSwitchButton marqueeSwitch,marqueeSwitchButton2_icon;
    private MarqueeSwitchButton2 marqueeSwitchButton2_bg;
    private MarqueeSeekBarView radianView, widthView, speedView;
    private TextView radianTv, widthTv, speedTv;
    private RelativeLayout nav;
    private ImageView menuBtn;
    private TextView title_main_text;
    private TextView radianIcon, widthIcon, speedIcon;
    private TextView pickerTitleTv;
    private RecyclerView marqueeRecView;
    private MarqueeRecyclerAdapter marqueeRecyclerAdapter;
    private ArrayList<MarqueeEntity> marqueeLists;
    private SharedPreferences sp;
    private RelativeLayout contentRelLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MarqueeSystemBarUtil.init(this, true);

        try{
            setContentView(R.layout.marquee_activity_marquee);
        }catch (Exception e){
            Log.d("测试#MarqueeActivity","onCreate#"+e.getMessage());
            finish();
        }


        sp = getSharedPreferences("setting_preference", Context.MODE_PRIVATE);
//        MarqueeSystemBarUtil.init(this, (SystemUtil.mCurrentTheme).getNavItem() == 0);

        initView();
        initTheme();
        initStatus();
    }

    private void initStatus() {
        boolean marqueeEnable = sp.getBoolean(MARQUEE_ENABLE, MARQUEE_ENABLE_VALUE);
//        marqueeSwitch.setChecked(marqueeEnable);

        marqueeSwitch.setIsShow(marqueeEnable);
        marqueeSwitch.setOnBitmap(MarqueeThemeUtil.getTotalSwitch());

        marqueeSwitchButton2_bg.setIsShow(marqueeEnable);
        marqueeSwitchButton2_icon.setIsShow(marqueeEnable);

        radianView.setEnable(marqueeEnable);
        radianView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),marqueeEnable);
        widthView.setEnable(marqueeEnable);
        widthView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),marqueeEnable);
        speedView.setEnable(marqueeEnable);
        speedView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),marqueeEnable);
        sweepView.setVisibility(marqueeEnable?View.VISIBLE:View.GONE);
        marqueeRecView.setEnabled(marqueeEnable);
        marqueeRecyclerAdapter.setOnListener(marqueeEnable?this:null);
        marqueeRecyclerAdapter.notifyItemChanged(marqueeLists.size());
    }

    /**
     * 主题化
     */
    private void initTheme() {
//        Window window = getWindow();
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        if (t.getNavItem() == 0){
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        } else {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        }

        if(MarqueeThemeUtil.getColorPrimaryFromOtherApp()!=0){
            mainRelLayout.setBackgroundColor(MarqueeThemeUtil.getColorPrimaryFromOtherApp());
            nav.setBackgroundColor(MarqueeThemeUtil.getColorPrimaryFromOtherApp());
        }else{
            int themeColor=MarqueeColorUtils.hex2Int(MarqueeThemeUtil.getThemeColor());
            mainRelLayout.setBackgroundColor(themeColor);
            nav.setBackgroundColor(themeColor);
        }
        contentRelLayout.setBackgroundColor(Color.parseColor("#232324"));
        menuBtn.setImageResource(R.drawable.btn_top_return_white);
        title_main_text.setTextColor(Color.parseColor("#ffffff"));
//        mainRelLayout.setBackgroundColor(t.getToolBarColor());
//        nav.setBackgroundColor(t.getToolBarColor());
//        contentRelLayout.setBackgroundColor(t.getMainBg());
//        menuBtn.setImageResource(t.getNavItem() == 0 ? R.drawable.btn_top_return_black : R.drawable.btn_top_return_white);
//        title_main_text.setTextColor(t.getTitleText());

        int colorNumber = Color.parseColor("#FFFFFF");
//        int colorNumber = t.getNavItem() == 0 ? Color.parseColor("#000000") : Color.parseColor("#FFFFFF");
        radianIcon.setTextColor(colorNumber);
        widthIcon.setTextColor(colorNumber);
        speedIcon.setTextColor(colorNumber);
        radianTv.setTextColor(colorNumber);
        widthTv.setTextColor(colorNumber);
        speedTv.setTextColor(colorNumber);
        pickerTitleTv.setTextColor(colorNumber);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            radianIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.marquee_ic_revolving_lamp_radian), null, null);
            widthIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.marquee_ic_revolving_lamp_width), null, null);
            speedIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.marquee_ic_revolving_lamp_speed), null, null);
        }

//        radianIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(t.getNavItem() == 0 ? R.drawable.ic_revolving_lamp_radian_black : R.drawable.marquee_ic_revolving_lamp_radian), null, null);
//        widthIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(t.getNavItem() == 0 ? R.drawable.ic_revolving_lamp_width_black : R.drawable.marquee_ic_revolving_lamp_width), null, null);
//        speedIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, getResources().getDrawable(t.getNavItem() == 0 ? R.drawable.ic_revolving_lamp_speed_black : R.drawable.marquee_ic_revolving_lamp_speed), null, null);

        radianView.setEnable(true);
        radianView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),true);
        widthView.setEnable(true);
        widthView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),true);
        speedView.setEnable(true);
        speedView.setEqEnable(MarqueeThemeUtil.getEqSeekBarBg(),true);

//        int[][] states = new int[2][];
//        states[0] = new int[]{-android.R.attr.state_checked};
//        states[1] = new int[]{android.R.attr.state_enabled};
//        ColorStateList colorStateList;
//        colorStateList = new ColorStateList(states,new int[]{Color.WHITE,Color.WHITE});
//        marqueeSwitch.setThumbTintList(colorStateList);
//        colorStateList = new ColorStateList(states,new int[]{Color.parseColor("#6a6a6a"),Color.parseColor("#"+t.getThemeColor())});
////        colorStateList = new ColorStateList(states,new int[]{Color.parseColor("#6a6a6a"),t.getBrightColor()});
//        marqueeSwitch.setTrackTintList(colorStateList);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            addRelLayout.setBackgroundResource(t.getBtnRipple());
////        }
    }

    private void initView() {
        mainRelLayout = findViewById(R.id.mainRelLayout);
        contentRelLayout = findViewById(R.id.contentRelLayout);
        nav = findViewById(R.id.nav);
        menuBtn = (ImageView) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(menuBtnListener);
        title_main_text = findViewById(R.id.title_main_text);
        sweepView = findViewById(R.id.sweepView);
        marqueeLists = MarqueeLoader.getInstance(this).getData();
        sweepView.post(new Runnable() {
            @Override
            public void run() {
                setSweepViewColors();
            }
        });

        marqueeSwitch = findViewById(R.id.marqueeSwitch);
        marqueeSwitchButton2_icon = findViewById(R.id.marqueeSwitch2_icon);
        marqueeSwitchButton2_bg = findViewById(R.id.marqueeSwitch2_bg);

        //两种switch按钮版本，如果别的App有自己的主题色，则调用switch2,3
        if(MarqueeThemeUtil.getColorPrimaryFromOtherApp()==0){
            marqueeSwitch.setVisibility(View.VISIBLE);
            marqueeSwitchButton2_icon.setVisibility(View.GONE);
            marqueeSwitchButton2_bg.setVisibility(View.GONE);
        }else{
            marqueeSwitch.setVisibility(View.GONE);
            marqueeSwitchButton2_icon.setVisibility(View.VISIBLE);
            marqueeSwitchButton2_bg.setVisibility(View.VISIBLE);
        }

//        marqueeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putBoolean(MARQUEE_ENABLE, isChecked);
//                editor.apply();
//                initStatus();
//            }
//        });
        marqueeSwitch.setOnchangeListener(new MarqueeSwitchButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(MARQUEE_ENABLE, CheckState);
                editor.apply();
                initStatus();
            }
        });

        marqueeSwitchButton2_icon.setOnchangeListener(new MarqueeSwitchButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean CheckState) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(MARQUEE_ENABLE, CheckState);
                editor.apply();
                initStatus();
            }
        });


        radianIcon = findViewById(R.id.radianIcon);
        widthIcon = findViewById(R.id.widthIcon);
        speedIcon = findViewById(R.id.speedIcon);
        radianTv = findViewById(R.id.radianTv);
        widthTv = findViewById(R.id.widthTv);
        speedTv = findViewById(R.id.speedTv);

        radianView = findViewById(R.id.radianView);
        widthView = findViewById(R.id.widthView);
        speedView = findViewById(R.id.speedView);

        int radianValue = sp.getInt(MARQUEE_RADIAN,MARQUEE_RADIAN_VALUE);
        int widthValue = sp.getInt(MARQUEE_WIDTH,MARQUEE_WIDTH_VALUE);
        int speedValue = sp.getInt(MARQUEE_SPEED, MARQUEE_SPEED_VALUE);
        radianTv.setText(String.valueOf(radianValue));
        widthTv.setText(String.valueOf(widthValue));
        speedTv.setText(String.valueOf(speedValue));

        sweepView.setRadius(radianValue);
        sweepView.setWidth(widthValue);
        sweepView.setBaseRotate(speedValue);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            View radianRelLayout = findViewById(R.id.radianRelLayout);
//            radianRelLayout.setVisibility(View.GONE);
//        }
        radianView.setEnable(true);
        radianView.setEqEnable(MarqueeThemeUtil.getRadianViewBg(), true);
        radianView.setMaxValue(60);
        radianView.setCurrentValue(radianValue);
        radianView.setOnSeekBarChangeListener(new MarqueeSeekBarView.OnSeekBarChangeListener() {
            @Override
            public void onSeekBarChange(int dbValue, boolean fromUser, boolean isVib) {
                sweepView.setRadius(dbValue);
                radianTv.setText(String.valueOf(dbValue));
            }
        });

        widthView.setEnable(true);
        widthView.setEqEnable(MarqueeThemeUtil.getWidthViewBg(), true);
        widthView.setMaxValue(10);
        widthView.setCurrentValue(widthValue);
        widthView.setOnSeekBarChangeListener(new MarqueeSeekBarView.OnSeekBarChangeListener() {
            @Override
            public void onSeekBarChange(int dbValue, boolean fromUser, boolean isVib) {
                sweepView.setWidth(dbValue);
                widthTv.setText(String.valueOf(dbValue));
            }
        });

        speedView.setEnable(true);
        speedView.setEqEnable(MarqueeThemeUtil.getSpeedViewBg(), true);
        speedView.setMaxValue(15);
        speedView.setCurrentValue(speedValue);
        speedView.setOnSeekBarChangeListener(new MarqueeSeekBarView.OnSeekBarChangeListener() {
            @Override
            public void onSeekBarChange(int dbValue, boolean fromUser, boolean isVib) {
                sweepView.setBaseRotate(dbValue);
                speedTv.setText(String.valueOf(dbValue));
            }
        });

        pickerTitleTv = findViewById(R.id.pickerTitleTv);

        marqueeRecView = findViewById(R.id.marqueeRecView);
        marqueeRecView.setHasFixedSize(true);
        marqueeRecView.setLayoutManager(new LinearLayoutManager(MarqueeActivity.this));
        marqueeRecyclerAdapter = new MarqueeRecyclerAdapter(this,marqueeLists);
        marqueeRecView.setAdapter(marqueeRecyclerAdapter);
    }

    private void setSweepViewColors() {
        int[] colors = new int[marqueeLists.size()+1];//保证头尾颜色值一样
        for (int i=0;i<colors.length;i++){
            if (i == colors.length -1){
                colors[i] = colors[0];
            } else {
                colors[i] = Color.parseColor(marqueeLists.get(i).getColor());
            }
        }
        if (sweepView != null)
            sweepView.setColors(colors);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }

    private View.OnClickListener menuBtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

//    private ArrayList<MarqueeEntity> getData(){
//        File tmpFile = new File(getFilesDir()+"/"+"marquee.xml");
//        ArrayList<MarqueeEntity> marqueeEntities = new ArrayList<>();
//        if (tmpFile.exists()){//文件存在
//            DocumentBuilder builder;
//            DocumentBuilderFactory factory = null;
//            Document document = null;
//            InputStream inputStream = null;
//            factory = DocumentBuilderFactory.newInstance();
//            try {
//                builder = factory.newDocumentBuilder();
//                inputStream = new FileInputStream(tmpFile);
//                document = builder.parse(inputStream);
//                Element root = document.getDocumentElement();
//                NodeList nodeList = root.getElementsByTagName("marquee");
//                MarqueeEntity marqueeEntity;
//                for (int i=0;i<nodeList.getLength();i++){
//                    marqueeEntity = new MarqueeEntity();
//                    Element marqueeElement = (Element)(nodeList.item(i));
//                    Element nameElement = (Element)marqueeElement.getElementsByTagName("name").item(0);
//                    marqueeEntity.setName(nameElement.getFirstChild().getNodeValue());
//                    Element colorElement = (Element)marqueeElement.getElementsByTagName("color").item(0);
//                    marqueeEntity.setColor(colorElement.getFirstChild().getNodeValue());
//                    marqueeEntities.add(marqueeEntity);
//                }
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            }catch (IOException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            }
//        } else {
//            //初始化两个Item
//            MarqueeEntity marqueeEntity;
//            ArrayList<MarqueeEntity> baseLists = new ArrayList<>();
//
//            marqueeEntity = new MarqueeEntity();
//            Theme t = SystemUtil.mCurrentTheme;
//            String strColor = String.format("#%08X", t.getBrightColor());
//            marqueeEntity.setName(strColor);
//            marqueeEntity.setColor(strColor);
//            baseLists.add(marqueeEntity);
//
//            marqueeEntity = new MarqueeEntity();
//            marqueeEntity.setName("#FFFFFFFF");
//            marqueeEntity.setColor("#FFFFFFFF");
//            baseLists.add(marqueeEntity);
//
//            setData(baseLists);
//            marqueeEntities = getData();
//        }
//        return marqueeEntities;
//    }

//    private void setData(ArrayList<MarqueeEntity> marqueeLists){
//        File xmlFile = new File(getFilesDir()+"/"+"marquee.xml");
//        try {
//            if (xmlFile.exists()){
//                xmlFile.delete();
//            }
//            xmlFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(xmlFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        XmlSerializer xmlSerializer = Xml.newSerializer();
//        try {
//            xmlSerializer.setOutput(fileOutputStream,"UTF-8");
//            xmlSerializer.startDocument(null,true);
//            xmlSerializer.startTag(null, "marquees");
//            for (int i=0;i<marqueeLists.size();i++){
//                xmlSerializer.startTag(null, "marquee");
//                xmlSerializer.startTag(null, "name");
//                xmlSerializer.text(marqueeLists.get(i).getName());
//                xmlSerializer.endTag(null, "name");
//                xmlSerializer.startTag(null, "color");
//                xmlSerializer.text(marqueeLists.get(i).getColor());
//                xmlSerializer.endTag(null, "color");
//                xmlSerializer.endTag(null, "marquee");
//            }
//            xmlSerializer.endTag(null, "marquees");
//            xmlSerializer.endDocument();
//            xmlSerializer.flush();
//            if (fileOutputStream != null)
//                fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(MARQUEE_ENABLE, marqueeSwitch.isChecked());
        editor.putInt(MARQUEE_RADIAN, radianView.getValue());
        editor.putInt(MARQUEE_WIDTH, widthView.getValue());
        editor.putInt(MARQUEE_SPEED, speedView.getValue());
        editor.apply();
        if (marqueeLists != null) {
            MarqueeLoader.getInstance(this).setData(marqueeLists);
        }
        View v = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onColorPickerClick(final int position) {
        MarqueeColorPickerDialog marqueeColorPickerDialog = new MarqueeColorPickerDialog(MarqueeActivity.this,Color.parseColor(marqueeLists.get(position).getColor()));
        marqueeColorPickerDialog.setOnColorChangedListener(new MarqueeColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                String strColor = String.format("#%08X", color);
                marqueeLists.get(position).setColor(strColor);
                marqueeRecyclerAdapter.notifyItemChanged(position);
                setSweepViewColors();
            }
        });
        marqueeColorPickerDialog.setAlphaSliderVisible(true);
        marqueeColorPickerDialog.setHexValueEnabled(true);
        marqueeColorPickerDialog.show();
    }

    @Override
    public void onAddClick(final int position) {

        String strColor = "#"+MarqueeThemeUtil.getThemeColor();

//        String strColor = String.format("#%08X", t.getBrightColor());
        MarqueeColorPickerDialog marqueeColorPickerDialog = new MarqueeColorPickerDialog(MarqueeActivity.this,Color.parseColor(strColor));
        marqueeColorPickerDialog.setOnColorChangedListener(new MarqueeColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                String strColor = String.format("#%08X", color);
                MarqueeEntity marqueeEntity = new MarqueeEntity();
                int num = sp.getInt(MARQUEE_COLOR_NAME, MARQUEE_COLOR_NAME_VALUE) + 1;
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(MARQUEE_COLOR_NAME, num);
                editor.apply();
                marqueeEntity.setName(getResources().getString(R.string.marquee_color_name) + " " + num);
//                marqueeEntity.setName(strColor);
                marqueeEntity.setColor(strColor);
                marqueeLists.add(marqueeEntity);
                setSweepViewColors();
                marqueeRecyclerAdapter.notifyItemChanged(position);
                marqueeRecyclerAdapter.notifyItemChanged(marqueeLists.size() - 1);
                marqueeRecView.scrollToPosition(marqueeLists.size() - 1);
            }
        });
        marqueeColorPickerDialog.setAlphaSliderVisible(true);
        marqueeColorPickerDialog.setHexValueEnabled(true);
        marqueeColorPickerDialog.show();
    }

    @Override
    public void onTextClick(int position) {
        marqueeRecyclerAdapter.notifyItemChanged(position);
    }

    @Override
    public void onEditClick(View v, int position) {
        String text = ((EditText)v).getText().toString();
        if (!text.isEmpty()){
            marqueeLists.get(position).setName(text);
        }
        try {
            marqueeRecyclerAdapter.notifyItemChanged(position);
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDeleteClick(final int position) {
        Snackbar snackbar = Snackbar.make(marqueeRecView,getString(R.string.marquee_delete_item),Snackbar.LENGTH_SHORT);
        snackbar .setAction(getString(R.string.marquee_dialog_btn_play_not_install), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        marqueeLists.remove(position);
                        setSweepViewColors();
                        marqueeRecyclerAdapter.notifyDataSetChanged();
                    }
                });

        snackbar.setActionTextColor(Color.parseColor("#"+MarqueeThemeUtil.getThemeColor()));
        View view = snackbar.getView();
        TextView snackBarTv = view.findViewById(R.id.snackbar_text);
        int colorNumber = Color.parseColor("#FFFFFF");
//        int colorNumber = t.getNavItem() == 0 ? Color.parseColor("#000000") : Color.parseColor("#FFFFFF");
        snackBarTv.setTextColor(colorNumber);
        view.setBackgroundColor(Color.parseColor("#323233"));
//        view.setBackgroundColor(t.getToolBarColor());
        snackbar.show();
    }
}
