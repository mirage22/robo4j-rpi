/*
 *  Copyright (C) 2015 Marcus Hirt
 *                     www.hirt.se
 *
 * This software is free:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESSED OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright (C) Marcus Hirt, 2015
 */
package com.robo4j.rpi.serial.gps;

/**
 * A 2D location on earth.
 * 
 * @author Marcus Hirt
 */
public final class Location {
	private static final String STR_DEGREE = "\u00B0";
	private static final String STR_MINUTE = "'"; // "\u2032";
	private static final String STR_SECOND = "\""; // \u2033";

	private final float latitude;
	private final float longitude;

	/**
	 * Creates a new location.
	 * 
	 * @param latitude
	 *            the latitude in decimal degrees.
	 * @param longitude
	 *            the longitude in decimal degrees.
	 */
	public Location(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the decimal degree for the latitude.
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @return the decimal degree for the longitude.
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Returns the coordinates as a String in degrees, minutes and seconds format.
	 * 
	 * @return the coordinates as a String in degrees, minutes and seconds format.
	 */
	public String asDMS() {
		return String.format("%s%s %s%s", toDMS(latitude), latitude > 0 ? "N" : "S", toDMS(longitude), longitude > 0 ? "E" : "W");
	}

	private Object toDMS(float coordinate) {
		int deg = Math.abs((int) coordinate);
		int minute = Math.abs((int) (coordinate * 60) % 60);
		int second = Math.abs((int) (coordinate * 3600) % 60);
		return String.format("%d%s%d%s%d%s", deg, STR_DEGREE, minute, STR_MINUTE, second, STR_SECOND);
	}

	@Override
	public String toString() {
		return asDMS();
	}
}
