package agh.iet.evolution.gui;

import agh.iet.evolution.gui.menu.Menu;
import agh.iet.evolution.map.World;
import agh.iet.evolution.mapElements.animal.Animal;
import agh.iet.evolution.mapElements.grass.Grass;
import javafx.scene.paint.Color;


public class SetConfiguration {

    public static int setGridSize(int availableWidth, int availableHeight, int mapWidth, int mapHeight){
        int size = availableWidth / (mapWidth + 2);
        size = Math.min(size, availableHeight / (mapHeight + 2));
        return size;
    }

    //Set color based on actualEnergy
    public static Color setColorAnimal (Animal animal, World world, Menu menu){
        if(menu.ifShowAnimalWithDominantGene() && world.getDominantGene() == animal.genome.dominantGene)
            return Color.rgb(0,162,255);
        else if(animal.getActualEnergy() > world.startAnimalEnergy * 5)
            return Color.rgb(31, 18, 4);
        else if(animal.getActualEnergy() > world.startAnimalEnergy * 3)
            return Color.rgb(52, 29, 8);
        else if(animal.getActualEnergy() > world.startAnimalEnergy * 2)
            return Color.rgb(75, 40, 10);
        else if(animal.getActualEnergy() > world.startAnimalEnergy)
            return Color.rgb(99, 51, 9);
        else if(animal.getActualEnergy() > (int)(world.startAnimalEnergy * 0.8))
            return Color.rgb(124, 63, 6);
        else if(animal.getActualEnergy() > (int)(world.startAnimalEnergy * 0.6))
            return Color.rgb(150, 75, 0);
        else if(animal.getActualEnergy() > (int)(world.startAnimalEnergy * 0.4))
            return Color.rgb(172, 103, 48);
        else if(animal.getActualEnergy() > (int)(world.startAnimalEnergy * 0.2))
            return Color.rgb(192, 132, 87);
        else
            return Color.rgb(211, 162, 127);
    }

    public static Color setColorSelectedAnimal (){
        return Color.rgb(255,0,0);
    }

    //Set color based on position on the map (Jungle or Steppe)
    public static Color setColorGrass (Grass grass, World world){
        if("Jungle".equals(grass.getPlace()))
            return Color.rgb(54,162,0);
        else
            return Color.rgb(78,235,0);


    }
}
