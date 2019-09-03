import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

enum WakeLock {
  PARTIAL_WAKE_LOCK,
  SCREEN_DIM_WAKE_LOCK,
  SCREEN_BRIGHT_WAKE_LOCK,
  FULL_WAKE_LOCK
}

class AndroidWakeLock {
  static const MethodChannel _channel =
      const MethodChannel('android_wake_lock');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///crete wakelock
  ///repeated calls are allowed
  static Future<bool> wakeLock(
    WakeLock type, {
    String name = "default",
    Duration timeout = Duration.zero,
  }) async {
    final bool lock = await _channel.invokeMethod('wakeLock', {
      'name': name,
      'timeout': timeout.inMilliseconds,
      'type': describeEnum(type)
    });
    return lock;
  }

  static Future<bool> release({String name = "default"}) async {
    final bool lock = await _channel.invokeMethod('release', {'name': name});
    return lock;
  }
}
