import java.awt.geom.CubicCurve2D;

public class StuffToDraw
{
	String names;
	CubicCurve2D bottom;
	CubicCurve2D top;
	boolean fix;
	
	public StuffToDraw(String names, CubicCurve2D bottom, CubicCurve2D top, boolean fix)
	{
		this.names=names;
		this.bottom=bottom;
		this.top=top;
		this.fix=fix;
	}
}
