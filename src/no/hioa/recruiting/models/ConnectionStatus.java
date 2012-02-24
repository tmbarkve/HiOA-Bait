package no.hioa.recruiting.models;

import java.io.Serializable;

public class ConnectionStatus implements Serializable {
	private static final long serialVersionUID = 4571272717547219185L;
	
	private boolean connected; 
	private String deviceAddress; 
	
	public ConnectionStatus() {
		this.connected = false; 
		this.deviceAddress = null; 
	}
	
	public ConnectionStatus(boolean isConnected, String deviceAddress) { 
		this.connected = isConnected; 
		this.deviceAddress = deviceAddress; 
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	
	public String toString() { 
		return "Is connected: " + connected + ". Device address: " + deviceAddress; 
	}
}
