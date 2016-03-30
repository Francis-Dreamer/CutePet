package com.dream.cutepet.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapUtil extends Activity {

	/**
	 * 剪切头像并压缩成圆形头像
	 * 
	 * @param bitmap
	 * @return
	 */
	@SuppressLint("ResourceAsColor")
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;

		if (width <= height) {// 判断是以宽还是以高为基准进行裁剪
			roundPx = width / 2;// 圆心坐标
			top = 0;// 图矩形的top点
			bottom = width;// 图矩形的bottom点
			left = 0;// 图矩形的left点
			right = width;// 图矩形的right点
			height = width;
			dst_left = 0;// 圆矩形的left点
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();

		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);// 定义图片的矩形

		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);// 定义屏幕上图片的矩形

		final RectF rectF = new RectF(dst_left, dst_top, dst_right, dst_bottom);// 定义圆矩形

		paint.setAntiAlias(true);// 设置抗锯齿

		canvas.drawARGB(0, 0, 0, 0);// 清屏
		
		paint.setColor(0xff424242);// 设置颜色

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 以roudPx为圆心画一个圆

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置图像合成模式
		canvas.drawBitmap(bitmap, src, dst, paint);// 在画布上画图
		// 第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方

		// 得到图片原始的高宽
		int roundHeight = output.getHeight();
		int roundWidth = output.getWidth();
		// 设定图片新的高宽
		int newHeight = 80;
		int newWidth = 80;
		// 计算缩放因子
		float heightScale = ((float) newHeight) / roundHeight;
		float widthScale = ((float) newWidth) / roundWidth;
		// 新建立矩阵
		Matrix matrix = new Matrix();
		matrix.postScale(heightScale, widthScale);
		// 设置图片的旋转角度
		// matrix.postRotate(-30);
		// 设置图片的倾斜
		// matrix.postSkew(0.1f, 0.1f);
		// 将图片大小压缩
		// 压缩后图片的宽和高以及kB大小均会变化
		Bitmap newBitmap = Bitmap.createBitmap(output, 0, 0, roundWidth,
				roundHeight, matrix, true);
		return newBitmap;
	}

}
