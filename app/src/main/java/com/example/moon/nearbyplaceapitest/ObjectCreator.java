package com.example.moon.nearbyplaceapitest;

class ObjectCreator {
    double lat;
    double lon;
    String name;

    public ObjectCreator() {
    }

    public ObjectCreator(double lat, double lon,String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
