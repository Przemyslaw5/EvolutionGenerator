package agh.iet.evolution.mapElements;

import agh.iet.evolution.parametersObject.Point;

public interface IMapElement {

    Point getPosition();
    int getActualEnergy();
    void setActualEnergy(int energy);
    int getStartEnergy();

}
