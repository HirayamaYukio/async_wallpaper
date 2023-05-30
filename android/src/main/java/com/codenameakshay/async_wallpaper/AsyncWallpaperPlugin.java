package com.codenameakshay.async_wallpaper;

import android.app.Activity;
import android.app.Application;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Canvas;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * AsyncWallpaperPlugin
 */
public class AsyncWallpaperPlugin extends Application implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private MethodChannel channel_home;
    public static Context context;
    private Activity activity;
    public static MethodChannel.Result res;

    private boolean redirectToLiveWallpaper;
    private boolean goToHome;
    Map<String, Integer> coordinateMap = new HashMap<String, Integer>();
    Map<String, Boolean> flgMap = new HashMap<String, Boolean>();
	private int left;
	private int top;
	private int right;
	private int bottom;
    private int deviceWidth;
    private int deviceHeight;
    private boolean rectangleFlg;
    private int centerX;
    private int userCorrection;


    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + "Image Downloaded");
            SetWallPaperTask setWallPaperTask = new SetWallPaperTask(context, coordinateMap, flgMap);
            setWallPaperTask.execute(new Pair(resource, "1"));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    private Target target1 = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + "Image Downloaded");
            SetWallPaperTask setWallPaperTask = new SetWallPaperTask(context, coordinateMap, flgMap);
            setWallPaperTask.execute(new Pair(resource, "2"));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    private Target target2 = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + "Image Downloaded");
            SetWallPaperTask setWallPaperTask = new SetWallPaperTask(context, coordinateMap, flgMap);
            setWallPaperTask.execute(new Pair(resource, "3"));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    private Target target3 = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + "Image Downloaded");
            SetWallPaperTask setWallPaperTask = new SetWallPaperTask(context, coordinateMap, flgMap);
            setWallPaperTask.execute(new Pair(resource, "4"));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "async_wallpaper");
        channel.setMethodCallHandler(this);
        context = flutterPluginBinding.getApplicationContext();
        redirectToLiveWallpaper = false;
        goToHome = false;
    }

    @Override
    public void onDetachedFromActivity() {
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding flutterPluginBinding) {
        if (redirectToLiveWallpaper && goToHome) {
            home();
        }
    }


    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding flutterPluginBinding) {
        activity = flutterPluginBinding.getActivity();
    }


    @Override
    public void onDetachedFromActivityForConfigChanges() {
    }

    public void home() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        res = result;
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("set_wallpaper")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);

            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load(url).into(target);
            // result.success(1);
        } else if (call.method.equals("set_wallpaper_file")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load("file://" + url).into(target);
            // result.success(1);

        } else if (call.method.equals("set_lock_wallpaper")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            // android.util.Log.i("Arguments ", "configureFlutterEngine(left, top, right, bottom): " + left.toString() + ", "+ left.toString() + ", " + right.toString() + ", " + bottom.toString());
            Picasso.get().load(url).into(target1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_home_wallpaper")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load(url).into(target2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_both_wallpaper")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load(url).into(target3);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_lock_wallpaper_file")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load("file://" + url).into(target1);
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_home_wallpaper_file")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load("file://" + url).into(target2);
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_both_wallpaper_file")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            left = call.argument("left");
            top = call.argument("top");
            right = call.argument("right");
            bottom = call.argument("bottom");
            deviceWidth = call.argument("deviceWidth");
            deviceHeight = call.argument("deviceHeight");
            rectangleFlg = call.argument("rectangleFlg");
            centerX = call.argument("centerX");
            userCorrection = call.argument("userCorrection");
            coordinateMap.put("left", left);
            coordinateMap.put("top", top);
            coordinateMap.put("right", right);
            coordinateMap.put("bottom", bottom);
            coordinateMap.put("deviceWidth", deviceWidth);
            coordinateMap.put("deviceHeight", deviceHeight);
            coordinateMap.put("centerX",centerX);
            coordinateMap.put("userCorrection",userCorrection);
            flgMap.put("rectangleFlg",rectangleFlg);
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            Picasso.get().load("file://" + url).into(target3);
            if (goToHome) home();
            // result.success(1);

        } else if (call.method.equals("set_video_wallpaper")) {
            String url = call.argument("url"); // .argument returns the correct type
            goToHome = call.argument("goToHome"); // .argument returns the correct type
            android.util.Log.i("Arguments ", "configureFlutterEngine: " + url);
            // Picasso.get().load("file://" + url).into(target3);
            copyFile(new File(url), new File(activity.getFilesDir().toPath() + "/file.mp4"));
            redirectToLiveWallpaper = false;
            VideoLiveWallpaper mVideoLiveWallpaper = new VideoLiveWallpaper();
            mVideoLiveWallpaper.setToWallPaper(context);
            result.success(true);

        } else {
            result.notImplemented();
        }
    }

    public void copyFile(File fromFile, File toFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;
        try {
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(toFile);
            fileChannelInput = fileInputStream.getChannel();
            fileChannelOutput = fileOutputStream.getChannel();
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (fileChannelInput != null)
                    fileChannelInput.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (fileChannelOutput != null)
                    fileChannelOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}


class SetWallPaperTask extends AsyncTask<Pair<Bitmap, String>, Boolean, Boolean> {

    private final Context mContext;
    private final Map<String, Integer> mMap;
    private final Map<String, Boolean> fMap;
	private double const_mobileBaseSizeY = 1536.0;
	private double const_mobileBaseSizeX = 1040.0;

    //public SetWallPaperTask(final Context context) {
    public SetWallPaperTask(final Context context, final Map<String, Integer> coordinateMap, final Map<String, Boolean> flgMap) {
        mContext = context;
		mMap = coordinateMap;
        fMap = flgMap;
    }

    @Override
    protected final Boolean doInBackground(Pair<Bitmap, String>... pairs) {
        switch (pairs[0].second) {
            case "1": {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                try {
                    Uri tempUri = getImageUri(mContext, pairs[0].first);
                    Log.i("Arguments ", "configureFlutterEngine: " + "Saved image to storage");
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    Uri contentURI = getImageContentUri(mContext, finalFile.getAbsolutePath());
                    Log.i("Arguments ", "configureFlutterEngine: " + "Opening crop intent");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        final Intent intentCrop = wallpaperManager.getCropAndSetWallpaperIntent(contentURI);
                        intentCrop.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intentCrop);
                    } else {
                        wallpaperManager.setBitmap(pairs[0].first);
                    }
                } catch (Exception ex) {
                    try {
                        wallpaperManager.setBitmap(pairs[0].first);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ex.printStackTrace();
                    return false;
                }
            }
            case "2": {
                // Pass when setting lock screen
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Bitmap image = pairs[0].first;
                        int ImageWidth = image.getWidth();
                        int ImageHeight = image.getHeight();

                        if(fMap.get("rectangleFlg")){
                            Rect rect = new Rect(mMap.get("left"),mMap.get("top"),mMap.get("right"),mMap.get("bottom"));
                            wallpaperManager.setBitmap(pairs[0].first, rect, true, WallpaperManager.FLAG_LOCK);
                        }else if(!fMap.get("rectangleFlg") && ImageWidth > ImageHeight){
                            Map<String, Integer> map = mMap;
                            int device_width = mMap.get("deviceWidth");
                            int device_height = mMap.get("deviceHeight");
                            int centerX = mMap.get("centerX");
                            int startX = 0;
                            int set_bitmap_width = ImageWidth;

                            int Dw = wallpaperManager.getDesiredMinimumWidth();
                            int Dh = wallpaperManager.getDesiredMinimumHeight();
                            int Cw = (int) ((float) Dw / (float) Dh * ImageHeight);
                            int Ch = (int)((float)ImageHeight / (float)Dw * Dh);

                            double scale = const_mobileBaseSizeY/ImageHeight; // Calculate how many times the height of the image is the mobile base size
                            int system_correction = (int)(centerX * scale); // Correct center coordinate X according to resizing
                            int user_correction = (int)(mMap.get("userCorrection") * scale);

                            // Resize to mobile base size based on height
                            Bitmap tmpBitmap = Bitmap.createScaledBitmap(image,(int)(ImageWidth * scale),(int)const_mobileBaseSizeY, true);

                            // Calculate start position X and end point
                            startX = system_correction - (int)(const_mobileBaseSizeX/2) + user_correction;
                            set_bitmap_width = (int)const_mobileBaseSizeX;

                            // Change the starting position if the width overflows
                            if(startX < 0){
                                startX = 0;
                            }else if(startX+set_bitmap_width > tmpBitmap.getWidth()){
                                startX = tmpBitmap.getWidth() - (int)const_mobileBaseSizeX;
                            }else if(tmpBitmap.getWidth() < set_bitmap_width){
                                startX = 0;
                                set_bitmap_width = tmpBitmap.getWidth();
                            }

                            // Formatted to mobileSize(1040*1536) size and set as home
                            Bitmap trimBmp = Bitmap.createBitmap(tmpBitmap,startX,0,set_bitmap_width,tmpBitmap.getHeight(), null, true);
                            wallpaperManager.setBitmap(trimBmp, null, true, WallpaperManager.FLAG_LOCK);
                        }else{
                            wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_LOCK);

                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            }
            case "3": {
                // Pass when setting home screen
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Bitmap image = pairs[0].first;
                        int ImageWidth = image.getWidth();
                        int ImageHeight = image.getHeight();

                        if(fMap.get("rectangleFlg")){
                            Rect rect = new Rect(mMap.get("left"),mMap.get("top"),mMap.get("right"),mMap.get("bottom"));
                            wallpaperManager.setBitmap(pairs[0].first, rect, true, WallpaperManager.FLAG_SYSTEM);
                        }else if(!fMap.get("rectangleFlg") && ImageWidth > ImageHeight){
                            Map<String, Integer> map = mMap;
                            int device_width = mMap.get("deviceWidth");
                            int device_height = mMap.get("deviceHeight");
                            int centerX = mMap.get("centerX");
                            int startX = 0;
                            int set_bitmap_width = ImageWidth;

                            int Dw = wallpaperManager.getDesiredMinimumWidth();
                            int Dh = wallpaperManager.getDesiredMinimumHeight();
                            int Cw = (int) ((float) Dw / (float) Dh * ImageHeight);
                            int Ch = (int)((float)ImageHeight / (float)Dw * Dh);

                            double scale = const_mobileBaseSizeY/ImageHeight; // Calculate how many times the height of the image is the mobile base size
                            int system_correction = (int)(centerX * scale); // Correct center coordinate X according to resizing
                            int user_correction = (int)(mMap.get("userCorrection") * scale);

                            // Resize to mobile base size based on height
                            Bitmap tmpBitmap = Bitmap.createScaledBitmap(image,(int)(ImageWidth * scale),(int)const_mobileBaseSizeY, true);

                            // Calculate start position X and end point
                            startX = system_correction - (int)(const_mobileBaseSizeX/2) + user_correction;
                            set_bitmap_width = (int)const_mobileBaseSizeX;

                            // Change the starting position if the width overflows
                            if(startX < 0){
                                startX = 0;
                            }else if(startX+set_bitmap_width > tmpBitmap.getWidth()){
                                startX = tmpBitmap.getWidth() - (int)const_mobileBaseSizeX;
                            }else if(tmpBitmap.getWidth() < set_bitmap_width){
                                startX = 0;
                                set_bitmap_width = tmpBitmap.getWidth();
                            }

                            // Formatted to mobileSize(1040*1536) size and set as home
                            Bitmap trimBmp = Bitmap.createBitmap(tmpBitmap,startX,0,set_bitmap_width,tmpBitmap.getHeight(), null, true);
                            wallpaperManager.setBitmap(trimBmp, null, true, WallpaperManager.FLAG_SYSTEM);
                        }else{
                            wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_SYSTEM);
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            }
            case "4": {
                // Pass when setting lock/home(both) screen
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Rect rect = new Rect(mMap.get("left"),mMap.get("top"),mMap.get("right"),mMap.get("bottom"));
                        wallpaperManager.setBitmap(pairs[0].first, rect, true,
                                WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }
                break;
            }

        }
        return true;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        myMethod(aBoolean);
    }

    private void myMethod(Boolean result) {
        AsyncWallpaperPlugin.res.success(result);
    }

    public static Uri getImageContentUri(Context context, String absPath) {

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{absPath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        fixMediaDir();
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    void fixMediaDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        if (sdcard != null) {
            File mediaDir = new File(sdcard, "DCIM/Camera");
            if (!mediaDir.exists()) {
                mediaDir.mkdirs();
            }
        }

        if (sdcard != null) {
            File mediaDir = new File(sdcard, "Pictures");
            if (!mediaDir.exists()) {
                mediaDir.mkdirs();
            }
        }
    }
}
