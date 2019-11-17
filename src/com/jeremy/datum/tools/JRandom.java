package com.jeremy.datum.tools;

import java.util.*;

public class JRandom {
	public static int randomInt(final int min, final int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

}
