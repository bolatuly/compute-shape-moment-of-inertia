package io.stemlab.github;

import io.stemlab.github.entity.Coordinate;
import io.stemlab.github.entity.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Polygon> list = dataLoad();
        for (Polygon pt : list) {
            pt.compute();
            System.out.print("Area : " + pt.area + "\n");
            System.out.println("Centroid : " + pt.centroid.x + ", " + pt.centroid.y);
            System.out.println("Ig : " + pt.IG);
            Double cmi = pt.area * pt.area / (2 * Math.PI * pt.IG);
            System.out.println("CMI : " + cmi);
            System.out.println("------------------------\n\n");
        }
    }

    //DataLoad
    public static List<Polygon> dataLoad() {
        List<Polygon> polygonList = new ArrayList<Polygon>();
//
//        ArrayList<Coord> coords  = new ArrayList<Coord>();
//
//        coords.add(new Coord(128.4564528, 37.4977577));
//        coords.add(new Coord(128.4564815, 37.4976713));
//        coords.add(new Coord(128.4574888, 37.4979073));
//        coords.add(new Coord(128.4574226, 37.4980311));
//        coords.add(new Coord(128.4564528, 37.4977577));
//        PolygonType pt = new PolygonType(coords);
//        polygonList.add(pt);
//
//        ArrayList<Coord> coords2  = new ArrayList<Coord>();
//        coords2.add(new Coord(2.0,2.0));
//        coords2.add(new Coord(2.0,3.5));
//        coords2.add(new Coord(3.0,3.5));
//        coords2.add(new Coord(3.0,2.0));
//        coords2.add(new Coord(2.0,2.0));
//        PolygonType pt2 = new PolygonType(coords2);
//        polygonList.add(pt2);
//
//        ArrayList<Coord> coords3  = new ArrayList<Coord>();
//        coords3.add(new Coord(3.3,1.1));
//        coords3.add(new Coord(1.0,1.1));
//        coords3.add(new Coord(0.1,2.5));
//        coords3.add(new Coord(2.7,3.0));
//        coords3.add(new Coord(4.0,2.0));
//        coords3.add(new Coord(3.3,1.1));
//        PolygonType pt3 = new PolygonType(coords3);
//        polygonList.add(pt3);

//        double[] array1_1 = {1.0, 0.0, 3.0, 2.0, 1.0, 1.0, -1.0 ,0.0 ,1.0 ,0.0};
//        polygonList.add(makeBuilding(makeArrayList(array1_1)));
//
//        double[] array1 = {1.0,0.0,-1.0,0.0,1.0,1.0,3.0,2.0,1.0,0.0};
//        polygonList.add(makeBuilding(makeArrayList(array1)));

        double[] building1 = {128.4423853, 37.4937327, 128.4424685, 37.4937205,
                128.4424732, 37.4937561, 128.4423935, 37.4937696, 128.4423853, 37.4937327};
        polygonList.add(makeBuilding(makeArrayList(building1)));

        double[] building2 = {128.4564528, 37.4977577, 128.4564815, 37.4976713, 128.4574888, 37.4979073,
                128.4574226, 37.4980311, 128.4564528, 37.4977577};
        polygonList.add(makeBuilding(makeArrayList(building2)));

        double[] building2_cw = {128.4564528, 37.4977577, 128.4574226, 37.4980311, 128.4574888, 37.4979073,
                128.4564815, 37.4976713, 128.4564528, 37.4977577};
        polygonList.add(makeBuilding(makeArrayList(building2_cw)));

        return polygonList;
    }

    public static ArrayList<Coordinate> makeArrayList(double[] list) {
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
        for (int i = 0; i < list.length; i += 2) {
            coords.add(new Coordinate(list[i], list[i + 1]));
        }
        return coords;
    }

    public static Polygon makeBuilding(ArrayList<Coordinate> coords) {
        Polygon pt = new Polygon(coords);
        return pt;
    }

}


