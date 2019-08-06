package com.uta.picartstudiosandep.waterfallcallerledscreen.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore.Images.Media;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.uta.picartstudiosandep.waterfallcallerledscreen.ButtonAnim;
import com.uta.picartstudiosandep.waterfallcallerledscreen.HomePage_Color;
import com.uta.picartstudiosandep.waterfallcallerledscreen.ITelephony;
import com.uta.picartstudiosandep.waterfallcallerledscreen.ImageTouchSlider;
import com.uta.picartstudiosandep.waterfallcallerledscreen.ImageTouchSliderendca;
import com.uta.picartstudiosandep.waterfallcallerledscreen.R;
import com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color.RoundCorner_color;

import java.io.IOException;
import java.lang.reflect.Method;

@TargetApi(16)
public class CallService_Theme extends Service {
    private Camera camera;
    Context context;
    RelativeLayout incomingLay;
    private static final String TAG = null;
    String incomingNumber = " ";
    private boolean isFlashOn = true;
    private ITelephony telephonyService;
    private String blacklistednumber = "+458664455";
    Parameters params;
    LayoutParams paramsIn;
    SharedPreferences sharedpreferences;
    boolean show = false;
    public boolean stop = false;
    View viewIn;
    WindowManager windowManager;

    class Accept_receiver implements Runnable {
        final Context contextAc;

        Accept_receiver(Context context) {
            this.contextAc = context;
        }

