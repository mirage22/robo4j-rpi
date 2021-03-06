/*
 * Copyright (C) 2014-2016, Marcus Hirt
 * 
 * Robo4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robo4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robo4J. If not, see <http://www.gnu.org/licenses/>.
 */
package com.robo4j.rpi.i2c;

import java.io.IOException;
import java.util.logging.Logger;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

/**
 * Abstract super class for I2C devices.
 * 
 * @author Marcus Hirt
 */
public abstract class AbstractI2CDevice {
	private final int bus;
	private final int address;
	protected final I2CDevice i2cDevice;

	/**
	 * Creates an I2C device.
	 * 
	 * @param bus
	 *            the I2C bus to use.
	 * @param address
	 *            the address to use.
	 * 
	 * @see I2CBus
	 * 
	 * @throws IOException
	 *             if there was communication problem
	 * @throws UnsupportedBusNumberException 
	 */
	public AbstractI2CDevice(int bus, int address) throws IOException {
		this.bus = bus;
		this.address = address;
		try {
			this.i2cDevice = I2CFactory.getInstance(bus).getDevice(address);
		} catch (com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException e) {
			throw new IOException("Unsupported bus", e);
		}
	}

	/**
	 * Returns the bus used when communicating with this I2C device.
	 * 
	 * @return the bus used when communicating with this I2C device.
	 */
	public final int getBus() {
		return bus;
	}
	
	/**
	 * Returns the address used when communicating with this I2C device.
	 * 
	 * @return the address used when communicating with this I2C device.
	 */
	public final int getAddress() {
		return address;
	}
	
	protected void writeByte(int address, byte b) throws IOException {
		i2cDevice.write(address, b);
	}

	protected int readByte(int address) throws IOException {
		return i2cDevice.read(address);
	}

	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Don't care
		}
	}
	
	/**
	 * Convenience method to get a logger for the specific class. 
	 * 
	 * @return a logger for this class.
	 */
	public Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
}
