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

    public void translateAndScale(){
        double max_x = -180, max_y =-180;
        double min_x = 180, min_y = 180;
        for (int i = 0 ; i < size ; i++){
            if (coords.get(i).getX() < min_x)
                min_x = coords.get(i).getX();
            if (coords.get(i).getX() > max_x)
                max_x = coords.get(i).getX();
            if (coords.get(i).getY() < min_y)
                min_y = coords.get(i).getY();
            if (coords.get(i).getY() > max_y)
                max_y = coords.get(i).getX();
        }

        for (int i = 0 ; i < size + 1 ; i++){

            coords.get(i).x-=Math.floor(min_x);
            coords.get(i).y -= Math.floor(min_y);
        }

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
}
