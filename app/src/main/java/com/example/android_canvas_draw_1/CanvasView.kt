package com.example.android_canvas_draw_1

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

const val STROKE_WIDTH=10f
const val TOLERANCE=4f

class CanvasView(context: Context?) : View(context), View.OnTouchListener {

    lateinit var mBitmap:Bitmap
    private var mCanvas: Canvas?=null
    private var mX=0f
    private var mY=0f
    private var paths=ArrayList<Path>()
    private var paints=ArrayList<Paint>()
    private var mPath:Path
    private var mPaint:Paint

    init{
        mPath=Path()
        mPaint=Paint()
        mPaint.isAntiAlias=true
        mPaint.isDither=true
        mPaint.color= Color.BLACK
        mPaint.style=Paint.Style.STROKE
        mPaint.strokeJoin=Paint.Join.ROUND
        mPaint.strokeCap=Paint.Cap.ROUND
        mPaint.strokeWidth=STROKE_WIDTH
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        mCanvas=Canvas(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        for(i in 0 until paths.size)
            canvas.drawPath(paths[i],paints[i])
        canvas.drawPath(mPath,mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x=event.x
        val y=event.y
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                touchStart(x,y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE->{
                touchMove(x,y)
                invalidate()
            }
            MotionEvent.ACTION_UP->{
                touchUp(x,y)
                invalidate()
            }
        }
        return true
    }

    private fun touchStart(x:Float,y:Float){
        mPath.reset()
        mPath.moveTo(x,y)
        mX=x
        mY=y
    }

    private fun touchMove(x:Float,y:Float){
        val dx=Math.abs(x-mX)
        val dy=Math.abs(y-mY)
        if(dx>= TOLERANCE || dy>= TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2)
            mX=x
            mY=y
        }
    }

    private fun touchUp(x:Float,y:Float){
        mPath.lineTo(mX,mY)
        mCanvas!!.drawPath(mPath,mPaint)
        paths.add(mPath)
        paints.add(mPaint)
        mPath=Path()
    }

}