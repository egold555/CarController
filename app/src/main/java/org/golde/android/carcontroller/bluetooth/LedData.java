package org.golde.android.carcontroller.bluetooth;

import no.nordicsemi.android.ble.data.Data;

public class LedData {

    public static Data getColor(int rr, int gg, int bb){
        return new Data(new byte[]{(byte) 0x33, (byte) 0x05, (byte) 0x02, (byte) rr, (byte) gg, (byte) bb, (byte) 0x00, (byte) 0xFF, (byte) 0xAE, (byte) 0x54, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) (0x31 ^ rr ^ gg ^ bb)});
    }

    public static Data getBrightness(int brightness){
        return new Data( new byte[]{(byte) 0x33, (byte) 0x04, (byte) brightness, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) (0x33 ^ 0x04 ^ brightness)});
    }

    public static Data getKeepAlive(){
        return new Data(new byte[]{(byte) 0xaa, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xab});
    }
}
