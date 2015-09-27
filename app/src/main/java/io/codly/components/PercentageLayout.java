package io.codly.components;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.codly.Uetface.R;

public class PercentageLayout extends RelativeLayout {

    private static String TAG = "PercentageLayout_test";

    private float baseWidth;
    private float baseHeight;
    private float screenWidth;
    private float screenHeight;
    private boolean stretchTextsize = true;

    private boolean scaleItSelf = true;
    private Map<View,Boolean>scaledMap = new HashMap<View,Boolean>();

    public PercentageLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public PercentageLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        // TODO Auto-generated constructor stub
    }

    public PercentageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeScreenSize(context);
        // TODO Auto-generated constructor stub
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PercentageLayout);
        baseWidth = a.getDimension(R.styleable.PercentageLayout_base_width, baseWidth);
        Log.d(TAG, "baseWidth = " + baseWidth);
        baseHeight = a.getDimension(R.styleable.PercentageLayout_base_height, baseHeight);
        Log.d(TAG, "baseHeight = " + baseHeight);
        Point screenSize = new Point();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);//getSize(screenSize);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        stretchTextsize = a.getBoolean(R.styleable.PercentageLayout_stretchTextsize, stretchTextsize);
        scaleItSelf = a.getBoolean(R.styleable.PercentageLayout_scaleItSelf, scaleItSelf);
        a.recycle();
        Log.i(TAG,"PercentageLayout--baseWidth:"+baseWidth+"--baseHeight:"+baseHeight+"--screenWidth:"+
                screenWidth+"--screenHeight:"+screenHeight+"--stretchTextsize:"+ stretchTextsize +
                "--scaleItSelf:"+scaleItSelf);
    }

    public void initializeScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenHeight = Math.min(metrics.heightPixels,metrics.widthPixels);
        screenWidth = Math.max(metrics.widthPixels,metrics.heightPixels);
        baseWidth = metrics.density* 320;
        baseHeight = metrics.density * 480;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        scaleDimensions(this);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void scaleDimensions(ViewGroup group){
        int count = group.getChildCount();
        Log.i(TAG,"scaleDimensions-----count="+count);
        if(group instanceof PercentageLayout){
            Log.i(TAG,"scaleDimensions-----scaleChild(group)");
            scaleChild(group);
        }

        for (int i = 0; i < count; i++) {
            View child = group.getChildAt(i);
            Log.i(TAG,"scaleDimensions-----scaleChild(child)");
            scaleChild(child);
        }
    }

    private void scaleChild(View child){
        float widthScale = screenWidth/baseWidth;
        float heightScale = screenHeight/baseHeight;
        Log.i(TAG,"scaleChild---widthScale="+widthScale+"--heightScale="+heightScale);

        Log.i(TAG,"!Boolean.TRUE.equals(scaledMap.get(child)):"+(!Boolean.TRUE.equals(scaledMap.get(child))));

        if(!Boolean.TRUE.equals(scaledMap.get(child))){
            ViewGroup.LayoutParams st = child.getLayoutParams();
            if(st instanceof ViewGroup.MarginLayoutParams){
                MarginLayoutParams margin = (MarginLayoutParams)st;
                margin.leftMargin*=widthScale;
                margin.rightMargin*=widthScale;
                margin.topMargin*=heightScale;
                margin.bottomMargin*=heightScale;
                Log.i(TAG,"scaleChild---margin.leftMargin="+margin.leftMargin+"--margin.rightMargin="+margin.rightMargin
                        +"--margin.topMargin="+margin.topMargin+"--margin.bottomMargin="+margin.bottomMargin);
            }
            boolean constraintRatio = false;
            if(st instanceof LayoutParams){
                constraintRatio = ((LayoutParams)st).constraintRatio;
            }
            Log.i(TAG,"scaleChild--origin---st.width="+st.width+"--st.height="+st.height);
            if(st.width>0){
                if(child!=this||scaleItSelf){
                    if(!constraintRatio){
                        st.width=(int) (st.width*widthScale);
                        Log.i(TAG,"scaleChild---st.width="+st.width);
                    }else{
                        st.width=(int) (st.width*heightScale);
                        Log.i(TAG,"scaleChild--else---st.width="+st.width);
                    }
                }
            }

            if(st.height>0){
                if(child!=this||scaleItSelf){
                    st.height*=heightScale;
                    Log.i(TAG,"scaleChild---st.height="+st.height);
                }
            }

            Drawable background = child.getBackground();
            if(background == null||!(background instanceof NinePatchDrawable)){
                Log.i(TAG,"scaleChild---child.getPadding--"+(int)(child.getPaddingLeft())+","+(int)(child.getPaddingTop())+","
                        +(int)(child.getPaddingRight())+","+(int)(child.getPaddingBottom()));

                child.setPadding((int)(child.getPaddingLeft()*widthScale), (int)(child.getPaddingTop()*heightScale),
                        (int)(child.getPaddingRight()*widthScale), (int)(child.getPaddingBottom()*heightScale));
                Log.i(TAG,"scaleChild---child.setPadding--"+(int)(child.getPaddingLeft()*widthScale)+","+(int)(child.getPaddingTop()*heightScale)+","
                        +(int)(child.getPaddingRight()*widthScale)+","+(int)(child.getPaddingBottom()*heightScale));
            }

            if(stretchTextsize && heightScale!=1 && child instanceof TextView){
                if(screenHeight <= 320){
                    heightScale = widthScale;
                }
                TextView t = (TextView)child;
                float size = t.getTextSize();
                t.setTextSize(TypedValue.COMPLEX_UNIT_PX, size*heightScale);
                Log.i(TAG,"scaleChild---t.setTextSize--size="+size+"size*heightScale="+size*heightScale);
                if(t instanceof AutoCompleteTextView){
                    AutoCompleteTextView at = (AutoCompleteTextView)t;
//            			at.setDropDownHeight((int) (at.getDropDownHeight()*heightScale));
                }

            }
            scaledMap.put(child, Boolean.TRUE);
        }

        if((child instanceof ViewGroup) && !(child instanceof PercentageLayout)){
            Log.i(TAG,"(child instanceof ViewGroup) && !(child instanceof PercentageLayout):"+
                    ((child instanceof ViewGroup) && !(child instanceof PercentageLayout)));
            scaleDimensions((ViewGroup) child);
        }
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public float getBaseWidth() {
        return baseWidth;
    }

    public void setBaseWidth(float baseWidth) {
        this.baseWidth = baseWidth;
    }

    public float getBaseHeight() {
        return baseHeight;
    }

    public void setBaseHeight(float baseHeight) {
        this.baseHeight = baseHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }


    public static class LayoutParams extends RelativeLayout.LayoutParams{

        private boolean constraintRatio = false;

        private boolean stretchImage = true;

        private boolean stretchTextsize = true;

        private boolean resizeByHeight = true;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            // TODO PercentageLayout.LayoutParams(Context c, AttributeSet attrs)
            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.PercentageLayout);
            constraintRatio = a.getBoolean(R.styleable.PercentageLayout_constraintRatio, constraintRatio);
            stretchImage = a.getBoolean(R.styleable.PercentageLayout_stretchImage, stretchImage);
            stretchTextsize = a.getBoolean(R.styleable.PercentageLayout_stretchTextsize, stretchTextsize);
            resizeByHeight = a.getBoolean(R.styleable.PercentageLayout_resizeByHeight, resizeByHeight);
            a.recycle();

            Log.i(TAG,"LayoutParams--constraintRatio:"+ constraintRatio +"--stretchImage:"+ stretchImage
                    +"--stretchTextsize:"+ stretchTextsize +"--resizeByHeight:"+resizeByHeight);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

        public LayoutParams(MarginLayoutParams arg0) {
            super(arg0);
        }
    }
}