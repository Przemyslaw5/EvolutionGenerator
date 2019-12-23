package agh.iet.evolution.mapElements.grass;

import agh.iet.evolution.mapElements.AbstractMapElement;
import agh.iet.evolution.parametersObject.Point;

public class Grass extends AbstractMapElement {

    private final String place;

    public Grass(Point startPosition, int startEnergy, String place){

        super(startPosition, startEnergy);
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public String toString() {
        return "G";
    }
}
