import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:async_wallpaper/async_wallpaper.dart';
import 'package:flutter_cache_manager/flutter_cache_manager.dart';

void main() {
  runApp(const MaterialApp(home: MyApp()));
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _wallpaperFileNative = 'Unknown';
  String _wallpaperFileHome = 'Unknown';
  String _wallpaperFileLock = 'Unknown';
  String _wallpaperFileBoth = 'Unknown';
  String _wallpaperUrlNative = 'Unknown';
  String _wallpaperUrlHome = 'Unknown';
  String _wallpaperUrlLock = 'Unknown';
  String _wallpaperUrlBoth = 'Unknown';
  String _liveWallpaper = 'Unknown';
  // String url = 'https://images.ctfassets.net/s5n2t79q9icq/7BQBejPzI2WjDEYuq691n6/e1eda96008fe75a2acc028cf8c86dbbd/Sigarda_Font_of_Blessings_2560x1600_EN.png';
  String url = 'https://images.unsplash.com/photo-1635593701810-3156162e184f';
  String liveUrl = 'https://github.com/codenameakshay/sample-data/raw/main/video3.mp4';

  late bool goToHome;


  @override
  void initState() {
    super.initState();
    goToHome = false;
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await AsyncWallpaper.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperFromFileNative(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperFileNative = 'Loading';
    });
    String result;
    // Specify the wallpaper setting position
    int left = 0; // Leftmost coordinate position
    int top = 0; // Top edge coordinate position
    int right = left + deviceWidth.round(); // Rightmost coordinate position
    int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    var file = await DefaultCacheManager().getSingleFile(url);
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaperFromFileNative(
        filePath: file.path,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
        bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperFileNative = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperFromFileHome(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperFileHome = 'Loading';
    });
    String result;
    // Specify the wallpaper setting position
    int left = 0; // Leftmost coordinate position
    int top = 0; // Top edge coordinate position
    int right = left + deviceWidth.round(); // Rightmost coordinate position
    int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    var file = await DefaultCacheManager().getSingleFile(url);
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaperFromFile(
        filePath: file.path,
        wallpaperLocation: AsyncWallpaper.HOME_SCREEN,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
        bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperFileHome = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperFromFileLock(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperFileLock = 'Loading';
    });
    String result;
    // Specify the wallpaper setting position
    int left = 0; // Leftmost coordinate position
    int top = 0; // Top edge coordinate position
    int right = left + deviceWidth.round(); // Rightmost coordinate position
    int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    var file = await DefaultCacheManager().getSingleFile(url);
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaperFromFile(
        filePath: file.path,
        wallpaperLocation: AsyncWallpaper.LOCK_SCREEN,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
        bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperFileLock = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperFromFileBoth(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperFileBoth = 'Loading';
    });
    String result;
    // Specify the wallpaper setting position
    int left = 0; // Leftmost coordinate position
    int top = 0; // Top edge coordinate position
    int right = left + deviceWidth.round(); // Rightmost coordinate position
    int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    var file = await DefaultCacheManager().getSingleFile(url);
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaperFromFile(
        filePath: file.path,
        wallpaperLocation: AsyncWallpaper.BOTH_SCREENS,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
        bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperFileBoth = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperNative(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperUrlNative = 'Loading';
    });
    String result;
    // Specify the wallpaper setting position
    int left = 0; // Leftmost coordinate position
    int top = 0; // Top edge coordinate position
    int right = left + deviceWidth.round(); // Rightmost coordinate position
    int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaperNative(
        url: url,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
        bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperUrlNative = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperHome(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperUrlHome = 'Loading';
    });
    String result;
	  // Specify the wallpaper setting position
	  int left = 0; // Leftmost coordinate position
	  int top = 0; // Top edge coordinate position
	  int right = left + deviceWidth.round(); // Rightmost coordinate position
	  int bottom = top + deviceHeight.round(); // Bottom edge coordinate position
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaper(
        url: url,
        wallpaperLocation: AsyncWallpaper.HOME_SCREEN,
        goToHome: goToHome,
        left: left,
		    top: top,
		    right: right,
		    bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperUrlHome = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperLock(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperUrlLock = 'Loading';
    });
    String result;
	  // Specify the wallpaper setting position
	  int left = 0; // Leftmost coordinate position
	  int top = 0; // Top edge coordinate position
	  int right = left + deviceWidth.round(); // Rightmost coordinate position
	  int bottom = top + deviceHeight.round(); // Bottom edge coordinate position

    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaper(
        url: url,
        wallpaperLocation: AsyncWallpaper.LOCK_SCREEN,
        goToHome: goToHome,
        left: left,
		    top: top,
		    right: right,
		    bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperUrlLock = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setWallpaperBoth(double deviceWidth, double deviceHeight) async {
    setState(() {
      _wallpaperUrlBoth = 'Loading';
    });
    String result;
	  int left = 0; // Leftmost coordinate position
	  int top = 0; // Top edge coordinate position
	  int right = left + deviceWidth.round(); // Rightmost coordinate position
	  int bottom = top + deviceHeight.round(); // Bottom edge coordinate position

    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setWallpaper(
        url: url,
        wallpaperLocation: AsyncWallpaper.BOTH_SCREENS,
        goToHome: goToHome,
        left: left,
        top: top,
        right: right,
		    bottom: bottom,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _wallpaperUrlBoth = result;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> setLiveWallpaper() async {
    setState(() {
      _liveWallpaper = 'Loading';
    });
    String result;
    var file = await DefaultCacheManager().getSingleFile(liveUrl);
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      result = await AsyncWallpaper.setLiveWallpaper(
        filePath: file.path,
        goToHome: goToHome,
        toastDetails: ToastDetails.success(),
        errorToastDetails: ToastDetails.error(),
      )
          ? 'Wallpaper set'
          : 'Failed to get wallpaper.';
    } on PlatformException {
      result = 'Failed to get wallpaper.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _liveWallpaper = result;
    });
  }

  @override
  Widget build(BuildContext context) {

    final double deviceWidth = MediaQuery.of(context).size.width;
    final double deviceHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: SingleChildScrollView(
        child: SizedBox(
          height: MediaQuery.of(context).size.height,
          child: Column(
            children: [
              Center(
                child: Text('Running on: $_platformVersion\n'),
              ),
              SwitchListTile(
                  title: const Text('Go to home'),
                  value: goToHome,
                  onChanged: (value) {
                    setState(() {
                      goToHome = value;
                    });
                  }),
              ElevatedButton(
                onPressed: () =>  setWallpaperFromFileNative(deviceWidth,deviceHeight),
                child: _wallpaperFileNative == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from file native'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperFileNative\n'),
              ),
              ElevatedButton(
                onPressed: () =>  setWallpaperFromFileHome(deviceWidth,deviceHeight),
                child: _wallpaperFileHome == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from file home'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperFileHome\n'),
              ),
              ElevatedButton(
                onPressed: () =>  setWallpaperFromFileLock(deviceWidth,deviceHeight),
                child: _wallpaperFileLock == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from file lock'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperFileLock\n'),
              ),
              ElevatedButton(
                onPressed: () =>  setWallpaperFromFileBoth(deviceWidth,deviceHeight),
                child: _wallpaperFileBoth == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from file both'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperFileBoth\n'),
              ),
              ElevatedButton(
                onPressed: () =>  setWallpaperNative(deviceWidth,deviceHeight),
                child: _wallpaperUrlNative == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from Url native'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperUrlNative\n'),
              ),
              ElevatedButton(
                onPressed: () =>  setWallpaperHome(deviceWidth,deviceHeight),
                child: _wallpaperUrlHome == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from Url home'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperUrlHome\n'),
              ),
              ElevatedButton(
                onPressed: () => setWallpaperLock(deviceWidth,deviceHeight),
                child: _wallpaperUrlLock == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from Url lock'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperUrlLock\n'),
              ),
              ElevatedButton(
                onPressed:  () => setWallpaperBoth(deviceWidth,deviceHeight),
                child: _wallpaperUrlBoth == 'Loading'
                    ? const CircularProgressIndicator()
                    : const Text('Set wallpaper from Url both'),
              ),
              Center(
                child: Text('Wallpaper status: $_wallpaperUrlBoth\n'),
              ),
              ElevatedButton(
                onPressed: setLiveWallpaper,
                child:
                    _liveWallpaper == 'Loading' ? const CircularProgressIndicator() : const Text('Set live wallpaper'),
              ),
              Center(
                child: Text('Wallpaper status: $_liveWallpaper\n'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
