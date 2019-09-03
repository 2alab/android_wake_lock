import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:android_wake_lock/android_wake_lock.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _lockStatus = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    bool lock;
    try {
      lock = await AndroidWakeLock.wakeLock(WakeLock.FULL_WAKE_LOCK);
    } on PlatformException {
      lock = false;
    }
    setState(() {
      _lockStatus = "lock $lock";
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('WakeLock test app'),
        ),
        body: Center(
            child: Column(
          children: <Widget>[
            Text('Running on: $_lockStatus'),
            IconButton(
              icon: Icon(Icons.volume_up),
              onPressed: () {
                setState(() {});
              },
            )
          ],
        )),
      ),
    );
  }
}
