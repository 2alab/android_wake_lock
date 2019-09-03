package delmeflutterapp.android_wake_lock;

import android.content.Context;
import android.os.PowerManager;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * AndroidWakeLockPlugin
 */
public class AndroidWakeLockPlugin implements MethodCallHandler {

    private static Context context;

    private Map<String, PowerManager.WakeLock> wakeLocks = new HashMap<>();

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "android_wake_lock");
        channel.setMethodCallHandler(new AndroidWakeLockPlugin());
        context = registrar.activeContext();
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        final String name = call.argument("name");
        if (null == name || name.isEmpty()) {
            result.error("name", "name is requared", null);
            return;
        }

        if (call.method.equals("wakeLock")) {
            WakeLocks type = WakeLocks.valueOf(call.argument("type").toString());
            Integer timeout =  call.argument("timeout");
            if (null==timeout){
                timeout = 0;
            }

            result.success(wakeLock(name, type,timeout));
        } else if (call.method.equals("release")) {
            result.success(release(name));
        } else {
            result.notImplemented();
        }
    }


    private boolean wakeLock(String name, WakeLocks type, long time) {
        if (null != wakeLocks.get(name)) {
            return false;
        }

        PowerManager.WakeLock wakeLock = null;
        if (0 == time) {
            wakeLock = createWakeLock(name, type);
            wakeLock.acquire();
        } else {
            wakeLock = createWakeLock(name, type);
            wakeLock.acquire(time);
        }
        wakeLocks.put(name, wakeLock);
        return true;
    }

    private boolean release(String name) {
        PowerManager.WakeLock wakeLock = wakeLocks.get(name);
        if (null != wakeLock) {
            wakeLock.release();
            return true;
        }
        return false;
    }

    private synchronized PowerManager.WakeLock createWakeLock(String name, WakeLocks type) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(type.getNativeConstant(), "AndroidWakeLockPlugin: newWakeLock " + name);
        if (null == wakeLock) {
            throw new IllegalStateException("newWakeLock ");
        }

        return wakeLock;
    }
}
