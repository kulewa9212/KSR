/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestr6.ksr;

/**
 *
 * @author Ewa Kulesza
 */
public class Metryki {

    protected double euklidean(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.abs(v1[i] - v2[i]) * Math.abs(v1[i] - v2[i]);
        }
        return Math.sqrt(result);
    }

    protected double taxi(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += Math.abs(v1[i] - v2[i]);
        }
        return result;
    }

    protected double manhattan(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            if (result < Math.abs(v1[i] - v2[i])) {
                result = Math.abs(v1[i] - v2[i]);
            };
        }
        return result;
    }

}
