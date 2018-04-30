package cz.vutbr.fit.brnogo.ui.route.navigation.map;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.vutbr.fit.brnogo.R;
import cz.vutbr.fit.brnogo.data.model.response.Node;
import cz.vutbr.fit.brnogo.data.model.response.Route;
import cz.vutbr.fit.brnogo.data.model.response.Vehicle;
import cz.vutbr.fit.brnogo.tools.constant.Constant;

public class MapHelper {

	private MapHelper() {
	}

	public static Location getDefaultLocation() {
		Location location = new Location("default");
		location.setLatitude(49.195061);
		location.setLongitude(16.606836);
		return location;
	}

	public static GeoApiContext getGeoContext(String apiKey) {
		GeoApiContext geoApiContext = new GeoApiContext();
		return geoApiContext.setQueryRateLimit(3)
				.setApiKey(apiKey)
				.setConnectTimeout(1, TimeUnit.SECONDS)
				.setReadTimeout(1, TimeUnit.SECONDS)
				.setWriteTimeout(1, TimeUnit.SECONDS);
	}

	public static Location getCurrentNodeLocation(Node currentNode) {
		Location location = new Location("BrnoGo");
		location.setLatitude(currentNode.getStopLatitude());
		location.setLongitude(currentNode.getStopLongitude());
		return location;
	}

	public static void addMarkersToMap(DirectionsResult results, GoogleMap map, LatLng endLocation) {
		if (endLocation != null) {
			map.addMarker(new MarkerOptions().position(endLocation));
		} else {
			map.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].startAddress));
		}
	}

	public static void addStopCircleAreasToMap(Context context, GoogleMap map, Vehicle currentVehicle) {
		int fillColor = ContextCompat.getColor(context, R.color.light_red_translucent);
		if (currentVehicle != null) {
			List<Node> paths = currentVehicle.getPath();
			for (Node node : paths) {
				map.addCircle(new CircleOptions()
						.center(new LatLng(node.getStopLatitude(), node.getStopLongitude()))
						.radius(Constant.Navigation.ON_STOP_DISTANCE_THRESHOLD)
						.strokeColor(Color.TRANSPARENT)
						.fillColor(fillColor));
			}
		}
	}

	public static void addPolylineToMap(DirectionsResult results, GoogleMap map, Context context) {
		List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
		int polylineColor = ContextCompat.getColor(context, R.color.colorAccent);
		map.addPolyline(new PolylineOptions().color(polylineColor).addAll(decodedPath));
	}
}
