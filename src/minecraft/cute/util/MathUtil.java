package cute.util;

public class MathUtil 
{
	public static <T extends Comparable<T>> T clamp(T val, T min, T max) 
	{
		if(min.compareTo(val) > 0)
			return min;
		if(max.compareTo(val) < 0)
			return max;
		return val;
	}
	
	public static <T extends Comparable<T>> T clampMin(T val, T min) 
	{
		if(min.compareTo(val) > 0)
			return min;
		return val;
	}
	
	public static <T extends Comparable<T>> T clampMax(T val, T max) 
	{
		if(max.compareTo(val) < 0)
			return max;
		return val;
	}
}
