package com.routethala.aks.routethala;


import com.google.api.services.vision.v1.model.BoundingPoly;

class TextDetails {
    private String text;
    private BoundingPoly boundingPoly;

    public TextDetails(String text, BoundingPoly boundingPoly) {
        this.text = text;
        this.boundingPoly = boundingPoly;
    }

    public String getText() {
        return text;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setText(String text) {
        this.text = text;
    }
}
