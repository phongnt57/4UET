package io.codly.components;

import io.codly.Uetface.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebView;

public class FastWebView extends WebView {

	public FastWebView(Context context) {
		super(context);
	}

	public FastWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FastWebView);

		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.FastWebView_url :
					this.loadUrl(a.getString(attr));
					break;
			}
		}
		a.recycle();
	}

	public FastWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FastWebView);

		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.FastWebView_url :
					this.loadUrl(a.getString(attr));
					break;
			}
		}
		a.recycle();
	}

}
