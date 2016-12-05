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
 * An event describing heading and speed relative to the ground.
 * 
 * @author Marcus Hirt
 */
public final class VelocityEvent extends GPSEvent {
	private float trueTrackMadeGood = Float.NaN;
	private float magneticTrackMadeGood = Float.NaN;
	private float groundSpeed = Float.NaN;

	/**
	 * Constructs a new VelocityEvent.
	 *
	 * @param source
	 *            the GPS from which the event originated.
	 * @param data
	 *            the raw GPS data.
	 */
	public VelocityEvent(GPS source, String data) {
		super(source);
		parse(data);
	}

	/**
	 * Returns the measured heading in degrees.
	 * 
	 * @return the measured heading in degrees.
	 */
	public float getTrueTrackMadeGood() {
		return trueTrackMadeGood;
	}

	/**
	 * Returns the measured magnetic heading. According to the chip manual this
	 * seems to "need GlobalTop Customization Service".
	 * 
	 * @return the measured magnetic heading.
	 */
	public float getMagneticTrackMadeGood() {
		return magneticTrackMadeGood;
	}

	/**
	 * Returns the horizontal speed in km/h.
	 * 
	 * @return the horizontal speed in km/h.
	 */
	public float getGroundSpeed() {
		return groundSpeed;
	}

	@Override
	public String toString() {
		return String.format("True: %.1f\u00B0 Magnetic: %.1f\u00B0 Speed: %.1f km/h", getTrueTrackMadeGood(), getMagneticTrackMadeGood(),
				getGroundSpeed());
	}

	protected void parse(String data) {
		String[] args = data.split(",");
		if (args.length >= 8) {
			trueTrackMadeGood = getFloat(args[1]);
			magneticTrackMadeGood = getFloat(args[3]);
			groundSpeed = getFloat(args[7]);
		}
	}

	private float getFloat(String string) {
		if (string == null || "".equals(string)) {
			return Float.NaN;
		}
		return Float.parseFloat(string);
	}
}
