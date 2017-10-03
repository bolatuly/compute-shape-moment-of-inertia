package io.stemlab.github.entity;

import java.util.ArrayList;


public class Polygon {

    public Coordinate centroid;
    public Double area;
    public Double IG;
    public int size;

    public ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
    public ArrayList<Shape> TList = new ArrayList<>();
    public ArrayList<Shape> RList = new ArrayList<>();

    public Polygon(ArrayList<Coordinate> coords) {
        this.coords = coords;
    }

    public void add(Coordinate crd){
        coords.add(crd);
    }

    public void compute(){
        size = coords.size() - 1;

        translateAndScale();
        for (int i = 0 ; i < size ; i++){
            Coordinate c1 = coords.get(i);
            Coordinate c2 = coords.get(i+1);

            addTriangle(c1, c2);
            addRectangle(c1, c2);
        }

        setArea();
        if (area < 0) {// CCW
            System.out.println("CCW");
            fixNegativeArea();
        }
        setCentroid();
        setIG();
    }

    //Mercator projection
    //https://stackoverflow.com/questions/18838915/convert-lat-lon-to-pixel-coordinate
    public void translateAndScale(){
        //get Boundary of Data
        MBR mbr = new MBR().calc();

        double east = Math.toRadians(mbr.getEast());
        double west = Math.toRadians(mbr.getWest());
        double north = Math.toRadians(mbr.getNorth());
        double south = Math.toRadians(mbr.getSouth());

        //Degree To radians
        degreeToRadians();

        // This also controls the aspect ratio of the projection
        double height = 1000.0;
        double width = 1000.0 * (east - west) / (north - south);

        double Ymin = mercY(south);
        double Ymax = mercY(north);
        double xFactor = width / (east - west);
        double yFactor = height / (Ymax - Ymin);

        //Map Projection
        for (int i = 0 ; i < size + 1 ; i++){
            Double x = (coords.get(i).x - west) * xFactor;
            Double y = mercY(coords.get(i).y);
            y = (Ymax - y) * yFactor;

            if (Double.isNaN(y) || Double.isNaN(x)){
                System.out.println("NAN in here");
            }

            coords.get(i).setX(x);
            coords.get(i).setY(y);
        }


    }
    private void degreeToRadians(){
        for (int i = 0 ; i < size + 1 ; i++){
            coords.get(i).x = Math.toRadians(coords.get(i).getX());
            coords.get(i).y = Math.toRadians(coords.get(i).getY());
        }
    }

    public double mercY(double lat) {
        double half_lat = lat/2;
        double tangent = Math.tan(half_lat + Math.PI/4);
        return Math.log(tangent);
    }

    public void fixNegativeArea(){
        for (int i = 0 ; i < size ; i++){
            TList.get(i).area *= -1;
            TList.get(i).IG *= -1;
            RList.get(i).area *= -1;
            RList.get(i).IG *= -1;
        }
        area *= -1;
    }

    public void setIG(){
        IG = 0.0;
        for (int i = 0 ; i < size ; i++){
            IG += getTriangle(i).IG;
            IG += getSquareDistance(centroid, getTriangle(i).centroid) * getTriangle(i).area;
            IG += getRectangle(i).IG;
            IG += getSquareDistance(centroid, getRectangle(i).centroid) * getRectangle(i).area;
        }
    }

    public void setCentroid(){
        double x = 0.0;
        double y = 0.0;
        for (int i = 0 ; i < size ; i++){
            x += getRectangle(i).centroid.getX() * getRectangle(i).area;
            x += getTriangle(i).centroid.getX() * getTriangle(i).area;

            y += getRectangle(i).centroid.getY() * getRectangle(i).area;
            y += getTriangle(i).centroid.getY() * getTriangle(i).area;
        }

        x /= area;
        y /= area;
        centroid = new Coordinate(x,y);
    }

    private Shape getTriangle(int i) {
        return TList.get(i);
    }

    private Shape getRectangle(int i) {
        return RList.get(i);
    }

    public Double setArea(){
        area = 0.0;
        for (int i = 0 ; i < size ; i++){
            area += getTriangle(i).area + getRectangle(i).area;
        }
        return area;
    }

    public void addTriangle(Coordinate c1, Coordinate c2){
        Coordinate centroid = new Coordinate((c1.x + 2*c2.x) / 3, (2*c1.y + c2.y)/3);
        Double area = (c2.x - c1.x) * (c2.y-c1.y) / 2;
        Double ig = area * getSquareDistance(c1, c2) / 18;

        TList.add(new Shape(centroid, area, ig));
    }

    public void addRectangle(Coordinate c1, Coordinate c2){
        Double area = (c2.x - c1.x) * c1.y;
        Coordinate centroid = new Coordinate((c1.x+c2.x)/2, c1.y / 2);
        Double ig = area * (Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c1.getY(), 2)) / 12;

        RList.add(new Shape(centroid, area, ig));
    }

    public static double getSquareDistance(Coordinate start, Coordinate end) {
        return Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2);
    }

    private class MBR {
        private double east;
        private double north;
        private double west;
        private double south;

        public MBR() {
            this.east = -180;
            this.north = -180;
            this.west = 180;
            this.south = 180;
        }

        public void setWest(double west) {
            this.west = west;
        }

        public double getEast() {
            return east;
        }

        public double getNorth() {
            return north;
        }

        public double getWest() {
            return west;
        }

        public double getSouth() {
            return south;
        }

        public MBR calc() {
            for (int i = 0 ; i < size ; i++){
                if (coords.get(i).getX() < west)
                    west = coords.get(i).getX();
                if (coords.get(i).getX() > east)
                    east = coords.get(i).getX();
                if (coords.get(i).getY() < south)
                    south = coords.get(i).getY();
                if (coords.get(i).getY() > north)
                    north = coords.get(i).getY();
            }
            return this;
        }

    }
}
