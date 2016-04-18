package net.dperny.wirelessledmatrix;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adafruit.bluefruit.le.connect.ble.BleManager;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnControlFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BleControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BleControlFragment extends UartInterfaceFragment implements BleManager.BleManagerListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG = BleControlFragment.class.getSimpleName();

    private OnControlFragmentInteractionListener mListener;

    private ListView listView;
    private ArrayAdapter<String> mCommandAdapter;

    // Commands
    private static final String COMMANDS[] = {
            "PHONE"
            , "MUTE"
            , "UNMUTE"
            , "MUSIC"
            , "NEWMSG"
            , "ERROR"
            , "WIFI1"
            , "WIFI2"
            , "WIFI3"
            , "BLUETOOTH"
    };

    public BleControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BleControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BleControlFragment newInstance() {
        BleControlFragment fragment = new BleControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBleManager = BleManager.getInstance(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBleManager.setBleListener(this);
        mDevice = mBleManager.getConnectedDevice();
        onServicesDiscovered();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ble_control, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onControlFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnControlFragmentInteractionListener) {
            mListener = (OnControlFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnControlFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        listView = (ListView) getView().findViewById(R.id.command_list);
        mCommandAdapter = new ArrayAdapter<String>(getContext(), R.layout.listitem_command, COMMANDS);
        listView.setAdapter(mCommandAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String command = mCommandAdapter.getItem(position);
                if(mDevice != null) {
                    sendData(command);
                } else {
                    Log.w(TAG, "No device connected");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mBleManager.setBleListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onServicesDiscovered() {
        Log.d(TAG, "Services discovered callback");
        mUartService = mBleManager.getGattService(UUID_SERVICE);
        mBleManager.enableNotification(mUartService, UUID_RX, true);
    }

    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onDataAvailable(BluetoothGattDescriptor descriptor) {

    }

    @Override
    public void onReadRemoteRssi(int rssi) {

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
    public interface OnControlFragmentInteractionListener {
        // TODO: Update argument type and name
        void onControlFragmentInteraction();
    }
}
