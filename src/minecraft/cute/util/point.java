package cute.util;

public class point 
{
	public double x;
	public double y;
	
	public point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double magnitude()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	public double dot(point p)
	{
		return this.x * p.x + this.y * p.y;
	}
	
	public double angleBetween(point p)
	{
		return Math.acos(this.dot(p) / (this.magnitude() * p.magnitude()));
	}
	
	public point add(point p)
	{
		return new point(this.x + p.x,this.y + p.y);
	}
	public point sub(point p)
	{
		return new point(this.x - p.x, this.y - p.y);
	}
	public point mul(double m)
	{
		return new point(this.x * m, this.y * m);
	}
	public point div(double d)
	{
		return new point(this.x / d, this.y / d);
	}
}