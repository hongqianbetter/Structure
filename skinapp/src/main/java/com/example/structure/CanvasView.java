package com.example.structure;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.skinlibrary.SkinViewSupport;
import com.example.structure.R;
import com.example.skinlibrary.SkinResources;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/28
 */
public class CanvasView extends View implements SkinViewSupport {

    private Paint paint;
    private int colorId;
    //    int[] attrId = new int[]{R.attr.canvasColor};

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CanvasView);
        colorId = typedArray.getResourceId(R.styleable.CanvasView_canvasColor, 0);
        int color = getResources().getColor(colorId);
        paint.setColor(color);
    }

    {
        paint = new Paint();

    }



    @Override
    public void applySkin() {
        int color = SkinResources.getInstance().getColor(colorId);
        //拿到换肤后的值 执行改变
        paint.setColor(color);
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
    }
}
