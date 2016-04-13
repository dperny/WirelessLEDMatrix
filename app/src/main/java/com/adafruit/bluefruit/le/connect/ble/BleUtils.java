package com.adafruit.bluefruit.le.connect.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Created by drewe on 4/12/2016.
 */
public class BleUtils {
    private final static String TAG = BleUtils.class.getSimpleName();

    public static final int STATUS_BLE_ENABLED = 0;
    public static final int STATUS_BLUETOOTH_NOT_AVAILABLE = 1;
    public static final int STATUS_BLE_NOT_AVAILABLE = 2;
    public static final int STATUS_BLUETOOTH_DISABLED = 3;

    public static BluetoothAdapter getBluetoothAdapter(Context context) {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager == null) {
            return null;
        } else {
            return bluetoothManager.getAdapter();
        }
    }

    public static String bytesToHexWithSpaces(byte[] bytes) {
        StringBuilder newString = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            String byteHex = String.format("%02X", (byte)bytes[i]);
            newString.append(byteHex).append(" ");

        }
        return newString.toString().trim();

    }
}
