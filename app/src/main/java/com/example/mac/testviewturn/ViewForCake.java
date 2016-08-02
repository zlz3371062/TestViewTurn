package com.example.mac.testviewturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author zlz
 * @time 2016/7/27
 * @version 1.0s
 * @description : android 自定义 蛋糕图
 * 
 **/
public class ViewForCake extends View {
	// view相关
	private Context context;

	private RectF oval;
	private TypedArray tyStyle;// xml属性
	private int width, height;// 宽高
	private int padingwidth;// pading值 同时 也是 圆和右边详情的距离
	private int circleWidth;// 圆的直径
	private int currangle = 0;
	private int zlz = 0;
	// 数据相关
	private List<Data> datalist = new ArrayList<Data>();
	private float total = 0;
	private float[] angle;

	public ViewForCake(Context context) {
		this(context, null);
	}

	public ViewForCake(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewForCake(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		padingwidth = px2dip(context, 20);
		oval = new RectF();
		tyStyle = context.obtainStyledAttributes(attrs, R.styleable.ViewForTurn);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec);
		circleWidth = width / 3 * 2 - padingwidth * 2;
		setMeasuredDimension(width, height);
		oval.left = padingwidth; // 左边
		oval.top = padingwidth; // 上边
		oval.right = circleWidth + padingwidth; // 右边
		oval.bottom = circleWidth + padingwidth; // 下边
		handler.postDelayed(runnable, 500);
	}

