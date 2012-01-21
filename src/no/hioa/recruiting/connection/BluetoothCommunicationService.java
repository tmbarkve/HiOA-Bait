package no.hioa.recruiting.connection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BluetoothCommunicationService {
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 2;  // now connected to a remote device

}
