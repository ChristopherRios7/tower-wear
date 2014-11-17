package com.o3dr.android.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;

import com.o3dr.services.android.lib.drone.connection.ConnectionResult;
import com.o3dr.services.android.lib.model.IDroidPlannerApiCallback;

/**
 * Created by fhuya on 10/29/14.
 */
public final class DPApiCallback extends IDroidPlannerApiCallback.Stub {

	private static final String CLAZZ_NAME = DPApiCallback.class.getName();

	public static final String ACTION_DRONE_EVENT = CLAZZ_NAME + ".ACTION_DRONE_EVENT";
    public static final String EXTRA_DRONE_EVENT = "extra_drone_event";

	public static final String ACTION_DRONE_CONNECTION_FAILED = CLAZZ_NAME
			+ ".ACTION_DRONE_CONNECTION_FAILED";

	public static final String EXTRA_CONNECTION_FAILED_ERROR_CODE = "extra_connection_failed_error_code";

	public static final String EXTRA_CONNECTION_FAILED_ERROR_MESSAGE = "extra_connection_failed_error_message";

	private final LocalBroadcastManager lbm;

	public DPApiCallback(Context context) {
		lbm = LocalBroadcastManager.getInstance(context);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) throws RemoteException {
		lbm.sendBroadcast(new Intent(ACTION_DRONE_CONNECTION_FAILED)
                .putExtra(EXTRA_CONNECTION_FAILED_ERROR_CODE, result.getErrorCode())
                .putExtra(EXTRA_CONNECTION_FAILED_ERROR_MESSAGE, result.getErrorMessage()));
	}

	@Override
	public void onDroneEvent(String event, Bundle eventExtras) throws RemoteException {
		lbm.sendBroadcast(new Intent(ACTION_DRONE_EVENT));

        final Intent droneIntent = new Intent(event);
        if(eventExtras != null)
            droneIntent.putExtras(eventExtras);
		lbm.sendBroadcast(droneIntent);
	}
}
