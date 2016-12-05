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
package com.robo4j.rpi.serial.gps;

import java.io.IOException;

/**
 * Listens for GPS event and prints them to stdout as they come.
 * 
 * @author Marcus Hirt
 */
public class GPSTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		GPS gps = new GPS();
		gps.addListener(new GPSListener() {
			@Override
			public void onEvent(PositionEvent event) {
				System.out.println(event);
			}

			@Override
			public void onEvent(VelocityEvent event) {
				System.out.println(event);
			}
		});
		System.out.println("Press enter to quit!");
		System.in.read();
		gps.shutdown();
	}
}
