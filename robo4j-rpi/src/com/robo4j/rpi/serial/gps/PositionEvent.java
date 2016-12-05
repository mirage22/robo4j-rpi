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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A GPS event describing position data.
 * 
 * @author Marcus Hirt
 */
public final class PositionEvent extends GPSEvent {
	private Date time;
	private FixQuality fixQuality;
	private Location location;
	private float altitude = Float.NaN;
	private float geoidSeparation = Float.NaN;
	private int numberOfSatellites = -1;
	private float hdop;
	private AccuracyCategory accuracyCategory;

	/**
	 * The quality of the GPS fix.
	 */
	public enum FixQuality {
		INVALID, GPS, DGPS, PPS, RTK, FLOAT_RTK, ESTIMATED, MANUAL, SIMULATION;

		public static FixQuality getFixQuality(int code) {
			for (FixQuality fq : values()) {
				if (fq.ordinal() == code) {
					return fq;
				}
			}
			return INVALID;
		}
	}

	/**
	 * Creates a new position event
	 * 
	 * @param source
	 *            the GPS that was the source of the event.
	 * @param data
	 *            the raw GPS data.
	 */
	public PositionEvent(GPS source, String data) {
		super(source);
		parse(data);
	}

	/**
	 * Returns the time, zulu, for the fix. Note that only the time part is
	 * valid, not the date.
	 * 
	 * @return the time, zulu, for the fix. Note that only the time part is
	 *         valid, not the date.
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * Returns the FixQuality at the time for this position event.
	 * 
	 * @return the FixQuality at the time for this position event.
	 * 
	 * @see FixQuality
	 */
	public FixQuality getFixQuality() {
		return fixQuality;
	}

	/**
	 * Returns the 2D location on earth.
	 * 
	 * @return the 2D location on earth.
	 * 
	 * @see Location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the antenna altitude above/below mean sea level.
	 * 
	 * @return the antenna altitude above/below mean sea level.
	 */
	public float getAltitude() {
		return altitude;
	}

	/**
	 * Returns the antenna altitude above/below the ellipsoid (WGS84).
	 * 
	 * @return the antenna altitude above/below the ellipsoid (WGS84).
	 */
	public float getElipsoidAltitude() {
		return getAltitude() + geoidSeparation;
	}

	/**
	 * Returns the number of satellites used.
	 * 
	 * @return the number of satellites used.
	 */
	public int getNumberOfSatellites() {
		return numberOfSatellites;
	}

	/**
	 * Returns the horizontal dilution of precision.
	 * 
	 * @return the horizontal dilution of precision.
	 */
	public float getHorizontalDilutionOfPrecision() {
		return hdop;
	}

	/**
	 * Returns a more user friendly version of the dilution of precision.
	 * 
	 * @return
	 */
	public AccuracyCategory getAccuracyCategory() {
		return accuracyCategory;
	}
	
	/**
	 * Returns an estimate of the max error for the location in this measurement, in meters.
	 */
	public float getMaxError() {
		return hdop * GPS.UNAIDED_POSITION_ACCURACY;
	}
	
	@Override
	public String toString() {
		return "Coordinates: " + getLocation() + " alt: " + getAltitude() + "m altWGS84: " + getElipsoidAltitude() + "m FixQuality: "
				+ getFixQuality() + " #sat: " + getNumberOfSatellites() + " max error: " + getMaxError() + "m accuracy category:" + getAccuracyCategory().getName();
	}

	protected void parse(String data) {
		String[] args = data.split(",");
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			time = format.parse(args[1]);
		} catch (ParseException e) {
			// log
		}
		float latitude = parseCrazyHybridFormat(args[2]);
		if ("S".equals(args[3])) {
			latitude *= -1;
		}
		float longitude = parseCrazyHybridFormat(args[4]);
		if ("W".equals(args[5])) {
			longitude *= -1;
		}
		location = new Location(latitude, longitude);
		numberOfSatellites = getInt(args[7]);
		hdop = getFloat(args[8]);
		accuracyCategory = AccuracyCategory.fromDOP(hdop);
		altitude = getFloat(args[9]);
		geoidSeparation = getFloat(args[11]);
		fixQuality = FixQuality.getFixQuality(Integer.parseInt(args[6]));
	}

	private int getInt(String string) {
		if (string == null || "".equals(string)) {
			return -1;
		}
		return Integer.parseInt(string);
	}

	private float getFloat(String string) {
		if (string == null || "".equals(string)) {
			return Float.NaN;
		}
		return Float.parseFloat(string);
	}

	private static float parseCrazyHybridFormat(String string) {
		int index = string.indexOf('.');
		if (index < 0) {
			return Float.NaN;
		}
		float minutes = Float.parseFloat(string.substring(index - 2));
		float degrees = Integer.parseInt(string.substring(0, index - 2));
		return degrees + minutes / 60.0f;
	}
}
