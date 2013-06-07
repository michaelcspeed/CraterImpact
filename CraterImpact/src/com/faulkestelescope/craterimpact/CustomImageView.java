package com.faulkestelescope.craterimpact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
	private float angleRotation;

	public CustomImageView(Context context) {
		super(context);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setAngleRotation(float angleRotation) {
		this.angleRotation = angleRotation;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Rect clipBounds = canvas.getClipBounds();
		canvas.save();
		canvas.rotate(angleRotation, clipBounds.left, clipBounds.bottom);
		super.onDraw(canvas);
		canvas.restore();
	}
}