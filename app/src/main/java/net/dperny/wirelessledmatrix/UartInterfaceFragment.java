package net.dperny.wirelessledmatrix;

import android.bluetooth.BluetoothGattService;
import android.app.Fragment;
import android.util.Log;

import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.ble.BleUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

public class UartInterfaceFragment extends Fragment {
    // Log
    private final static String TAG = UartInterfaceFragment.class.getSimpleName();

    // Service Constants
    public static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_RX = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_TX = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_DFU = "00001530-1212-EFDE-1523-785FEABCD123";
    public static final int kTxMaxCharacters = 20;

    // Data
    protected BleManager mBleManager;
    protected BluetoothGattService mUartService;

    // region Send Data to UART
    protected void sendData(String text) {
        final byte[] value = text.getBytes(Charset.forName("UTF-8"));
        sendData(value);
    }

    protected void sendData(byte[] data) {
        if(mUartService != null) {
            // Split the value into chunks (UART service has a max number of characters that can be written)
            for(int i = 0; i < data.length; i += kTxMaxCharacters) {
                final byte[] chunk = Arrays.copyOfRange(data, i, Math.min(i + kTxMaxCharacters, data.length));
                mBleManager.writeService(mUartService, UUID_TX, chunk);
            }
        } else {
            Log.w(TAG, "Uart Service not discovered. Unable to send data.");
        }
    }

    protected void sendDataWithCRC(byte[] data) {
        // Calculate checksum
        byte checksum = 0;
        for (int i = 0; i < data.length; i++) {
            checksum += data[i];
        }
        checksum = (byte) (~checksum);       // Invert

        // Add crc to data
        byte dataCrc[] = new byte[data.length + 1];
        System.arraycopy(data, 0, dataCrc, 0, data.length);
        dataCrc[data.length] = checksum;

        // Send it
        Log.d(TAG, "Send to UART: " + BleUtils.bytesToHexWithSpaces(dataCrc));
        sendData(dataCrc);
    }
}
