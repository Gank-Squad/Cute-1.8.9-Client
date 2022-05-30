package cute.ui.hud.display;

import cute.ui.hud.ScreenPosition;
import net.minecraft.client.Minecraft;

public class DraggableComponent
{
	protected int color;
		
		protected int x;
		protected int y;
		
		protected int rx;
		protected int ry;
		
		protected int parentX;
		protected int parentY;
		
		protected int width;
		protected int height;
		
		protected final Minecraft mc = Minecraft.getMinecraft();;
		
		public DraggableComponent(int relativeX, int relativeY, int width, int height, int color)
		{
			this.rx = relativeX;
			this.ry = relativeY;
			
			this.x = rx + parentX;
			this.y = ry + parentY;
			this.width = width;
			this.height = height;
			
			this.color = color;		
		}
		
//		public DraggableComponent(int relativeX, int relativeY, int width, int height, 
//				int parentX, int parentY, int color)
//		{
//			
//			this.rx = relativeX;
//			this.ry = relativeY;
//			
//			this.x = rx + parentX;
//			this.y = ry + parentY;
//			this.width = width;
//			this.height = height;
//			
////			this.parentX = parentX;
////			this.parentY = parentY;
//			
//			this.color = color;			
//		}
		
		public void render(ScreenPosition pos, float scaleX, float scaleY)
		{
			
		}
		
		public void renderDummy(ScreenPosition pos, float scaleX, float scaleY)
		{
			
		}
		public void renderDummy(double parentY, double parentX)
		{
			
		}
		
		public void setParentXY(int x, int y)
		{
			this.parentX = x;
			this.parentY = y;
		}
		// unlike whatever ScreenPosition does
		// this should give position relative to parent
		public int getAbsoluteX()
		{
			return x;
		}
		public int getAbsoluteY()
		{
			return y;
		}
		public int getRelativeX()
		{
			return rx;
		}
		public int getRelativeY()
		{
			return ry;
		}
		
		public void checkPos(int parentX, int parentY)
		{
			this.parentX = parentX;
			this.parentY = parentY;
			this.x = this.parentX + rx;
			this.y = this.parentY + ry;
		}
		
		public void setRelativeXY(int x, int y)
		{
			this.rx = x;
			this.ry = y;
			checkPos(this.parentX, this.parentY);
		}
		
		public void setColor(byte color)
		{
			this.color = color;
		}
		
		public void setHeight(int height)
		{
			this.height = height;
		}
		
		public void setWidth(int width)
		{
			this.width = width;
		}
		
		public void setSize(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
		
		
		public int getColor()
		{
			return this.color;
		}
		
		public int getWidth()
		{
			return this.width;
		}
		
		public int getHeight()
		{
			return this.height;
		}
}





