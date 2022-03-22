package com.example.DropdownNotification;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

//import android.view.KeyEvent;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.FLAG_SHOW_UI;

public class MyAccessibilityService extends AccessibilityService implements KeyEvent.Callback {
    AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//    AccessibilityManager am = new AccessibilityManager()

    private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
    private static long lastPressTime;
    private static long cnt = 0;


    @Override
    public boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            long pressTime = event.getEventTime();
//            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
////                Log.d("Hello", "KeyUp");
//            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
////                Log.d("Hello", "KeyDown");
//            }
//            if (cnt == 0) {

            if (action == KeyEvent.ACTION_DOWN) {
                lastPressTime = pressTime;
                cnt++;
                return true;
            } else if (action == KeyEvent.ACTION_UP) {
                if (pressTime - lastPressTime >= DOUBLE_PRESS_INTERVAL) {
                    AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                    final AccessibilityEvent accessibilityEvent = AccessibilityEvent
                            .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START);
                    Log.d("Hello", "KEYCODE_VOLUME_DOWN");
                    manager.sendAccessibilityEvent(accessibilityEvent);
                    return true;
                } else {
//                    cnt = 0;
                    AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                    final AccessibilityEvent accessibilityEvent = AccessibilityEvent
                            .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END);
                    Log.d("Hello", "KEYCODE_VOLUME_DOWN");
                    manager.sendAccessibilityEvent(accessibilityEvent);
//                    audioManager.adjustVolume(ADJUST_LOWER, 0);
                    return false;
                }
            }


//            if (action == KeyEvent.ACTION_DOWN) {
////                event.startTracking();
//                Log.d("Hello", "ACTION_DOWN");
//                return true;
//            }
//
//            if (action == KeyEvent.ACTION_UP) {
//                Log.d("Hello", "ACTION_UP");
//                if (event.isTracking()) {
//                    if (event.isLongPress()) {
//                        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//                        final AccessibilityEvent accessibilityEvent = AccessibilityEvent
//                                .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START);
//                        Log.d("Hello", "KEYCODE_VOLUME_DOWN");
//                        manager.sendAccessibilityEvent(accessibilityEvent);
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//            }


//            if (action == KeyEvent.ACTION_DOWN && event.isLongPress()) {
//                AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//                final AccessibilityEvent accessibilityEvent = AccessibilityEvent
//                        .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START);
//                Log.d("Hello", "KEYCODE_VOLUME_DOWN");
//                manager.sendAccessibilityEvent(accessibilityEvent);
//                return true;
//            } else {
//                return false;
//            }



//            return true;
//            Log.d("Hello", "KeyPower");


        }
//            return super.onKeyEvent(event);
//            Log.d("Hello", "no KeyPower");
            Log.d("Hello", "no KEYCODE_VOLUME_DOWN");
            return false;

    }

//    @Override
//    protected boolean onGesture(int gestured) {//This method always called, I want this method only called when my App in foreground
//        Log.d("onGesture","enter onGesture");
//        if (gestured == GESTURE_SWIPE_UP_AND_DOWN) {
//            Log.d("onGesture","GESTURE_SWIPE_UP_AND_DOWN");
//            this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
//        }
//        return false;
//    }


//    @Override
//    protected boolean onKeyEvent(KeyEvent event) {
//        Log.d("Hello", "onKeyEvent");
//        return super.onKeyEvent(event);
//    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        String eventText = null;
        long pressTime;
//        switch(eventType) {
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:
//                eventText = "Clicked: ";
//                break;
//        }
        Log.d("Hello", "enter");
//        if (AccessibilityEvent.TYPE_VIEW_CLICKED == eventType) {
//            pressTime = System.currentTimeMillis();
//            if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
//                this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
//                Log.d("Hello", "double");
//            } else {
//                lastPressTime = pressTime;
//                Log.d("Hello", "no double");
//            }
////            this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
//        }

        if (AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START == eventType) {
            this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
        }

        if (AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END == eventType) {
//            audioManager.adjustVolume(ADJUST_LOWER, 0);
            AudioManager audioManager =
                    (AudioManager) getSystemService(AUDIO_SERVICE);
//            audioManager.adjustStreamVolume(AudioManager.STREAM_ACCESSIBILITY,
//                    ADJUST_RAISE, FLAG_SHOW_UI);
//            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
//                    ADJUST_LOWER, FLAG_SHOW_UI);
            audioManager.adjustVolume(ADJUST_LOWER, FLAG_SHOW_UI);
        }



            eventText = eventText + event.getContentDescription();

        // Do something nifty with this text, like speak the composed string
        // back to the user.
//        this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
    }



    @Override
    public void onInterrupt() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("Hello", "onKeyDown");
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.d("Hello", "onKeyLongPress");
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("Hello", "onKeyUp");
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        Log.d("Hello", "onKeyMultiple");
        return false;
    }

//    @Override
//    public void onServiceConnected() {
////        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//
//        // Set the type of events that this service wants to listen to. Others
//        // won't be passed to this service.
//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED;
//
//        // If you only want this service to work with specific applications, set their
//        // package names here. Otherwise, when the service is activated, it will listen
//        // to events from all applications.
////        info.packageNames = new String[]
////                {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};
//
//        // Set the type of feedback your service will provide.
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
//
//        // Default services are invoked only if no package-specific ones are present
//        // for the type of AccessibilityEvent generated. This service *is*
//        // application-specific, so the flag isn't necessary. If this was a
//        // general-purpose service, it would be worth considering setting the
//        // DEFAULT flag.
//
//        // info.flags = AccessibilityServiceInfo.DEFAULT;
//
//        info.notificationTimeout = 100;
//
//        this.setServiceInfo(info);
//
//    }

//    @Override
//    protected boolean onKeyDown(KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
//            //Do something
//        }
//        return true;
//    }



}


