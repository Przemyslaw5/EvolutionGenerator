package agh.iet.evolution.mapElements;

import agh.iet.evolution.parametersObject.Point;

public abstract class AbstractMapElement implements IMapElement{

    private Point actualPosition;
    private int actualEnergy;
    private final int startEnergy;

    public AbstractMapElement(Point startPosition, int startEnergy){
        actualPosition = startPosition;
        actualEnergy = startEnergy;
        this.startEnergy = startEnergy;
    }

    @Override
    public Point getPosition() {
        return actualPosition;
    }

    @Override
    public int getActualEnergy() {
        return actualEnergy;
    }

    @Override
    public void setActualEnergy(int energy) {
        actualEnergy = energy;
    }

    @Override
    public int getStartEnergy() {
        return startEnergy;
    }

    public void setActualPosition(Point actualPosition) {
        this.actualPosition = actualPosition;
    }
}