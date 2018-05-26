package semestr6.ksr.dom;

import java.util.Comparator;

public class DistanceResult implements Comparable<DistanceResult> {
    String label;
    Double distance;

    public DistanceResult(String label, Double distance) {
        this.label = label;
        this.distance = distance;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    public int compareTo(DistanceResult o) {

        return Double.compare(this.distance, o.distance);
    }


}

