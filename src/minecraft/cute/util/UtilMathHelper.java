package cute.util;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class UtilMathHelper 
{
	public static Vec3 getVectorForRotation(float pitch, float yaw)
	{
		float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI) ;
	    float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
	    float f2 = -MathHelper.cos(-pitch * 0.017453292F);
	    float f3 = MathHelper.sin(-pitch * 0.017453292F);
	    return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
	}
}