	private void init() {

	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("zlz", "onDraw");
		super.onDraw(canvas);
		Paint paint1 = new Paint();
		paint1.setStrokeWidth(30);
		paint1.setStyle(paint1.getStyle().FILL);
		paint1.setStrokeCap(Paint.Cap.ROUND);
		paint1.setColor(Color.WHITE);

		angle = new float[datalist.size()];
		total = 0;
		for (int i = 0; i < datalist.size(); i++) {
			total = total + datalist.get(i).getDetial();
			angle[i] = total;
		}
		for (int i = 0; i < currangle; i++) {

			for (int j = 1; j < angle.length; j++) {
				if (i < angle[0] / total * 360)

				{
					paint1.setColor(Color.RED);
				}

				if (i < angle[j] / total * 360 && i > angle[j - 1] / total * 360) {
					Log.e("zlz", "i:" + i);
					int color = 111111 * j * 2 + j * 1000;
					String strColor = "#" + String.valueOf(color);
					paint1.setColor(Color.parseColor(strColor));

				}

			}
			canvas.drawArc(oval, -90 + i, 3, true, paint1);

		}
		Paint paint = new Paint();
		paint.setStrokeWidth(10);
		paint.setStyle(paint1.getStyle().FILL);
		paint.setColor(Color.WHITE);
		canvas.drawLine(circleWidth / 2 + padingwidth, padingwidth, circleWidth / 2 + padingwidth,
				circleWidth / 2 + padingwidth, paint);
		if (currangle < angle[0] / total * 360) {
			canvas.rotate(currangle, circleWidth / 2 + padingwidth, circleWidth / 2 + padingwidth);
			canvas.drawLine(circleWidth / 2 + padingwidth, padingwidth, circleWidth / 2 + padingwidth,
					circleWidth / 2 + padingwidth, paint);
			canvas.rotate(-currangle, circleWidth / 2 + padingwidth, circleWidth / 2 + padingwidth);
		} else {
			canvas.rotate(angle[0] / total * 360, circleWidth / 2 + padingwidth, circleWidth / 2 + padingwidth);
			canvas.drawLine(circleWidth / 2 + padingwidth, padingwidth, circleWidth / 2 + padingwidth,
					circleWidth / 2 + padingwidth, paint);
			canvas.rotate(-(angle[0] / total * 360), circleWidth / 2 + padingwidth, circleWidth / 2 + padingwidth);

		}

		// 画矩形颜色说明
		for (int i = 0; i < datalist.size(); i++) {
			Rect _rect = new Rect();
			int _leftX = padingwidth * 3 + circleWidth;
			int _leftY = padingwidth + px2dip(context, 20) * i;
			int _bottomX = padingwidth * 3 + circleWidth + px2dip(context, 20);
			int _bottomY = padingwidth + px2dip(context, 20) * i + px2dip(context, 10);
			_rect.set(_leftX, _leftY, _bottomX, _bottomY);
			if (i == 0) {
				paint.setColor(Color.RED);
			} else {
				int color = 111111 * i * 2 + i * 1000;
				String strColor = "#" + String.valueOf(color);
				paint.setColor(Color.parseColor(strColor));

			}
			canvas.drawRect(_rect, paint);
			paint.setTextSize(px2dip(context, 10));
			canvas.drawText(datalist.get(i).getName(), padingwidth * 3 + circleWidth + px2dip(context, 30),
					padingwidth + px2dip(context, 20) * i  +  px2dip(context, 10), paint);

		}
		// 画%号注释
		for (int i = 0; i < datalist.size(); i++) {

			float _turn;
			if (!(i == 0)) {
				_turn = (angle[i] / total * 360 - (angle[i - 1] / total * 360)) / 2 + (angle[i - 1] / total * 360);
			} else {
				_turn = angle[i] / total * 360 / 2;
			}
			if (currangle > _turn) {
				if (i == 0) {
					paint.setColor(Color.RED);
				} else {
					int color = 111111 * i * 2 + i * 1000;
					String strColor = "#" + String.valueOf(color);
					paint.setColor(Color.parseColor(strColor));

				}

				if (i == datalist.size() - 1) {
					float ft = (datalist.get(i).getDetial() / total) * 100;
					int scale = 2;// 设置位数
					int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
					BigDecimal bd = new BigDecimal((double) ft);
					bd = bd.setScale(scale, roundingMode);
					ft = bd.floatValue();
					canvas.rotate(_turn, padingwidth + circleWidth / 2, padingwidth + circleWidth / 2);
					canvas.drawText((int)(datalist.get(i).getDetial()) + " : " + ft + "%",
							padingwidth + circleWidth / 2 - px2dip(context, 15), padingwidth - px2dip(context, 4),
							paint);
					canvas.rotate(-_turn, padingwidth + circleWidth / 2, padingwidth + circleWidth / 2);
				} else {
					float ft = (datalist.get(i).getDetial() / total) * 100;
					int scale = 2;// 设置位数
					int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
					BigDecimal bd = new BigDecimal((double) ft);
					bd = bd.setScale(scale, roundingMode);
					ft = bd.floatValue();
					canvas.rotate(_turn, padingwidth + circleWidth / 2, padingwidth + circleWidth / 2);
					canvas.drawText(datalist.get(i).getDetial() + " : " + ft + "%",
							padingwidth + circleWidth / 2 - px2dip(context, 10), padingwidth - px2dip(context, 4),
							paint);
					canvas.rotate(-_turn, padingwidth + circleWidth / 2, padingwidth + circleWidth / 2);

				}

			}

		}

	}

	/**
	 * @Description:设置蛋糕的分块
	 * @Parameters:上送一个Date()类型的List
	 */
	public void setData(List<Data> pList) {
		List<Data> List = pList;
		for (int i = 0; i < List.size() - 1; i++) {

			for (int j = 0; j < List.size() - i - 1; j++) {
				if (List.get(j).getDetial() < List.get(j + 1).getDetial()) {
					Data temp;
					temp = List.get(j + 1);
					List.remove(j + 1);
					List.add(j + 1, List.get(j));
					List.remove(j);
					List.add(j, temp);

				}

			}

		}
		datalist = List;

	}

	// 转成dp
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int zlz = (int) (pxValue / scale + 0.5f);
		return (int) (pxValue * scale + 0.5f);
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			invalidate();
			currangle = currangle + 1;
			handler.postDelayed(runnable, 10);
			if (currangle > 356) {
				handler.removeCallbacks(runnable);
			}
		}
	};

}
