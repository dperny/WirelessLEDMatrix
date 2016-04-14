package net.dperny.wirelessledmatrix;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adafruit.bluefruit.le.connect.ble.BleManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BleStatusFragment.OnStatusFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BleStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BleStatusFragment extends Fragment implements BleManager.BleManagerListener {
    // Log
    private final String TAG = BleStatusFragment.class.getSimpleName();

    //Constants

    // Data
    private BleManager mBleManager;
    private BluetoothDevice mDevice;

    private OnStatusFragmentInteractionListener mListener;

    public BleStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BleStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BleStatusFragment newInstance() {
        BleStatusFragment fragment = new BleStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get the BLE manager
        mBleManager = BleManager.getInstance(this.getContext());
        mBleManager.setBleListener(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_status, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDevice = mBleManager.getConnectedDevice();
        if(mDevice != null) {
            View v = getView();
            if(v != null) {
                TextView name = (TextView) v.findViewById(R.id.device_name);
                TextView addr = (TextView) v.findViewById(R.id.device_address);
                name.setText(mDevice.getName());
                addr.setText(mDevice.getAddress());
                // TODO: figure out how to get device rssi
            }
        } else {
            Log.d(TAG, "No device connected");
            // no need to alter anything. default values work just fine
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // remove this as the listener, because it does not exist anymore
        mBleManager.setBleListener(null);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onStatusFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStatusFragmentInteractionListener) {
            mListener = (OnStatusFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnControlFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStatusFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStatusFragmentInteraction();
    }

    @Override
    public void onConnected() {
        // TODO: method body
    }

    @Override
    public void onConnecting() {
        // TODO: method body
    }

    @Override
    public void onDisconnected() {
        mDevice = mBleManager.getConnectedDevice();
        View v = getView();
        if(v != null) {
            TextView name = (TextView) v.findViewById(R.id.device_name);
            TextView addr = (TextView) v.findViewById(R.id.device_address);
            if (mDevice != null) {
                name.setText(mDevice.getName());
                addr.setText(mDevice.getAddress());
            } else {
                name.setText(R.string.no_device_name);
                addr.setText(R.string.no_device_address);
            }
        }
        Toast toast = Toast.makeText(this.getContext(), R.string.device_connecting,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onServicesDiscovered() { }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) { }

    @Override
    public void onDataAvailable(BluetoothGattDescriptor descriptor) { }

    @Override
    public void onReadRemoteRssi(int rssi) {
        // TODO: periodically ask for remote rssi and display that on the status screen
    }


}
