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
package com.robo4j.rpi.i2c.lidar;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.robo4j.rpi.i2c.AbstractI2CDevice;

/**
 * Abstraction for the Garmin/PulsedLight LidarLite.
 * 
 * @author Marcus Hirt
 */
public final class LidarDevice extends AbstractI2CDevice {
	private static final int REGISTER_COMMAND = 0x0;
	private static final int REGISTER_RESULT = 0x8f;
	private static final byte COMMAND_ACQUIRE_RANGE = 0x4;

	/**
	 * Constructs a LidarDevice using the default settings. (I2CBUS.BUS_1, 0x62)
	 * 
	 * @throws IOException
	 *             if there was communication problem
	 */
	public LidarDevice() throws IOException {
		// 0x62 is the hardwired address of the Lidar-Lite Device
		super(I2CBus.BUS_1, 0x62);
	}

	/**
	 * Creates a software interface to a Lidar-Lite.
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
	 */
	public LidarDevice(int bus, int address) throws IOException {
		super(bus, address);
	}

	/**
	 * Call this to acquire a new range reading.
	 * 
	 * @throws IOException
	 *             if there was communication problem
	 */
	public void acquireRange() throws IOException {
		i2cDevice.write(REGISTER_COMMAND, COMMAND_ACQUIRE_RANGE);
	}

	/**
	 * Reads the result. Note that if the result is read too soon after a call
	 * to acquireRange, the result may not be ready yet, and the previously
	 * acquired result may be read. Check the documentation of your particular
	 * device to know for how long to wait!
	 * 
	 * @return the distance in m.
	 * @throws IOException
	 *             if there was communication problem
	 */
	public float readDistance() throws IOException {
		int inCM = readU2(REGISTER_RESULT);
		return inCM / 100.0f;
	}

	/**
	 * Read 2 bytes unsigned.
	 */
	private int readU2(int address) throws IOException {
		byte[] result = new byte[2];
		i2cDevice.read(address, result, 0, 2);
		return (result[0] << 8) + (result[1] & 0xff);
	}
}
