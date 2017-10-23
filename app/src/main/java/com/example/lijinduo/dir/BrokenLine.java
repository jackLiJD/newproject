package com.example.lijinduo.dir;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 版权：XXX公司 版权所有
 * 作者：lijinduo
 * 版本：2.0
 * 创建日期：2017/7/25
 * 描述：(重构)
 * 修订历史：
 * 参考链接：
 */
public class BrokenLine extends View {
    private int viewWidth;
    private int viewHeight;
    /**
     * 传递进来的七日收益
     */
    private List<Double> yList = new ArrayList<>();
    /**
     * 取得的比例列表
     */
    private List<Double> yScale = new ArrayList<>();

    private List<String> time = new ArrayList<>();
    /**
     * 每个坐标之间的间距
     */
    private int PointScaleX = 80;

    private int PointScaleY = 50;
    /**
     * 左边距
     */
    private int MarginX = 80;

    /**
     * 七日最大的收益
     */
    private Double MaxMoney = 0.0;
    /**
     * 系统的当前时间
     */
    private long currentTime = 0;
    /**
     * 一天的毫秒数
     */
    private float dayTime = 24 * 60 * 60 * 1000;

    public BrokenLine(Context context) {
        this(context, null);
    }

    public BrokenLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PointScaleX = dip2px(30);

        PointScaleY = dip2px(20);

        MarginX = dip2px(50);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setTextSize(sp2px(11));
        //画Y轴
        canvas.drawLine(MarginX, PointScaleY, MarginX, PointScaleY * 6, paint);
        //画X轴
        canvas.drawLine(MarginX, PointScaleY * 6, MarginX + PointScaleX * 7, PointScaleY * 6, paint);
        paint.setColor(Color.parseColor("#8bd2f7"));
        //画刻度
        for (int i = 0; i < yList.size(); i++) {
            canvas.drawText(yList.get(i) + "", (i + 1) * PointScaleX + MarginX - paint.measureText(yList.get(i) + "") / 2, (float) (PointScaleY * 6 - yScale.get(i) * PointScaleY) - 10, paint);

        }
        //话折线
        paint.setStrokeWidth(3);
        if (yList.size() > 1) {
            Path path = new Path();
            path.moveTo(MarginX + PointScaleX, (float) (PointScaleY * 6 - yScale.get(0) * PointScaleY));
            for (int i = 0; i < yList.size(); i++) {
                path.lineTo(PointScaleX * (i + 1) + MarginX, (float) (PointScaleY * 6 - yScale.get(i) * PointScaleY));
            }
            canvas.drawPath(path, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < yList.size(); i++) {
            canvas.drawCircle((i + 1) * PointScaleX + MarginX,(float)(PointScaleY * 6 - yScale.get(i) * PointScaleY),5,paint);
        }
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(3);
        paint.setTextSize(sp2px(12));
        canvas.drawText("日收益", MarginX - paint.measureText("日收益") - 10, PointScaleY + 10, paint);
        //画日期
        for (int i = 0; i < time.size(); i++) {
            if (i == 0 || i == 3 || i == 6) {
                canvas.drawText(time.get(i), (i + 1) * PointScaleX + MarginX - paint.measureText(time.get(i)) / 2, 6 * PointScaleY + dip2px(20), paint);
            }
        }
        viewWidth=getMeasuredWidth();
        viewHeight=getMeasuredHeight();
    }

    /**
     * 造数据和取比例
     */
    private void initData() {

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    yList.add(3.4);
                    break;
                case 1:
                    yList.add(2.4);
                    break;
                case 2:
                    yList.add(6.0);
                    break;
                case 3:
                    yList.add(5.5);
                    break;
                case 4:
                    yList.add(8.4);
                    break;
                case 5:
                    yList.add(10.4);
                    break;
                case 6:
                    yList.add(3.5);
                    break;
            }
        }

        MaxMoney = Collections.max(yList);

        if (MaxMoney == 0) {
            return;
        }
        currentTime = System.currentTimeMillis();
        for (int i = 0; i < yList.size(); i++) {
            yScale.add(yList.get(i) / MaxMoney * 5);
            time.add(DataChange(currentTime - (yList.size() - i - 1) * dayTime));
        }
    }
    private String DataChange(float time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date curDate = new Date((long) time);//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode ==
                MeasureSpec.AT_MOST){
            setMeasuredDimension(dip2px(300), dip2px(160));
//            setMeasuredDimension(viewWidth, viewHeight);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px( float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private  int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * sp转换成px
     */
    private int sp2px(float spValue){
        float fontScale=getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
}
