package com.example.tourism.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 自定义圆形图像视图类
 *
 */
public class CircleImageView extends AppCompatImageView {
    private float width;    //宽
    private float height;   //高
    private float radius;   //半径
    private Paint paint;    //
    private Matrix matrix;  //缩放矩阵

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿
        matrix = new Matrix();      //初始化缩放矩阵
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }

    /**
     * 进行绘图（绘制圆形）传入宽，高，以及半径
     * @param canvas 绘图（不是Android所特有的）
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }
        /**
         * instanceof 是 Java 的一个二元操作符
         * 表示测试所指向的对象是否一致 是则返回true，否则返回false
         */
        if (drawable instanceof BitmapDrawable) {
            paint.setShader(initBitmapShader((BitmapDrawable) drawable)); //将着色器设置给画笔
            canvas.drawCircle(width / 2, height / 2, radius, paint); //使用画笔在画布上画圆
            return;
        }
        super.onDraw(canvas);
    }

    /**
     * 获取ImageView中资源图片的Bitmap(位图)，利用Bitmap(位图)初始化图片着色器，
     * 通过缩放矩阵将元资源图片缩放到铺满整个绘制区域，避免边界填充。
     *
     * 1.CLAMP：如果渲染器超出原始边界范围，恢复至范围内边缘染色。
     * 2.REPEAT：横向和纵向的重复渲染器图片，平铺。
     * 3.MIRROR：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像的方式平铺。
     * @param drawable
     * @return
     */
    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        //通过位图创建着色器
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        //将资源图片铺满整个区域
        matrix.setScale(scale, scale);
        //设置矩阵
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }

}
