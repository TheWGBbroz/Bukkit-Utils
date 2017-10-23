package nl.thewgbbroz.butils.utils;

public class Maths {
	private Maths() {
	}
	
	public static double map(double n, double min, double max, double newMin, double newMax) {
		return ((n - min) / (max - min)) * (newMax - newMin) + newMin;
	}
	
	public static double normalizeRotation(double n) {
		while(n < 360)
			n += 360;
		
		while(n > 360)
			n -= 360;
		
		return n;
	}
	
	public static double distSq(double x1, double y1, double x2, double y2) {
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}
	
	public static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt(distSq(x1, y1, x2, y2));
	}
}
