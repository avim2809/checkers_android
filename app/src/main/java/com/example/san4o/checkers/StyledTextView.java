package com.example.san4o.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class StyledTextView extends TextView {

    public StyledTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //____________________________________________
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(15);
        paint.setColor(Color.YELLOW);

        canvas.drawLine(0,0,getWidth(),0,paint);
        canvas.drawLine(0,0,0,getHeight(),paint);
        canvas.drawLine(getWidth(),0,getWidth(),getHeight(),paint);
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);
    }
    //____________________________________________
}
