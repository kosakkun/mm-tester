public class LineSegment
{
    public final int x1,y1;
    public final int x2,y2;
    
    public LineSegment (int _x1, int _y1, int _x2, int _y2)
    {
        if (_x1 <= _x2) {
            x1 = _x1; y1 = _y1;
            x2 = _x2; y2 = _y2;
        } else {
            x1 = _x2; y1 = _y2;
            x2 = _x1; y2 = _y1;
        }
    }

    public double calcDist ()
    {
        double dx = (double)(x1 - x2);
        double dy = (double)(y1 - y2);
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        LineSegment ls = (LineSegment)obj;
        return ls.x1 == this.x1 && ls.y1 == this.y1 &&
               ls.x2 == this.x2 && ls.y2 == this.y2;
    }
}