        @SuppressLint("WrongConstant")
        public void run() {
            try {
                if (VERSION.SDK_INT >= 21) {
                    for (MediaController mediaController : ((MediaSessionManager) getApplicationContext().getSystemService("media_session")).getActiveSessions(new ComponentName(this.contextAc, NotificationService.class))) {
                        if ("com.android.server.telecom".equals(mediaController.getPackageName())) {
                            mediaController.dispatchMediaButtonEvent(new KeyEvent(0, 79));
                            mediaController.dispatchMediaButtonEvent(new KeyEvent(1, 79));
                            return;
                        }
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    class C02181 implements OnClickListener {

        class C02171 implements Runnable {
            C02171() {
            }

            public void run() {
                try {
                    declinePhone(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stopService(new Intent(context, CallService_Theme.class));
            }
        }


        public void onClick(View view) {
            releaseCamera();
            new Handler().postDelayed(new C02171(), 300);
        }
    }

    class C02202 implements OnClickListener {

        class C02191 implements Runnable {
            C02191() {
            }

            public void run() {

            }
        }

        public void onClick(View view) {
            releaseCamera();
            if (VERSION.SDK_INT >= 23) {
                new Thread(new C0266O(CallService_Theme.this)).start();
                new Thread(new Accept_receiver(CallService_Theme.this)).start();
                return;
            }
            CallService_Theme.acceptCall(getApplicationContext());
            answerCall1();
            stopService(new Intent(getApplicationContext(), CallService_Theme.class));
        }
    }

    class C02245 implements Runnable {

        class C02231 extends Thread {
            C02231() {
            }

            public void run() {
                try {
                    getCamera();
                    while (!stop) {
                        if (isFlashOn) {
                            turnOffFlash();
                        } else {
                            turnOnFlash();
                        }
                        int i = sharedpreferences.getInt("flashspeed", 0);
                        if (i >= 200) {
                            i = 200;
                        }
                        sleep((long) i);
                    }
                    releaseCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        C02245() {
        }

        public void run() {
            if (sharedpreferences.getBoolean("flash", false)) {
                new C02231().start();
            }
        }
    }

    class C0266O implements Runnable {
        final CallService_Theme is_sc1;

        C0266O(CallService_Theme my_ColorCallService) {
            this.is_sc1 = my_ColorCallService;
        }

        public void run() {
            try {
                CallService_Theme.acceptCall(context);
                answerCall1();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stopService(new Intent(context, CallService_Theme.class));
        }
    }

    class CallListener extends PhoneStateListener {
        private Context context;

        public CallListener(Context context) {
            this.context = context;
        }

        public void onCallStateChanged(int i, String str) {
            incomingNumber = str;
            if (i != 0) {
                incomingNumber = str;
                if (!show) {
                    incomingDisplay();
                    show = true;
                    return;
                }
            }
            if (incomingLay != null) {
                show = false;
                incomingLay.setVisibility(View.GONE);
            }
            releaseCamera();
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return START_STICKY;
    }

    public void startForeGround() {
        startForeground(11, new Notification.Builder(this).setSmallIcon(R.drawable.appicon).setContentTitle(getString(R.string.app_name)).setContentText("Service running...").setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, HomePage_Color.class), 0)).build());
    }

    public void onCreate() {
        super.onCreate();
        this.context = this;
        this.sharedpreferences = this.context.getSharedPreferences("MyPrefs", 0);
        this.incomingNumber = BroadcastReceiverService.incomingNumber;
        incomingDisplay();
    }

    @SuppressLint("WrongConstant")
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
        if (this.viewIn != null) {
            ((WindowManager) getSystemService("window")).removeView(this.viewIn);
        }
    }

    public void getCamera() {
        releaseCamera();
        if (this.camera == null) {
            try {
                this.camera = Camera.open();
                this.params = this.camera.getParameters();
            } catch (RuntimeException e) {
            }
        }
    }

    public void turnOnFlash() {
        if (!this.isFlashOn && this.camera != null && this.params != null) {
            this.params = this.camera.getParameters();
            this.params.setFlashMode("torch");
            this.camera.setParameters(this.params);
            this.camera.startPreview();
            this.isFlashOn = true;
        }
    }

    public void turnOffFlash() {
        if (this.isFlashOn && this.camera != null && this.params != null) {
            this.params = this.camera.getParameters();
            this.params.setFlashMode("off");
            this.camera.setParameters(this.params);
            this.camera.stopPreview();
            this.isFlashOn = false;
        }
    }

    public void releaseCamera() {
        if (this.camera != null) {
            this.camera.release();
            this.camera = null;
        }
    }

    @SuppressLint("WrongConstant")
    public void incomingDisplay() {
        int i;
        this.windowManager = (WindowManager) getSystemService("window");
        if (VERSION.SDK_INT >= 26) {
            i = 2038;
        } else {
            i = 2010;
        }
        this.paramsIn = new LayoutParams(-1, -1, i, 19399552, -3);
        AudioManager audioManager = (AudioManager) this.context.getSystemService("audio");
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService("notification");
        if (VERSION.SDK_INT >= 24 && !notificationManager.isNotificationPolicyAccessGranted()) {
            startActivity(new Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"));
        } else if (this.sharedpreferences.getBoolean("vibrate", false)) {
            audioManager.setRingerMode(1);
        } else {
            audioManager.setStreamVolume(3, audioManager.getStreamMaxVolume(3), 0);
            audioManager.setRingerMode(2);
        }
        this.viewIn = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.activity_preview, null);
        this.incomingLay = (RelativeLayout) this.viewIn.findViewById(R.id.incomingLay);
        TextView textView = (TextView) this.viewIn.findViewById(R.id.nameTV);
        TextView textView2 = (TextView) this.viewIn.findViewById(R.id.numberTV);
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fontface/fontbold.otf");
        textView.setTypeface(createFromAsset);
        textView2.setTypeface(createFromAsset);
//        CharSequence contactName = getContactName(this.incomingNumber, this.context);
//        if (contactName.equals("")) {
//            contactName = "Unknown";
//        }
//        textView.setText(contactName);
        if (this.incomingNumber != null) {
            textView2.setText(this.incomingNumber);
        }
        RoundCorner_color my_Roundcorner = (RoundCorner_color) this.viewIn.findViewById(R.id.photoIV);
        my_Roundcorner.setImageBitmap(getContactsPhoto(this.incomingNumber));
        ImageTouchSlider anscall = this.viewIn.findViewById(R.id.slider_anscall);
        ImageTouchSliderendca endcall = this.viewIn.findViewById(R.id.sliderto_endcall);

        anscall.setOnUnlockListener(new ImageTouchSlider.OnUnlockListener() {
            @Override
            public void onUnlock() {
                releaseCamera();
                if (VERSION.SDK_INT >= 23) {
                    new Thread(new C0266O(CallService_Theme.this)).start();
                    new Thread(new Accept_receiver(CallService_Theme.this)).start();
                    return;
                }
                CallService_Theme.acceptCall(getApplicationContext());
                answerCall1();
                stopService(new Intent(getApplicationContext(), CallService_Theme.class));
            }
        });
        endcall.setOnUnlockListener(new ImageTouchSliderendca.OnUnlockListener() {
            @Override
            public void onUnlock() {
                releaseCamera();
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        try {
                            declinePhone(context);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stopService(new Intent(context, CallService_Theme.class));
                    }
                }, 300);
            }
        });

//        imageView.setOnClickListener(new C02181());
//        imageView2.setOnClickListener(new C02202());
        VideoView videoView = (VideoView) this.viewIn.findViewById(R.id.video);
        String videourl = this.sharedpreferences.getString("videouri", null);
        final VideoView videoView2;
        if (videourl == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android.resource://");
            stringBuilder.append(getPackageName());
            stringBuilder.append("/");
            stringBuilder.append(R.raw.screen_1);
            videoView.setVideoPath(stringBuilder.toString());
            videoView.start();
            videoView2 = videoView;
            videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView2.start();
                }
            });
        } else {
            videoView.setVideoPath(videourl);
            videoView.start();
            videoView2 = videoView;
            videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    videoView2.start();
                }
            });
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.context, R.anim.bounce);
//        imageView2.startAnimation(loadAnimation);
//        imageView.startAnimation(loadAnimation);
        my_Roundcorner.setLayoutParams(new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 324) / 1080, (getResources().getDisplayMetrics().widthPixels * 324) / 1080));
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels * 218) / 1080, (getResources().getDisplayMetrics().heightPixels * 218) / 1920);
//        imageView2.setLayoutParams(layoutParams);
//        imageView.setLayoutParams(layoutParams);
        this.windowManager.addView(this.viewIn, this.paramsIn);
        new Handler().postDelayed(new C02245(), 1200);
    }

    public String getContactName(String str, Context context) {
        Cursor cursor = context.getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name"}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                str = cursor.getString(0);
            }
            cursor.close();
        }
        return str;
    }

    public Bitmap getContactsPhoto(String str) {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.dimen.profile_picture_size);
        Cursor cursor = getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), null, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex("photo_uri"));
            if (string != null) {
                try {
                    decodeResource = Media.getBitmap(getContentResolver(), Uri.parse(string));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return decodeResource;
    }


    public static void acceptCall(Context context) {
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
        context.sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
    }


    private void endCallIfBlocked(String callingNumber) {
        try {

            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(tm);
            telephonyService = (ITelephony) m.invoke(tm);
            //
            telephonyService.silenceRinger();
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void declinePhone(Context context) {

        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("unable", "msg cant dissconect call....");

        }


//        ITelephony telephonyService;
//        TelephonyManager telephony = (TelephonyManager)
//                context.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            Class c = Class.forName(telephony.getClass().getName());
//            Method m = c.getDeclaredMethod("getITelephony");
//            m.setAccessible(true);
////            SessionManager.getInstance(context).setBlockStatusAllow("BLOCKED");
//            telephonyService = (ITelephony) m.invoke(telephony);
//            telephonyService.silenceRinger();
//            telephonyService.endCall();
//            Toast.makeText(context, "Call Ended", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("ERROR", e.toString());
//            Toast.makeText(context, "Call Not Ended" + e, Toast.LENGTH_LONG).show();
//        }

////        try {
//            Class<?> telephonyClass = Class.forName("com.android.internal.telephony.ITelephony");
//            Class<?> telephonyStubClass = telephonyClass.getClasses()[0];
//            Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
//            Class<?> serviceManagerNativeClass = Class.forName("android.os.ServiceManagerNative");
//            Method getService = serviceManagerClass.getMethod("getService", new Class[]{String.class});
//            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", new Class[]{IBinder.class});
//            new Binder().attachInterface(null, "fake");
//            Binder tmpBinder = new Binder();
//            IBinder retbinder = (IBinder) getService.invoke(tempInterfaceMethod.invoke(null, new Object[]{tmpBinder}), new Object[]{"phone"});
//            telephonyClass.getMethod("endCall", new Class[0]).invoke(telephonyStubClass.getMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{retbinder}), new Object[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
    }

    public void answerCall1() {
        try {
            Runtime runtime = Runtime.getRuntime();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("input keyevent ");
            stringBuilder.append(Integer.toString(79));
            runtime.exec(stringBuilder.toString());
        } catch (IOException e) {
            String str = "android.permission.CALL_PRIVILEGED";
            Intent putExtra = new Intent("android.intent.action.MEDIA_BUTTON").putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, 79));
            Intent putExtra2 = new Intent("android.intent.action.MEDIA_BUTTON").putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
            sendOrderedBroadcast(putExtra, str);
            sendOrderedBroadcast(putExtra2, str);
        }
    }
}
