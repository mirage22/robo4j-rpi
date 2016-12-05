package com.robo4j.rpi.i2c;

import java.io.IOException;

public interface XYZDevice<E> {
	E read() throws IOException;
}
