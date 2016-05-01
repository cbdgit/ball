package com.bosong.ball_light.model.bluetooth;

import java.util.List;
import java.util.Locale;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BlutoothOpera {
	private Activity context = null;
	private BluetoothManager mBluetoothManager = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothDevice mBluetoothDevice = null;
	private BluetoothGatt mBluetoothGatt = null;
	private String mDeviceAddress = "";
	private boolean mConnected = false;

	private List<BluetoothGattService> mBluetoothGattServices = null;
	/* callback object through which we are returning results to the caller */
	private BleWrapperUiCallbacks mUiCallback = null;
	/* define NULL object for UI callbacks */
	private static final BleWrapperUiCallbacks NULL_CALLBACK = new BleWrapperUiCallbacks.Null();

	public BlutoothOpera(Activity context) {
		this.context = context;
	}
	/* creates BleWrapper object, set its parent activity and callback object */
	public BlutoothOpera(Activity activity, BleWrapperUiCallbacks callback) {
		this.context = activity;
		mUiCallback = callback;
		if(mUiCallback == null) mUiCallback = NULL_CALLBACK;
	}

	public BluetoothManager getManager() {
		return mBluetoothManager;
	}

	public BluetoothAdapter getAdapter() {
		return mBluetoothAdapter;
	}

	public BluetoothDevice getDevice() {
		return mBluetoothDevice;
	}

	public BluetoothGatt getGatt() {
		return mBluetoothGatt;
	}

	public List<BluetoothGattService> getCachedServices() {
		return mBluetoothGattServices;
	}

	public boolean isConnected() {
		return mConnected;
	}

	/* run test and check if this device has BT and BLE hardware available */
	// 检查手机设备是否支持蓝牙
	@SuppressLint("NewApi")
	public boolean checkBleHardwareAvailable() {
		// First check general Bluetooth Hardware:
		// get BluetoothManager...
		final BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		if (manager == null)
			return false;
		// .. and then get adapter from manager
		final BluetoothAdapter adapter = manager.getAdapter();
		if (adapter == null)
			return false;

		// and then check if BT LE is also available
		boolean hasBle = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
		return hasBle;
	}

	/*
	 * before any action check if BT is turned ON and enabled for us call this
	 * in onResume to be always sure that BT is ON when Your application is put
	 * into the foreground
	 */
	@SuppressLint("NewApi")
	public boolean isBtEnabled() {
		final BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		if (manager == null)
			return false;

		final BluetoothAdapter adapter = manager.getAdapter();
		if (adapter == null)
			return false;

		return adapter.isEnabled();
	}

	// initialize BLE and get BT Manager & Adapter
	public boolean initialize() {
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				return false;
			}
		}

		if (mBluetoothAdapter == null)
			mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			return false;
		}
		return true;
	}

	/* start scanning for BT LE devices around */
	public void startScanning() {
		// 在这个mDeviceFoundCallback系统回调里面，处理系统扫描到的设备信息
		mBluetoothAdapter.startLeScan(mDeviceFoundCallback);
	}

	/* stops current scanning */
	public void stopScanning() {
		mBluetoothAdapter.stopLeScan(mDeviceFoundCallback);
	}

	/* defines callback for scanning results */
	private BluetoothAdapter.LeScanCallback mDeviceFoundCallback = new BluetoothAdapter.LeScanCallback() {
		// 系统扫描到的设备数据信息返回
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
			// 进行处理系统返回来的数据，
			// 自己写的一个回调mUiCallback,处理系统返回的数据。
			// mUiCallback.uiDeviceFound(device, rssi, scanRecord);
			Log.d("设备地址deviceaddress", "" + device.getAddress());
			Log.d("设备名称devicename", "" + device.getName());
			Log.d("信号rssi", "" + rssi);
			Log.d("scanRecord", "" + scanRecord);
			Log.d("sacnDevice", "这是扫描到的设备");
			connect(device.getAddress());

		}
	};

	/* connect to the device with specified address */
	// / 根据地址连接蓝牙设备
	public boolean connect(final String deviceAddress) {
		if (mBluetoothAdapter == null || deviceAddress == null)
			return false;
		mDeviceAddress = deviceAddress;

		// check if we need to connect from scratch or just reconnect to
		// previous device
		if (mBluetoothGatt != null && mBluetoothGatt.getDevice().getAddress().equals(deviceAddress)) {
			// just reconnect
			return mBluetoothGatt.connect();
		} else {
			// connect from scratch
			// get BluetoothDevice object for specified address
			mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
			if (mBluetoothDevice == null) {
				// we got wrong address - that device is not available!
				return false;
			}
			// auto connect with remote device
			mBluetoothGatt = mBluetoothDevice.connectGatt(context, false, mBleCallback);
		}
		return true;
	}

	/* disconnect the device. It is still possible to reconnect to it later with this Gatt client */
	public void diconnect() {
		if(mBluetoothGatt != null) mBluetoothGatt.disconnect();
		mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
	}

	/* callbacks called for any action on particular Ble Device */
	private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

			if (newState == BluetoothProfile.STATE_CONNECTED) {
				mConnected = true;
				// mUiCallback.uiDeviceConnected(mBluetoothGatt,
				// mBluetoothDevice);

				// in our case we would also like automatically to call for
				// services discovery
				// 我们也会自动调用发现的服务
				//	gatt.discoverServices();
				startServicesDiscovery();
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mConnected = false;

			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// now, when services discovery is finished, we can call
				// getServices() for Gatt
				// 当发现服务完成后,我们可以调用getservice()
				Log.d("onServicesDiscovered", "这个可能是特服务发现的地方");
				getSupportedServices();
			}
		}
		/*
		 * gets services and calls UI callback to handle them before calling
		 * getServices() make sure service discovery is finished!
		 */
		public void getSupportedServices() {
			if (mBluetoothGattServices != null && mBluetoothGattServices.size() > 0)
				mBluetoothGattServices.clear();
			// keep reference to all services in local array:
			if (mBluetoothGatt != null){

				mBluetoothGattServices = mBluetoothGatt.getServices();
				BluetoothGattService service =  mBluetoothGattServices.get(mBluetoothGattServices.size()-1);
				List<BluetoothGattCharacteristic> chars = null;
				chars = service.getCharacteristics();
				BluetoothGattCharacteristic character=chars.get(chars.size()-1);
				//String newValue = "00000099000000";
				//byte[] dataToWrite = parseHexStringToBytes(newValue);

				// first set it locally....
				//byte [] dataToWrite={0x00,0x00,(byte) 0xFF,0x00,0x00,0x00,0x00};
				//byte [] dataToWrite={0x00,0x00,0X00,(byte)0xFF,0x00,0x00,0x00};
				byte [] dataToWrite={0x00,0x00,0x00,0x00,(byte) 0xFF,0x00,0x00};

				character.setValue(dataToWrite);
				// ... and then "commit" changes to the peripheral
				mBluetoothGatt.writeCharacteristic(character);
			}

		}

		public byte[] parseHexStringToBytes(final String hex) {
			String tmp = hex.substring(2).replaceAll("[^[0-9][a-f]]", "");
			byte[] bytes = new byte[tmp.length() / 2]; // every two letters in the string are one byte finally

			String part = "";

			for(int i = 0; i < bytes.length; ++i) {
				part = "0x" + tmp.substring(i*2, i*2+2);
				bytes[i] = Long.decode(part).byteValue();
			}

			return bytes;
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			Log.d("onCharacteristicRead", "这个可能是特征值读取的地方");
			// we got response regarding our request to fetch characteristic
			// value
			/*
			 * if (status == BluetoothGatt.GATT_SUCCESS) { // and it success, so
			 * we can get the value getCharacteristicValue(characteristic); }
			 */
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			Log.d("onCharacteristicChanged", "这个可能是特征值改变的地方");
			// characteristic's value was updated due to enabled notification,
			// lets get this value
			// the value itself will be reported to the UI inside
			// getCharacteristicValue
			/*
			 * getCharacteristicValue(characteristic); // also, notify UI that
			 * notification are enabled for particular characteristic
			 * mUiCallback.uiGotNotification(mBluetoothGatt, mBluetoothDevice,
			 * mBluetoothSelectedService, characteristic);
			 */
		}


		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			Log.d("onCharacteristicWrite", "这个可能是发送数据的地方");
			/*
			 * String deviceName = gatt.getDevice().getName(); String
			 * serviceName =
			 * BleNamesResolver.resolveServiceName(characteristic.getService().
			 * getUuid().toString().toLowerCase(Locale.getDefault())); String
			 * charName =
			 * BleNamesResolver.resolveCharacteristicName(characteristic.getUuid
			 * ().toString().toLowerCase(Locale.getDefault())); String
			 * description = "Device: " + deviceName + " Service: " +
			 * serviceName + " Characteristic: " + charName;
			 *
			 * // we got response regarding our request to write new value to
			 * the characteristic // let see if it failed or not if(status ==
			 * BluetoothGatt.GATT_SUCCESS) {
			 * mUiCallback.uiSuccessfulWrite(mBluetoothGatt, mBluetoothDevice,
			 * mBluetoothSelectedService, characteristic, description); } else {
			 * mUiCallback.uiFailedWrite(mBluetoothGatt, mBluetoothDevice,
			 * mBluetoothSelectedService, characteristic, description +
			 * " STATUS = " + status); }
			 */
		};

	};



	/*
	 * request to discover all services available on the remote devices results
	 * are delivered through callback object
	 */
	public void startServicesDiscovery() {
		if (mBluetoothGatt != null)
			mBluetoothGatt.discoverServices();
	}

	public static void sacanDevice(Context context) {
		Toast.makeText(context, "scandevice", Toast.LENGTH_SHORT).show();
	}

	public static void WirteData() {

	}

}
