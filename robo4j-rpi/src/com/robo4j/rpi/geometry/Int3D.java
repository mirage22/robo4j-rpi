/*
 * Copyright (C) 2016, Marcus Hirt
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
package com.robo4j.rpi.geometry;

/**
 * 3D vector of ints.
 * 
 * @author Marcus Hirt
 */
public class Int3D {
	public int x;
	public int y;
	public int z;
	
	public String toString() {
		return "x:" + x + " y:" + y +  " z:" + z;
	}
}
