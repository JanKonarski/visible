package visible2;

import java.awt.Graphics;
import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.json.JSONException;



public class Camera extends JPanel {
    public final double SHIFT = 0.25;
    public final double TURN = 1.0;
    public final double ZOOM = 1.0;
    
    private final int DIEWIDTH = 12;
    private final int DIEHEIGHT = 12;
    private double focalLength = 5;
    
    //World position
    private double xPosition = 0.0;
    private double yPosition = 0.0;
    private double zPosition = 0.0;
    //World rotation
    private double xRotation = 0.0;
    private double yRotation = 0.0;
    private double zRotation = 0.0;
    
    private List<Polygon3D> polygons;
    
    public Camera() {
        try {
            polygons = new Data().read();
            
        } catch(IOException e) {
            System.out.println("IOException");
        } catch(JSONException e) {
            System.out.println("JSONException");
        }
    }
    
    public void moveX(double value) {
        xPosition += value;
    }
    
    public void moveY(double value) {
        yPosition += value;
    }
    
    public void moveZ(double value) {
        zPosition += value;
    }
    
    public void rotateX(double value) {
        xRotation += value;
    }
    
    public void rotateY(double value) {
        yRotation += value;
    }
    
    public void rotateZ(double value) {
        zRotation += value;
    }
    
    public void focalLength(double value) {
        focalLength += value;
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        List<Polygon3D> world = div(transform());
        world = sort(world);
        
        for(var polygon : world) {
            Polygon p = new Polygon();
            for(var coord : polygon.points)
                p.addPoint((int)coord[0], (int)coord[1]);
            
            graphics.setColor(polygon.color);
            graphics.fillPolygon(p);
        }
    }
    
    private List<Polygon3D> div(List<Polygon3D> world) {
        List<Polygon3D> newWorld = new ArrayList<>();
        
        for(var polygon : world) {
            if(polygon.points.size() > 3){
                Polygon3D p = new Polygon3D();
                
                for(int i=0; i<polygon.points.size()-2; i++) {
                    for(int j=i; j<i+3; j++)
                        p.addPoint(polygon.points.get(j));
                    p.setColor(polygon.color);
                }
                
                newWorld.add(p);
            } else {
                newWorld.add(polygon);
            }
        }
        
        return newWorld;
    }
    
    private List<Polygon3D> transform() {
        List<Polygon3D> world = new ArrayList<>();
        
        for(var polygon : polygons) {
            boolean flag = false;
            Polygon3D p = new Polygon3D();
            
            for(var coord : polygon.points) {
                if(!flag) {
                    double x = coord[0] + xPosition;
                    double y = coord[1] + yPosition;
                    double z = coord[2] + zPosition;
                    
                    //X rotation
                    double xRadian = (xRotation * Math.PI) / 180;
                    y = y*Math.cos(xRadian) - z*Math.sin(xRadian);
                    z = y*Math.sin(xRadian) + z*Math.cos(xRadian);
                    
                    //Y rotation
                    double yRadian = (yRotation * Math.PI) / 180;
                    x = z*Math.sin(yRadian) + x*Math.cos(yRadian);
                    z = z*Math.cos(yRadian) - x*Math.sin(yRadian);
                    
                    //Z rotation
                    double zRadian = (zRotation * Math.PI) / 180;
                    x = x*Math.cos(zRadian) - y*Math.sin(zRadian);
                    y = x*Math.sin(zRadian) + y*Math.cos(zRadian);
                    
                    if(z < 0) {
                        flag = true;
                        continue;
                    }
                    
                    x = x*focalLength / (z+focalLength);
                    y = -y*focalLength / (z+focalLength);
                    
                    int scaleX = getSize().width / DIEWIDTH;
                    int scaleY = getSize().height / DIEHEIGHT;
                    
                    int centerX = getSize().width / 2;
                    int centerY = getSize().height / 2;
                    
                    x = Math.ceil(x * scaleX + centerX);
                    y = Math.ceil(y * scaleY + centerY);
                    
                    p.addPoint(new double[]{x, y, z});
                }
            }
            
            if(!flag) {
                p.color = polygon.color;
                world.add(p);
            }
        }
        
        return world;
    }
    
    private List<Polygon3D> sort(List<Polygon3D> world) {
        double max = Double.MIN_VALUE;
        for(var polygon : world)
            for(var coord : polygon.points)
                if(coord[2] > max)
                    max = coord[2];
        
        double[] count  = new double[world.size()];
        for (int i=0; i<world.size(); i++) {
           double sum = 0;
            for(var coord : world.get(i).points)
                sum += Math.sqrt(
                        Math.pow(coord[0], 2) *
                        Math.pow(coord[1], 2) * 
                        Math.pow(coord[2], 2)
                );
            count[i] = sum;
        }
        
        for(int i=0; i<count.length; i++)
            for(int j=count.length-1; j>0; j--)
                if(count[j] > count[j-1]) {
                    var tmp = count[j];
                    count[j] = count[j-1];
                    count[j-1] = tmp;
                    
                    var tmp2 = world.get(j);
                    world.set(j, world.get(j-1));
                    world.set(j-1, tmp2);
                }
        
        return world;
    }
}
