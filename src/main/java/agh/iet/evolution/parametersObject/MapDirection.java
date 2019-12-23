package agh.iet.evolution.parametersObject;

import java.util.Random;

public enum MapDirection {

    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static MapDirection getRandomDirection(){
        Random random = new Random();
        int randomNumber = random.nextInt(8);
        switch (randomNumber){
            case 0: return MapDirection.NORTH;
            case 1: return MapDirection.NORTH_EAST;
            case 2: return MapDirection.EAST;
            case 3: return MapDirection.SOUTH_EAST;
            case 4: return MapDirection.SOUTH;
            case 5: return MapDirection.SOUTH_WEST;
            case 6: return MapDirection.WEST;
            case 7: return MapDirection.NORTH_WEST;
        }
        System.out.println("ERROR MAP DIRECTION");
        return null;
    }

    @Override
    public String toString(){
        switch (this){
            case NORTH:
                return "N";
            case NORTH_EAST:
                return "NE";
            case EAST:
                return "E";
            case SOUTH_EAST:
                return "SE";
            case SOUTH:
                return "S";
            case SOUTH_WEST:
                return "SW";
            case WEST:
                return "W";
            case NORTH_WEST:
                return "NW";
            default:
                return "Error";
        }
    }

    public MapDirection next(){
        switch (this){
            case NORTH:
                return NORTH_EAST;
            case NORTH_EAST:
                return EAST;
            case EAST:
                return SOUTH_EAST;
            case SOUTH_EAST:
                return SOUTH;
            case SOUTH:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return WEST;
            case WEST:
                return NORTH_WEST;
            case NORTH_WEST:
                return NORTH;
            default:
                throw new RuntimeException();
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH:
                return NORTH_WEST;
            case NORTH_EAST:
                return NORTH;
            case EAST:
                return NORTH_EAST;
            case SOUTH_EAST:
                return EAST;
            case SOUTH:
                return SOUTH_EAST;
            case SOUTH_WEST:
                return SOUTH;
            case WEST:
                return SOUTH_WEST;
            case NORTH_WEST:
                return WEST;
            default:
                throw new RuntimeException();
        }
    }

    public Point toUnitVector(){
        switch (this){
            case NORTH:
                return new Point(0, 1);
            case NORTH_EAST:
                return  new Point(1, 1);
            case EAST:
                return new Point(1, 0);
            case SOUTH_EAST:
                return new Point(1, -1);
            case SOUTH:
                return new Point(0, -1);
            case SOUTH_WEST:
                return new Point(-1, -1);
            case WEST:
                return new Point(-1, 0);
            case NORTH_WEST:
                return new Point(-1, 1);
            default:
                return new Point(0, 0);
        }
    }
}
