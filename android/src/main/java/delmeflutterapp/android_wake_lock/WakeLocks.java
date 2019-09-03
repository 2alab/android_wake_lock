package delmeflutterapp.android_wake_lock;

import android.os.PowerManager;

/**
 * https://developer.android.com/reference/android/os/PowerManager.html#PARTIAL_WAKE_LOCK
 */
public enum WakeLocks {

    PARTIAL_WAKE_LOCK(PowerManager.PARTIAL_WAKE_LOCK),//
    SCREEN_DIM_WAKE_LOCK(PowerManager.SCREEN_DIM_WAKE_LOCK),//
    SCREEN_BRIGHT_WAKE_LOCK(PowerManager.SCREEN_BRIGHT_WAKE_LOCK),//
    FULL_WAKE_LOCK(PowerManager.FULL_WAKE_LOCK);


    private int nativeConstant;


    WakeLocks(int nativeConstant) {
        this.nativeConstant = nativeConstant;
    }

    public int getNativeConstant() {
        return nativeConstant;
    }}
