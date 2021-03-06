package com.example.super_yu.myexample.usb2serialp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参考
 * https://github.com/felHR85/UsbSerial
 */
public class UsbSerialp2Activity extends Activity {

    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.show_content)
    TextView showContent;

    LoraMessageService.MyBindler mMyBinder;
    UsbServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_usb_serialp2);
        ButterKnife.bind(this);
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoraMessageService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(LoraMessageService.ACTION_NO_USB);
        filter.addAction(LoraMessageService.ACTION_USB_DISCONNECTED);
        filter.addAction(LoraMessageService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(LoraMessageService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);

        // 串口操作
        Intent serialPortIntent = new Intent(this, LoraMessageService.class);
        connection = new UsbServiceConnection();
        bindService(serialPortIntent, connection, BIND_AUTO_CREATE);

//        ImmersionBar mImmersionBar = ImmersionBar.with(UsbSerialp2Activity.this);
//        mImmersionBar.init();
//        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();

        clickThread.start();
    }

    @OnClick(R.id.send)
    void send() {
        Toast.makeText(UsbSerialp2Activity.this, "点击了", Toast.LENGTH_SHORT).show();
        if (mMyBinder != null) {
            int i = mMyBinder.write("nihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihao" +
                    "nihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihaonihao");
            Toast.makeText(UsbSerialp2Activity.this, " i = " + i, Toast.LENGTH_SHORT).show();
        }
    }

    private class UsbServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (LoraMessageService.MyBindler) service;
            mMyBinder.setLoraMessageCallBackListener(new LoraMessageService.LoraMessageCallBack() {
                @Override
                public void onLoraMessage_Serial_Port(String message) {
                    Toast.makeText(UsbSerialp2Activity.this, message + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoraMessage_Sycn_Read(String message) {
                    Toast.makeText(UsbSerialp2Activity.this, message + "！", Toast.LENGTH_SHORT).show();
                    Log.d("tt", message);
                }

                @Override
                public void onCts_Change(boolean state) {
                    Toast.makeText(UsbSerialp2Activity.this, state + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDsr_Change(boolean state) {
                    Toast.makeText(UsbSerialp2Activity.this, state + "", Toast.LENGTH_SHORT).show();
                }
            });
            mMyBinder.readBufferListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyBinder = null;
        }
    }

    /**
     * Notifications from UsbService will be received here.
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case LoraMessageService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case LoraMessageService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case LoraMessageService.ACTION_NO_USB: // NO USB CONNECTED
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case LoraMessageService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case LoraMessageService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    Thread clickThread = new Thread() {

        @Override
        public void run() {
            super.run();
            try {

                Thread.sleep(10000);

                Instrumentation mInst = new Instrumentation();
//                //按键事件
//                mInst.sendKeyDownUpSync(KeyEvent.KEYCODE_A);
                //触摸按下
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1138, 1216, 0));
                //触摸抬起
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 1138, 1216, 0));

                Thread.sleep(3000);
                Instrumentation mInsts = new Instrumentation();
                //按键事件
//                mInsts.sendKeyDownUpSync(KeyEvent.KEYCODE_A);
                //触摸按下
                mInsts.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1138, 1216, 0));
                //触摸抬起
                mInsts.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 1138, 1216, 0));

            } catch (Exception e) {

            }

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Toast.makeText(UsbSerialp2Activity.this, "x = " + ev.getX() + " y = " + ev.getY(), Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
        unbindService(connection);
    }
}
