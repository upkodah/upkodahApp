package com.uos.upkodah.data.local.gps;

public class DegreeMinSec{
    public final double value;

    public DegreeMinSec(double degree, double min, double sec){
        this.value = degree+((double)min)/60+((double)sec)/3600;
    }
}
