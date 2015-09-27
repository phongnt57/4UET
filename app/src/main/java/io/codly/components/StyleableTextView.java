package io.codly.components;

import io.codly.Uetface.R;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Abd-Allah on 11/06/13.
 */
public class StyleableTextView extends TextView {

	public StyleableTextView(Context context) {
		super(context);
	}

	public StyleableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.StyleableComponent);

		final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.StyleableComponent_fontName:
                    try {
                        String fontPath = a.getString(attr);
                        AssetManager am = context.getAssets();
                        String[] filename = am.list(fontPath);
                        if(filename!=null && filename.length>0 ){
                            fontPath = "font/"+ filename[0];
                            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                                    fontPath);
                            this.setTypeface(tf);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        a.recycle();

	}

	public StyleableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.StyleableComponent);

		final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.StyleableComponent_fontName:
                    try {
                        String fontPath = a.getString(attr);
                        AssetManager am = context.getAssets();
                        String[] filename = am.list(fontPath);
                        if(filename!=null && filename.length>0 ){
                            fontPath = "font/"+ filename[0];
                            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                                    fontPath);
                            this.setTypeface(tf);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        a.recycle();
	}

	@Override
	public boolean isInEditMode() {
		return true;
	}
}
