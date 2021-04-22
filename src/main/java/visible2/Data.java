package visible2;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Data {
    public List<Polygon3D> read() throws IOException, JSONException {
       List<Polygon3D> polygonsList = new ArrayList<>();
       
       Path path = Path.of("input.json");
       final String jsonText = Files.readString(path, StandardCharsets.UTF_8);
       
       JSONObject json = new JSONObject(jsonText);
       System.out.println(jsonText);
       
       JSONArray polygonsArray = json.getJSONArray("polygons");
       for(var polygonItem : polygonsArray) {
           JSONObject polygonObject = (JSONObject)polygonItem;
           
           Polygon3D polygon3d = new Polygon3D();
           
           JSONArray pointsArray = polygonObject.getJSONArray("points");
           for(var pointsItem : pointsArray) {
               JSONArray pointsList = (JSONArray)pointsItem;
               double[] coordinates = new double[3];
               
               for(int i=0; i<3; i++)
                   coordinates[i] = pointsList.getDouble(i);
               
               polygon3d.addPoint(coordinates);
           }
           
           int colorInt = polygonObject.getInt("color");
           polygon3d.setColor(new Color(colorInt));
           
           polygonsList.add(polygon3d);
       }
       
       return polygonsList;
    }
}
