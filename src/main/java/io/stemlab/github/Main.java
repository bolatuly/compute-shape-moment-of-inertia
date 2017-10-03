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

        double[] building1 = {128.4423853, 37.4937327, 128.4424685, 37.4937205,
                128.4424732, 37.4937561, 128.4423935, 37.4937696, 128.4423853, 37.4937327};

        double[] building2 = {128.4564528, 37.4977577, 128.4564815, 37.4976713, 128.4574888, 37.4979073,
                128.4574226, 37.4980311, 128.4564528, 37.4977577};

        double[] building2_cw = {128.4564528, 37.4977577, 128.4574226, 37.4980311, 128.4574888, 37.4979073,
                128.4564815, 37.4976713, 128.4564528, 37.4977577};

        double[] building3 = {127.0272317, 37.5823958, 127.0272795, 37.5823285, 127.0274195, 37.5823779, 127.0274483, 37.5823417,
                127.0275777, 37.5824108, 127.0276033, 37.5823564, 127.0276843, 37.5823845, 127.0276296, 37.5824571, 127.0277461, 37.5825262,
                127.0276911, 37.5825957, 127.0272317, 37.5823958};

        double[] building4 = {127.0265381, 37.5834985, 127.026851, 37.5830764, 127.0279427, 37.5835846,
                127.027639, 37.5839942, 127.0275059, 37.5839322, 127.0277303, 37.5836296, 127.0274269,
                37.5834884, 127.0269491, 37.5832659, 127.0267154, 37.5835811, 127.0265381, 37.5834985};

        double[] building5 = {127.0263526, 37.5833794, 127.0266632, 37.5829637, 127.0268138, 37.5830227,
                127.0265179, 37.5834538, 127.0263526, 37.5833794};

        double[] building6 = {129.0623125, 35.1315426, 129.0623557, 35.131031, 129.0626343, 35.1310467, 129.0625911, 35.1315583,
                129.0623125, 35.1315426};

        polygonList.add(makeBuilding(makeArrayList(building1)));
        polygonList.add(makeBuilding(makeArrayList(building2)));
        polygonList.add(makeBuilding(makeArrayList(building2_cw)));
        polygonList.add(makeBuilding(makeArrayList(building3)));
        polygonList.add(makeBuilding(makeArrayList(building4)));
        polygonList.add(makeBuilding(makeArrayList(building5)));
        polygonList.add(makeBuilding(makeArrayList(building6)));
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


