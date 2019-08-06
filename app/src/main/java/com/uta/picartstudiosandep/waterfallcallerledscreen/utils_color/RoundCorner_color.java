package com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class RoundCorner_color extends ImageView {
    private Bitmap maskBitmap;
    private Paint maskPaint;
    private Paint paint;

    public RoundCorner_color(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundCorner_color(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public RoundCorner_color(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedValue.applyDimension(1, 1.31728998E9f, context.getResources().getDisplayMetrics());
        this.paint = new Paint(1);
        this.maskPaint = new Paint(3);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        setWillNotDraw(false);
    }

    public void draw(Canvas canvas) {
        Bitmap createBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        super.draw(canvas2);
        if (this.maskBitmap == null) {
            this.maskBitmap = createMask(canvas.getWidth(), canvas.getHeight());
        }
        canvas2.drawBitmap(this.maskBitmap, 0.0f, 0.0f, this.maskPaint);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.paint);
    }

    private Bitmap createMask(int paramInt1, int paramInt2) {
        Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Config.ALPHA_8);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint(1);
        localPaint.setColor(-1);
        float f1 = (float) paramInt1;
        float f2 = (float) paramInt2;
        localCanvas.drawRect(0.0f, 0.0f, f1, f2, localPaint);
        localPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        localCanvas.drawRoundRect(new RectF(0.0f, 0.0f, f1, f2), 190.0f, 190.0f, localPaint);
        return localBitmap;
    }
}
