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

/**
 * Repeatedly reads channel 8 and channel 9 from the PWM-reader, and displays
 * the pulse length read.
 * 
 * FIXME(Marcus/Dec 5, 2016): The PWM reader is custom code running on an
 * Arduino Nano, should probably share design and c-code.
 * 
 * @author Marcus Hirt
 */
public class PWMReaderTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		PWMReader reader = new PWMReader();

		while (true) {
			double channel8Val = 0;
			double channel9Val = 0;
			channel8Val = reader.readPulse(8);
			Thread.sleep(10);
			channel9Val = reader.readPulse(9);
			System.out.println(
					String.format("Channel 8 Pulse: %2.3fms, Channel 9 Pulse: %2.3fms", channel8Val, channel9Val));
			Thread.sleep(10);
		}
	}

}
