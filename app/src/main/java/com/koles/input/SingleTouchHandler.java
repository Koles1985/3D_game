package com.koles.input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SingleTouchHandler implements TouchHandler{
    private boolean isTouched;
    private int touchX;
    private int touchY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
    private float scaleX;
    private float scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY){
        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };

        touchEventPool = new Pool(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this){
            TouchEvent touchEvent = touchEventPool.newObject();
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    touchEvent.setType(TouchEvent.TOUCH_DOWN);
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.setType(TouchEvent.TOUCH_MOVE);
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent.setType(TouchEvent.TOUCH_UP);
                    isTouched = false;
                    break;
            }

            touchEvent.setX(touchX = (int)(event.getX() * scaleX));
            touchEvent.setY(touchY = (int)(event.getY() * scaleY));
            touchEventBuffer.add(touchEvent);

            return true;

        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this){
            if(pointer == 0){
                return isTouched;
            }else{
                return false;
            }
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this){
            return touchX;
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this){
            return touchY;
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this){
            int len = touchEvents.size();
            for(int i = 0; i < len; i++){
                touchEventPool.tryAddObject(touchEvents.get(i));
            }

            touchEvents.clear();
            touchEvents.addAll(touchEventBuffer);
            touchEventBuffer.clear();
            return touchEvents;
        }
    }
}
