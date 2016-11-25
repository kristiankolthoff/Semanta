package de.unima.dws.semanta.recommender;

public class Location {

	private String status;
	private String country;
	private String countryCode;
	private String region;
	private String regionName;
	private String city;
	private int zip;
	private double lat;
	private double lon;
	private String timezone;
	private String isp;
	private String org;
	private String as;
	private String query;
	
	public Location(String status, String country, String countryCode, String region, String regionName, String city,
			int zip, double lat, double lon, String timezone, String isp, String org, String as, String query) {
		this.status = status;
		this.country = country;
		this.countryCode = countryCode;
		this.region = region;
		this.regionName = regionName;
		this.city = city;
		this.zip = zip;
		this.lat = lat;
		this.lon = lon;
		this.timezone = timezone;
		this.isp = isp;
		this.org = org;
		this.as = as;
		this.query = query;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "Location [status=" + status + ", country=" + country + ", countryCode=" + countryCode + ", region="
				+ region + ", regionName=" + regionName + ", city=" + city + ", zip=" + zip + ", lat=" + lat + ", lon="
				+ lon + ", timezone=" + timezone + ", isp=" + isp + ", org=" + org + ", as=" + as + ", query=" + query
				+ "]";
	}
	
	
}
