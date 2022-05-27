package cute.events;

import cute.eventapi.events.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public class RenderLivingModelEvent<T extends EntityLivingBase> implements Event 
{
	public final T entityLivingBaseIn;
	public final ModelBase modelBase;
	public final float p2;
	public final float p3;
	public final float p4;
	public final float p5;
	public final float p6;
	public final float scaleFactor;
	
	public RenderLivingModelEvent(T entitylivingbaseIn, ModelBase base, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor)
	{
		this.entityLivingBaseIn = entitylivingbaseIn;
		this.p2 = p_77036_2_;
		this.p3 = p_77036_3_;
		this.p4 = p_77036_4_;
		this.p5 = p_77036_5_;
		this.p6 = p_77036_6_;
		this.scaleFactor = scaleFactor;
		this.modelBase = base;
	}
}
