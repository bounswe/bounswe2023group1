package com.a1.disasterresponse.model;

public class GeoJsData {
    private String ipAddress;
    private String country;
    private String region;
    private String city;
    private String latitude;
    private String longitude;
    private String timezone;
    private String organization;

    public GeoJsData(String ipAddress, String country, String region, String city, String latitude, String longitude, String timezone, String organization) {
        this.ipAddress = ipAddress;
        this.country = country;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.organization = organization;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    public String getTimezone() {
        return timezone;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getOrganization() {
        return organization;
    }
}
