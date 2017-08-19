package com.routethala.aks.routethala;

import com.google.api.services.vision.v1.model.BoundingPoly;

public class BusResult {
    private String routeNo;
    private boolean correctBus;
    private BoundingPoly boundingPoly;

    public BusResult(String routeNo, boolean correctBus, BoundingPoly boundingPoly) {
        this.routeNo = routeNo;
        this.correctBus = correctBus;
        this.boundingPoly = boundingPoly;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public boolean isCorrectBus() {
        return correctBus;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }
}
