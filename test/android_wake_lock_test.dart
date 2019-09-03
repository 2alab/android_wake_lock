import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:android_wake_lock/android_wake_lock.dart';

void main() {
  const MethodChannel channel = MethodChannel('android_wake_lock');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AndroidWakeLock.platformVersion, '42');
  });
}
