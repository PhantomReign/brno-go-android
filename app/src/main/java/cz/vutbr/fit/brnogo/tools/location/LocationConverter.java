package cz.vutbr.fit.brnogo.tools.location;

import android.location.Location;

import com.google.maps.android.data.geojson.GeoJsonPoint;

public class LocationConverter {

	private LocationConverter() {
	}

	/**
	 * Get distance from coordinates
	 * Implementation of haversine formula:
	 * https://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @param locationFrom coordinates of source location.
	 * @param longitudeTo longitude of end location.
	 * @param latitudeTo latitude of end location.
	 * @return distance in meters.
	 */
	public static double getDistance(Location locationFrom, double longitudeTo, double latitudeTo) {

		final int EARTH_RADIUS = 6371;

		double latitudeDistance = Math.toRadians(latitudeTo - locationFrom.getLatitude());
		double longitudeDistance = Math.toRadians(longitudeTo - locationFrom.getLongitude());
		double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
				+ Math.cos(Math.toRadians(locationFrom.getLatitude())) * Math.cos(Math.toRadians(latitudeTo))
				* Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c * 1000;
	}
}
