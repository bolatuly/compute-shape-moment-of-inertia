package io.stemlab.github.entity;

public class Shape {

    public Coordinate centroid;
    public Double area;
    public Double IG;

    public Shape(Coordinate centroid, Double area, Double IG) {
        this.centroid = centroid;
        this.area = area;
        this.IG = IG;
    }

    public Shape(Coordinate centroid, Double area) {
        this.centroid = centroid;
        this.area = area;
    }

    public Coordinate getCentroid() {
        return centroid;
    }

    public void setCentroid(Coordinate centroid) {
        this.centroid = centroid;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getIG() {
        return IG;
    }

    public void setIG(Double IG) {
        this.IG = IG;
    }
}
