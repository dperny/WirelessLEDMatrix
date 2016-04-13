package net.dperny.wirelessledmatrix;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adafruit.bluefruit.le.connect.ble.BleManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BleStatusFragment.OnFragmentInteractionListener} interface
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

    private OnFragmentInteractionListener mListener;

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

        mBleManager = BleManager.getInstance(this.getContext());

        BluetoothDevice device = mBleManager.getConnectedDevice();
        if(device != null) {
            // set some properties of the device
        } else {
            // set the default values on this screen
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_status, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        // TODO: change some screen shit when we disconnect
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
