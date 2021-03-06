package module3;

import java.awt.Color;
//Java utilities libraries
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PShape;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

        
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		
		
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());

                        
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    
	    
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();
	    

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    
	    if (earthquakes.size() > 0) {
	    	
	    	for(PointFeature earthquake : earthquakes){

		    	System.out.println(earthquake.getLocation());
		    	
		    	
		    	Object magObj = earthquake.getProperty("magnitude");
		    	float mag = Float.parseFloat(magObj.toString());
		    	// PointFeatures also have a getLocation method
		    	
		    	
		    	//setting different design for the markers according to the magnitude of the earthquake.
		    	int myColor;
		    	int myRadius;
		    	
		    	if(mag < 4.0){
		    		myColor = color(0,0,255);
		    		myRadius = 5;
		    	}else if(mag < 5.0){
		    		myColor = color(255, 255, 0);
		    		myRadius = 8;
		    	}else {
		    		myColor = color(255,0,0);
		    		myRadius = 12;
		    	}
		    	
		    	
		    	
		    	
		    	SimplePointMarker myTempMarker = createMarker(earthquake);
		    	myTempMarker.setColor(myColor);
		    	myTempMarker.setRadius(myRadius);
		    	markers.add(myTempMarker);
		    	map.addMarkers(markers);
		    	
	    	}
	    	
	    	
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    
	    //TODO: Add code here as appropriate
	}
		
	
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		System.out.println(feature.getProperty("magnitude"));
		
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	    
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	

		
		//legend rectangle
		fill(color(255,255,200));
		rect(30, 50, 120, 250);
		
		
		
		
		//text fields for the legend
		textSize(10);
		fill(color(0,0,0));
		text("5.0+ Magnitude", 60, 130); 
		text("4.0+ Magnitude", 60, 180);
		text("Below 4.0  Magnitude", 60, 230);

		
		//the circles for each magnitude;
		fill(color(255,0,0));
		ellipse(40, 130, 12, 12);
		fill(color(255,255,0));
		ellipse(40, 180, 8, 8);
		fill(color(0,0,255));
		ellipse(40, 230, 5, 5);

		
		// Remember you can use Processing's graphics methods here
	
	}
}
