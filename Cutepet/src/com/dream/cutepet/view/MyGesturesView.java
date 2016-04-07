package com.dream.cutepet.view;

import java.util.ArrayList;
import java.util.List;

import com.dream.cutepet.model.PointModel;
import com.dream.cutepet.util.GesturesUtil;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MyGesturesView extends View {
	Paint paint_cicle;
	Paint paint_line;
	Paint paint_text;

	List<PointModel> point;
	public static List<Integer> point_num = new ArrayList<Integer>();

	// 获取屏幕长宽
	WindowManager wm = (WindowManager) getContext().getSystemService(
			Context.WINDOW_SERVICE);
	@SuppressWarnings("deprecation")
	public int width = wm.getDefaultDisplay().getWidth();
	@SuppressWarnings("deprecation")
	public int height = wm.getDefaultDisplay().getHeight();
	// 圆的半径
	float r = (float) (width / 9.4);
	// 定义边距
	float margin_v = r;
	float margin_h = r;
	float padding_v = 0.7f * r;
	float padding_h = 0.7f * r;

	float startX, startY, stopX, stopY;
	float startX_new, startY_new, stopX_new, stopY_new;

	boolean flog = false;

	public MyGesturesView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 设置圆的画笔
		paint_cicle = new Paint();
		paint_cicle.setStrokeWidth(5);
		// 设置绘制风格
		paint_cicle.setStyle(Paint.Style.STROKE);
		paint_cicle.setColor(getResources().getColor(android.R.color.black));
		// 设置是否抗锯齿
		paint_cicle.setAntiAlias(true);

		paint_line = new Paint();
		paint_line.setStrokeWidth(5);
		paint_line.setColor(getResources().getColor(
				android.R.color.holo_green_light));
		// 设置是否抗锯齿
		paint_line.setAntiAlias(true);

		paint_text = new Paint();
		paint_text.setColor(getResources().getColor(android.R.color.black));
		paint_text.setStrokeWidth(10);
		paint_text.setTextAlign(Paint.Align.CENTER);
		paint_text.setTextSize(30);
		paint_text.setAntiAlias(true);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// 每次绘制时，都重新new一个圆的集合对象，防止数据重复添加
		point = new ArrayList<PointModel>();
		// 纵向的添加标记量
		int count_y = 0;
		// 横向的添加表计量
		int count_x = 0;
		// 循环绘制9个圆
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0) {
				count_y++;
				count_x = 0;
			} else {
				count_x++;
			}
			float point_x = margin_h + r + (2 * r + padding_h) * count_x;
			float point_y = margin_v + r + (2 * r + padding_v) * count_y;

			canvas.drawCircle(point_x, point_y, r, paint_cicle);
			canvas.drawCircle(point_x, point_y, 6, new Paint());

			// 每次绘制一个圆，则向集合中添加该圆的属性，圆的位置对应（i+1）的值
			PointModel model = new PointModel(point_x, point_y, r);
			point.add(model);
		}

		// 线条一：循环绘制移动到圆的线，并连接
		for (int m = 0; m < point_num.size() - 1; m++) {
			startX_new = point.get(point_num.get(m)).getPoint_x();
			startY_new = point.get(point_num.get(m)).getPoint_y();
			stopX_new = point.get(point_num.get(m + 1)).getPoint_x();
			stopY_new = point.get(point_num.get(m + 1)).getPoint_y();
			canvas.drawLine(startX_new, startY_new, stopX_new, stopY_new,
					paint_line);
		}

		// 线条二：绘制每次最后一次经过的圆的圆心到鼠标移动的位置的线条
		if (flog) {
			if (!point_num.isEmpty()) {
				canvas.drawLine(point.get(point_num.get(point_num.size() - 1))
						.getPoint_x(),
						point.get(point_num.get(point_num.size() - 1))
								.getPoint_y(), stopX, stopY, paint_line);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int num;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			point_num.clear();
			// 获取判断是否经过圆的方法返回的值
			num = GesturesUtil.PassCircle(x, y, point);
			if (num != -1) {
				// 如果该值不为-1，则返回的是经过圆的位置值
				point_num.add(num);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			// 获取判断是否经过圆的方法返回的值
			num = GesturesUtil.PassCircle(x, y, point);
			if (num != -1) {
				// 如果该值不为-1，则返回的是经过圆的位置值
				point_num.add(num);
			}
			flog = false;
			invalidate();
			break;

		case MotionEvent.ACTION_MOVE:
			// 获取判断是否经过圆的方法返回的值
			num = GesturesUtil.PassCircle(x, y, point);
			if (num != -1) {
				// 如果该值不为-1，则返回的是经过圆的位置值
				if (!GesturesUtil.hasNumber(num, point_num)) {
					point_num.add(num);
					invalidate();
				}
			}
			flog = true;
			stopX = x;
			stopY = y;
			invalidate();
			break;

		default:
			break;
		}

		return true;
	}

}
