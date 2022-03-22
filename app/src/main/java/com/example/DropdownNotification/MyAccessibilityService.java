package com.example.DropdownNotification;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.FLAG_SHOW_UI;

public class MyAccessibilityService extends AccessibilityService {

    private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
    private static long lastPressTime;

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            long pressTime = event.getEventTime();

            if (action == KeyEvent.ACTION_DOWN) {
                lastPressTime = pressTime;
                return true;
            } else if (action == KeyEvent.ACTION_UP) {
                if (pressTime - lastPressTime >= DOUBLE_PRESS_INTERVAL) {
                    AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                    final AccessibilityEvent accessibilityEvent = AccessibilityEvent
                            .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START);
                    manager.sendAccessibilityEvent(accessibilityEvent);
                    return true;
                } else {
                    AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
                    final AccessibilityEvent accessibilityEvent = AccessibilityEvent
                            .obtain(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END);
                    manager.sendAccessibilityEvent(accessibilityEvent);
                    return true;
                }
            }
        }
            Log.d("Hello", "no KEYCODE_VOLUME_DOWN");
            return false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();

        if (AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START == eventType) {
            this.performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
        }

        if (AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END == eventType) {
            AudioManager audioManager =
                    (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.adjustVolume(ADJUST_LOWER, FLAG_SHOW_UI);
        }
    }

    @Override
    public void onInterrupt() {
    }
}


