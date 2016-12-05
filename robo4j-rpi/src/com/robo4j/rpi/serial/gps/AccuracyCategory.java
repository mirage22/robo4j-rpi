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
 * A user friendly categorization of dilution of precision.
 * http://en.wikipedia.org/wiki/Dilution_of_precision_(GPS)
 * 
 * @author Marcus Hirt
 */
public enum AccuracyCategory {
	IDEAL(1, "Ideal",
			"This is the highest possible confidence level to be used for applications demanding the highest possible precision at all times."), EXCELLENT(
			2, "Excellent",
			"At this confidence level, positional measurements are considered accurate enough to meet all but the most sensitive applications."), GOOD(
			5,
			"Good",
			"Represents a level that marks the minimum appropriate for making business decisions. Positional measurements could be used to make reliable in-route navigation suggestions to the user."), MODERATE(
			10,
			"Moderate",
			"Positional measurements could be used for calculations, but the fix quality could still be improved. A more open view of the sky is recommended."), FAIR(
			20,
			"Fair",
			"Represents a low confidence level. Positional measurements should be discarded or used only to indicate a very rough estimate of the current location."), POOR(
			100,
			"Poor",
			"At this level, measurements are inaccurate by as much as 300 meters with a 6 meter accurate device (50 DOP × 6 meters) and should be discarded.");

	private final int dop;
	private String name;
	private String description;

	private AccuracyCategory(int dop, String name, String description) {
		this.dop = dop;
		this.name = name;
		this.description = description;
	}

	/**
	 * Returns the user friendly name of the accuracy.
	 * 
	 * @return the user friendly name of the accuracy.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a longer description explaining the accuracy.
	 * 
	 * @return a longer description explaining the accuracy.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Looks up an Accuracy from a Dilution of Precision value.
	 * 
	 * @param dop
	 *            the dilution of precision measurement to get an Accuracy from.
	 * @return the Accuracy corresponding to the dilution of precision.
	 */
	public static AccuracyCategory fromDOP(float dop) {
		for (AccuracyCategory a : values()) {
			if (a.dop >= dop) {
				return a;
			}
		}
		return POOR;
	}

}
