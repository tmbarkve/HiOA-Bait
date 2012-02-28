package no.hioa.recruiting.models;

import java.io.Serializable;

/**
 * The Class ArduinoConnection.
 */
public class ArduinoConnection implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4571272717547219185L;
	
	/** The connected boolean. */
	private boolean connected; 
	
	/** The device address. */
	private String deviceAddress; 
	
	/**
	 * The defualt constructor, instantiates a arduino connection object
	 * with the connection set to false, and no device address.
	 */
	public ArduinoConnection() {
		this.connected = false; 
		this.deviceAddress = null; 
	}
	
	/**
	 * Instantiates a new arduino connection.
	 *
	 * @param isConnected boolean to represent a connection, true if connected.
	 * @param deviceAddress the device address
	 */
	public ArduinoConnection(boolean isConnected, String deviceAddress) { 
		this.connected = isConnected; 
		this.deviceAddress = deviceAddress; 
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Sets the connected status
	 *
	 * @param connected the new connected
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * Gets the device address.
	 *
	 * @return the device address
	 */
	public String getDeviceAddress() {
		return deviceAddress;
	}

	/**
	 * Sets the device address.
	 *
	 * @param deviceAddress the new device address
	 */
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() { 
		return "Connection: Is connected: " + connected 
				+ ". Device address: " + deviceAddress; 
	}
}
