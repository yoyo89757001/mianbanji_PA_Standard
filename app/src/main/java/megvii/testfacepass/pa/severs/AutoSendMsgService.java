package megvii.testfacepass.pa.severs;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AutoSendMsgService extends AccessibilityService {
    /**
     * 必须重写的方法，响应各种事件。
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {

                String currentActivity = event.getClassName().toString();

                Log.d("AutoSendMsgService", currentActivity);
                Log.d("AutoSendMsgService", "event.getAction():" + event.getAction());

            }
            break;
        }
    }

    @Override
    public void onInterrupt() {

    }


}
