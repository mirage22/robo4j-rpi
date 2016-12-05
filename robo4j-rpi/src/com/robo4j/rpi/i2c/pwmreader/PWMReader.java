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
package com.robo4j.rpi.i2c.pwmreader;

import java.io.IOException;
import com.pi4j.io.i2c.I2CBus;
import com.robo4j.rpi.i2c.AbstractI2CDevice;

/**
 * Abstraction for reading PWM pulse lengths from a PWM reader.
 * 
 * The PWM reader is a custom built arduino nano thingy. ;)
 * 
 * @author Marcus Hirt
 */
public class PWMReader extends AbstractI2CDevice {

	public PWMReader() throws IOException {
		this(I2CBus.BUS_1, 0x02);
	}

	public PWMReader(int bus, int address) throws IOException {
		super(bus, address);
	}
	
	/**
	 * @return the pulse length in ms
	 * @throws IOException 
	 */
	public double readPulse(int channel) throws IOException {
		i2cDevice.write((byte) channel);
		sleep(10);
		int valueInMicros = readU2();
		return valueInMicros / 1000.0;
	}
	
	private int readU2() throws IOException {
		byte [] result = new byte[2];
		i2cDevice.read(result, 0, 2);		
		return (result[0] << 8) + (result[1] & 0xff);
	}
}
