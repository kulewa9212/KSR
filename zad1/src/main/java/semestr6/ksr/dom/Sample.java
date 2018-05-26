package semestr6.ksr.dom;


import java.util.*;


public class Sample {
    protected String label;
    protected Map<String , Double> features;
    protected Map<String , Double> featuresToCompare;
    protected List<DistanceResult> distances;
    protected String knnResult;
    protected boolean pertinence;

    public Sample() {
        this.label = new String();
        this.features = new LinkedHashMap<>();
        this.distances = new ArrayList<>();
        this.featuresToCompare = new LinkedHashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Double> features) {
        this.features = features;
    }

    public Map<String, Double> getFeaturesToCompare() {
        return featuresToCompare;
    }

    public void setFeaturesToCompare(Map<String, Double> featuresToCompare) {
        this.featuresToCompare = featuresToCompare;
    }

    public String getKnnResult() {
        return knnResult;
    }

    public void setKnnResult(String knnResult) {
        this.knnResult = knnResult;
    }

    public List<DistanceResult> getDistances() {
        return distances;
    }

    public void setDistances(List<DistanceResult> distances) {
        this.distances = distances;
    }

    public boolean isPertinence() { return pertinence; }

    public void setPertinence(boolean pertinence) { this.pertinence = pertinence; }
}
