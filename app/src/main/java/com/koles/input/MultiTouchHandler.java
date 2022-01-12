package com.koles.input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandler{
    private boolean[] isTouched = new boolean[20];
    private int[] touchX = new int[20];
    private int[] touchY = new int[20];

    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();

    private float scaleX;
    private float scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY){
        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };

        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this){
            int action = event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);

            TouchEvent touchEvent;
            switch(action){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.setType(TouchEvent.TOUCH_DOWN);
                    touchEvent.setPointer(pointerId);
                    touchEvent.setX(touchX[pointerId] = (int)(event.getX(pointerIndex) * scaleX));
                    touchEvent.setY(touchY[pointerId] = (int)(event.getY(pointerIndex) * scaleY));
                    isTouched[pointerId] = true;
                    touchEventBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.setType(TouchEvent.TOUCH_UP);
                    touchEvent.setPointer(pointerId);
                    touchEvent.setX(touchX[pointerId] = (int)(event.getX(pointerIndex) * scaleX));
                    touchEvent.setY(touchY[pointerId] = (int)(event.getY(pointerIndex) * scaleY));
                    isTouched[pointerId] = false;
                    touchEventBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for(int i = 0; i < pointerCount; i++){
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);
                        touchEvent = touchEventPool.newObject();
                        touchEvent.setType(TouchEvent.TOUCH_MOVE);
                        touchEvent.setPointer(pointerId);
                        touchEvent.setX(touchX[pointerId] = (int)(event.getX(pointerIndex)
                                * scaleX));
                        touchEvent.setY(touchY[pointerId] = (int)(event.getY(pointerIndex)
                                * scaleY));
                        touchEventBuffer.add(touchEvent);
                    }
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this){
            if(pointer < 0 || pointer >= 20){
                return false;
            }else{
                return isTouched[pointer];
            }
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this){
            if(pointer < 0 || pointer > 20){
                return 0;
            }else {
                return touchX[pointer];
            }
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this){
            if(pointer < 0 || pointer > 20){
                return 0;
            }else {
                return touchX[pointer];
            }
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this){
            int len = touchEvents.size();
            for(int i = 0; i < len; i ++){
                touchEventPool.tryAddObject(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventBuffer);
            touchEventBuffer.clear();
            return touchEvents;
        }
    }
}
