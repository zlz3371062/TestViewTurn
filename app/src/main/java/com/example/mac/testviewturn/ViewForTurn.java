package com.example.mac.testviewturn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ViewForTurn extends View {

	private Context context;
	private Paint paint;// 画笔
	private RectF oval; // RectF对象（圆环）
	private int height;//
	private int width;//
	private int circleWidth;//
	private int strokeWidth;// 画笔大小，可以控制圆环的宽度，可以看作小长方形的�?
	private int padingwidth;// 圆环距离边缘的距离，距离越大圆环越小
	private float fTurn = -120;
	private Bitmap bit;
	private float zlz;

	public ViewForTurn(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public ViewForTurn(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public ViewForTurn(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	private void init() {

		padingwidth = px2dip(context, 30f);// 圆环距离边缘的距离，距离越大圆环越小
		strokeWidth = px2dip(context, 30f);// 画
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		oval = new RectF();
		paint.setStrokeWidth(strokeWidth); // 线宽
		paint.setStyle(Paint.Style.STROKE);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		height = View.MeasureSpec.getSize(heightMeasureSpec);
		width = View.MeasureSpec.getSize(widthMeasureSpec);
		if (width >= height) {
			circleWidth = height;
		} else {
			circleWidth = width;
		}

		setMeasuredDimension(circleWidth, circleWidth);
		oval.left = padingwidth; // 左边
		oval.top = padingwidth; // 上边
		oval.right = circleWidth - padingwidth; // 右边
		oval.bottom = circleWidth - padingwidth; // 下边
		// 自动旋转
		// handler.postDelayed(runnable, 500);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 画弧
		paint.setColor(Color.GREEN);
		canvas.drawArc(oval, 148, 1f, false, paint);
		for (int i = 0; i < 240; i++) {
			if (i % 24 == 0) {
				int _intPer = i / 24;
				if (_intPer < 6) {

					paint.setColor(Color.GREEN);

				} else if (_intPer > 5 && _intPer < 8) {
					paint.setColor(Color.MAGENTA);

				} else {

					paint.setColor(Color.WHITE);
					canvas.drawArc(oval, 30, 1f, false, paint);

				}
				canvas.drawArc(oval, 150 + i, 23f, false, paint);

			}

		}
		// 画弧结束
		// 画文字
		Paint _txtPaint = new Paint();
		_txtPaint.setAntiAlias(true);
		_txtPaint.setColor(Color.BLACK);
		_txtPaint.setTextSize(25);
		_txtPaint.setStyle(Paint.Style.FILL);
		canvas.drawText("50", circleWidth / 2 - px2dip(context, 10), padingwidth * 3 / 2 + px2dip(context, 20),
				_txtPaint);
		for (int i = 1; i < 6; i++) {
			int _per = (5 - i) * 10;
			String _strPer = _per + "";
			canvas.rotate(-24, circleWidth / 2, circleWidth / 2);
			canvas.drawText(_strPer, circleWidth / 2 - px2dip(context, 10), padingwidth * 3 / 2 + px2dip(context, 20),
					_txtPaint);
		}
		canvas.rotate(120, circleWidth / 2, circleWidth / 2);
		for (int j = 1; j < 6; j++) {
			int _per = (5 + j) * 10;
			String _strPer = _per + "";
			canvas.rotate(24, circleWidth / 2, circleWidth / 2);
			canvas.drawText(_strPer, circleWidth / 2 - px2dip(context, 10), padingwidth * 3 / 2 + px2dip(context, 20),
					_txtPaint);
		}
		canvas.rotate(-120, circleWidth / 2, circleWidth / 2);
		// 文字结束

		// 画指针中心
		bit = BitmapFactory.decodeResource(getResources(), R.drawable.bz);
		canvas.drawCircle(circleWidth / 2, circleWidth / 2, px2dip(context, 25), _txtPaint);
		// if (turn){
		canvas.rotate(fTurn, circleWidth / 2, circleWidth / 2);
		canvas.drawBitmap(bit, circleWidth / 2 - px2dip(context, 7), circleWidth / 2 - px2dip(context, 90), _txtPaint);
		canvas.rotate(-fTurn, circleWidth / 2, circleWidth / 2);

		// }

		// 画文字方框
		_txtPaint.setColor(Color.GRAY);
		canvas.drawRect(circleWidth / 2 - px2dip(context, 45), circleWidth / 2 + px2dip(context, 40),
				circleWidth / 2 + px2dip(context, 45), circleWidth / 2 + px2dip(context, 60), _txtPaint);
		String zlztxt = "当前值：" + (int) (zlz);
		_txtPaint.setColor(Color.BLACK);
		_txtPaint.setTextSize(30);
		canvas.drawText(zlztxt, circleWidth / 2 - px2dip(context, 40), circleWidth / 2 + px2dip(context, 55),
				_txtPaint);

	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int zlz = (int) (pxValue / scale + 0.5f);
		return (int) (pxValue * scale + 0.5f);
	}

	// Handler handler = new Handler();
	// Runnable runnable = new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// invalidate();
	// handler.postDelayed(runnable, 100);
	// currentDegree = currentDegree + 8;
	// if (currentDegree > zlz) {
	// turn = true;
	// handler.removeCallbacks(runnable);
	// }
	//
	// }
	// };
	//
	public void setValue(int pzlz) {

		zlz = (float) pzlz;
		fTurn = zlz / 100f * 240 - 120;
		Log.e("zlz", fTurn + "");
		invalidate();

	}

}
