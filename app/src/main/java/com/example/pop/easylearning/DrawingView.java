package com.example.pop.easylearning;

/**
 * Created by pop on 26/02/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by pop on 04/02/2016.
 */public class DrawingView extends View{


    private Canvas drawCanvas;
    public Bitmap canvasBitmap;
    private Paint drawPaint,canvasPaint;
    private Path drawPath;
    private int paintColor = 0xFF660000;
    private float brushSize, lastBrushSize;


    private boolean erase=false;



//zomm
static final String TAG = "DrawView";
    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 6f;
    private float scaleFactor = 3.f;
    private ScaleGestureDetector detector;
    private float startX = 0f;
    private float startY = 0f;
    private float translateX = 0f;
    private float translateY = 0f;
    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

    private boolean dragged = false;
    private float displayWidth;
    private float displayHeight;

    Context ctx;










    public DrawingView (Context context,AttributeSet attr)
    { super(context, attr);


        setUpDrawing();

//zoom
        ctx=context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();

        display.getMetrics(metrics);

        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;

        translateX = displayWidth/4;
        translateY = displayHeight/4;

        previousTranslateX = displayWidth/4;
        previousTranslateY = displayHeight/4;

        detector = new ScaleGestureDetector(context, new ScaleListener());

        setFocusable(true);
        setFocusableInTouchMode(true);











    }


    private void setUpDrawing(){
        drawPath=new Path();
        drawPaint=new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);

        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);


        canvasPaint = new Paint(Paint.DITHER_FLAG);
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;

        drawPaint.setStrokeWidth(brushSize);

    }

    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap=Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        drawCanvas=new Canvas(canvasBitmap);
    }
    protected  void onDraw(Canvas canvas)
    {

        canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
//zoom
        canvas.save();

        //We're going to scale the X and Y coordinates by the same amount
        canvas.scale(scaleFactor, scaleFactor, 0, 0);

        //We need to divide by the scale factor here, otherwise we end up with excessive panning based on our zoom level
        //because the translation amount also gets scaled according to how much we've zoomed into the canvas.
        canvas.translate((translateX) / scaleFactor, (translateY) / scaleFactor);

        canvas.drawBitmap(canvasBitmap,getMatrix(), drawPaint);

        canvas.restore();






    }

    public  boolean onTouchEvent(MotionEvent event){

        float touchX=event.getX();
        float touchY=event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);

                startX = event.getX( ) - previousTranslateX;
                startY = event.getY() - previousTranslateY;



                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);

                translateX = event.getX() - startX;
                translateY = event.getY() - startY;



                break;
            case MotionEvent.ACTION_UP:

                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                previousTranslateX = translateX;
                previousTranslateY = translateY;


                break;
            default:
                return false;
        }
        detector.onTouchEvent(event);

        invalidate();



        return true;

    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {


            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

            invalidate();
            return true;
        }
    }




           public void setColor(String newColor) {
               invalidate();
               paintColor = Color.parseColor(newColor);
               drawPaint.setColor(paintColor);


           }


           public void setBrushSize(float newSize) {
               float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                       newSize, getResources().getDisplayMetrics());
               brushSize = pixelAmount;
               drawPaint.setStrokeWidth(brushSize);
           }

           public void setLastBrushSize(float lastSize) {
               lastBrushSize = lastSize;
           }

           public float getLastBrushSize() {
               return lastBrushSize;
           }

           public void setErase(boolean isErase) {
               erase = isErase;


               if (erase)

                    drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

               else drawPaint.setXfermode(null);


           }


           public void startNew() {
               drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
               invalidate();
           }


       }