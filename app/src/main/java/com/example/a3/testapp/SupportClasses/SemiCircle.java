package com.example.a3.testapp.SupportClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.a3.testapp.R;

import java.util.Calendar;
import java.util.Date;

public class SemiCircle extends View {


    private int parentWidth;
    private int parentHeight;
    private Paint paint=new Paint();
    private Paint paint2 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(getResources().getColor(R.color.colorAccent));


        paint2.setColor(Color.GRAY);

        float radius= parentHeight/2;
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        int temp = hour-6;
        float i =15f*temp;

        //RectF rectF = new RectF(0,0,parentWidth/2,parentHeight/2+parentWidth/2);

        canvas.drawCircle(parentWidth/2,parentHeight/2,radius,paint);
        canvas.drawArc(parentWidth/2-radius,0,parentWidth/2+radius,parentHeight,90F+i,180F-(2*i),false,paint2);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        parentHeight=h;
        parentWidth=w;
    }

    public SemiCircle(Context context, Date date){
        super(context);

    }
    public SemiCircle(Context context) {
        super(context);
    }

    public SemiCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SemiCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SemiCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
