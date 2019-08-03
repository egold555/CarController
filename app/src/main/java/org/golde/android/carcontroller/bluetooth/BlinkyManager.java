/*
 * Copyright (c) 2018, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.golde.android.carcontroller.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.BleManager;

public class BlinkyManager extends BleManager<BlinkyManagerCallbacks> {

	private BluetoothGattCharacteristic mLedCharacteristic;
	private boolean mSupported;

	public BlinkyManager(@NonNull final Context context) {
		super(context);
	}

	@NonNull
	@Override
	protected BleManagerGattCallback getGattCallback() {
		return mGattCallback;
	}

	@Override
	public void log(final int priority, @NonNull final String message) {
		// The priority is a Log.X constant, while the Logger accepts it's log levels.
		Log.d("Logger Manager", message);
	}

	@Override
	protected boolean shouldClearCacheWhenDisconnected() {
		return !mSupported;
	}



	/**
	 * BluetoothGatt callbacks object.
	 */
	private final BleManagerGattCallback mGattCallback = new BleManagerGattCallback() {
		@Override
		protected void initialize() {

		}

		@Override
		public boolean isRequiredServiceSupported(@NonNull final BluetoothGatt gatt) {
			final BluetoothGattService service = gatt.getService(BluetoothConstants.UUID_SERVICE);
			if (service != null)
			    Log.d("PETER", "service found!");
			else
			    Log.d( "PETER", "Service not found!");

			if (service != null) {
				mLedCharacteristic = service.getCharacteristic(BluetoothConstants.UUID_CHARASTIC);
			}

            if (mLedCharacteristic != null)
                Log.d("PETER", "characteristic found!");
            else
                Log.d( "PETER", "characteristic not found!");

			boolean writeRequest = false;
			if (mLedCharacteristic != null) {
				final int rxProperties = mLedCharacteristic.getProperties();
				writeRequest = (rxProperties & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0;

                    Log.d("PETER", "properties: " + rxProperties);
                    Log.d( "PETER", "writeRequest: " + writeRequest);
			}

			mSupported = mLedCharacteristic != null && writeRequest;

			if (mSupported)
                Log.d("PETER", "supported!");
            else
                Log.d( "PETER", "not supported!");
			return mSupported;
		}

		@Override
		protected void onDeviceDisconnected() {
			mLedCharacteristic = null;
		}
	};



	public void sendKeepAlive(){
		if(mLedCharacteristic == null){
			return;
		}
		writeCharacteristic(mLedCharacteristic, LedData.getKeepAlive()).enqueue();
	}

	public void sendBrightness(int brightness){
		if(mLedCharacteristic == null){
			return;
		}
		writeCharacteristic(mLedCharacteristic, LedData.getBrightness(brightness)).enqueue();
	}

	public void sendColor(int rr, int gg, int bb){
		if(mLedCharacteristic == null){
			return;
		}
		writeCharacteristic(mLedCharacteristic, LedData.getColor(rr, gg, bb)).enqueue();
	}

}
