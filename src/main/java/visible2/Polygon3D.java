package visible2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;



public class Polygon3D {
    public List<double[]> points;
    public Color color;
    
    public Polygon3D() {
        points = new ArrayList<>();
        color = Color.BLACK;
    }
    
    public void addPoint(double[] coordinates) {
        points.add(coordinates);
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
