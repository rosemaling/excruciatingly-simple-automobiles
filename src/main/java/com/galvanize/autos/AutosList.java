package com.galvanize.autos;

import java.util.ArrayList;
import java.util.List;


public class AutosList {

    private List<Automobile> automobiles;
    
    public boolean isEmpty() {
        return automobiles.isEmpty();
    }

    public AutosList() {
        this.automobiles = new ArrayList<>();
    }

    public AutosList(List<Automobile> automobiles) {
        this.automobiles = automobiles;
    }

    public List<Automobile> getAutomobiles() {
        return automobiles;
    }

    public void setAutomobiles(List<Automobile> automobiles) {
        this.automobiles = automobiles;
    }
}
