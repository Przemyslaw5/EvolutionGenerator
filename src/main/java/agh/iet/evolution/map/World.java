package agh.iet.evolution.map;

import agh.iet.evolution.mapElements.animal.Animal;
import agh.iet.evolution.mapElements.grass.Grass;
import agh.iet.evolution.parametersObject.Point;

import java.util.*;
import java.util.stream.Collectors;

public class World implements IWorldMap {
    public final int MAP_WIDTH;
    public final int MAP_HEIGHT;
    public final int startAnimalEnergy;
    private int moveEnergy;
    private final int grassEnergy;
    private Point leftBottomCorner;
    private Point rightTopCorner;
    private Point leftBottomCornerOfJungle;
    private Point rightTopCornerOfJungle;

    private static Random random = new Random();

    private HashMap<Point, LinkedList<Animal>> animals = new HashMap<>();
    private HashMap<Point, Grass> grasses = new HashMap<>();
    private List<Animal> historyAllAnimals = new LinkedList<>();

    public World(int width, int height, int grassEnergy, int startAnimalEnergy, double jungleRatio, int moveEnergy){
        this.MAP_WIDTH = width;
        this.MAP_HEIGHT = height;
        this.startAnimalEnergy = startAnimalEnergy;
        this.moveEnergy = moveEnergy;
        this.grassEnergy = grassEnergy;
        this.leftBottomCorner = new Point(0,0);
        this.rightTopCorner = new Point(width, height);
        this.leftBottomCornerOfJungle = new Point((int)((this.rightTopCorner.x / 2) - (jungleRatio * this.rightTopCorner.x / 2)),(int)((this.rightTopCorner.y / 2) - (jungleRatio * this.rightTopCorner.y / 2)));
        this.rightTopCornerOfJungle = new Point((int)((this.rightTopCorner.x / 2) + (jungleRatio * this.rightTopCorner.x / 2)),(int)((this.rightTopCorner.y / 2) + (jungleRatio * this.rightTopCorner.y / 2)));
    }

    public static Point repairPositionOnMap(Point position, World world){
        int newXPosition;
        int newYPosition;
        int width = world.rightTopCorner.x;
        int height = world.rightTopCorner.y;

        //Set x position
        if(position.x < world.leftBottomCorner.x)
            newXPosition = (width - Math.abs(position.x % width)) % width;
        else
            newXPosition = Math.abs(position.x % width);

        //Set y position
        if(position.y < world.leftBottomCorner.x)
            newYPosition = (height - Math.abs(position.x % height)) % height;
        else
            newYPosition = Math.abs(position.y % height);

        return new Point(newXPosition, newYPosition);
    }

    @Override
    public boolean canMoveTo(Point position) {
        return position.precedes(rightTopCorner) && position.follows(leftBottomCorner);
    }

    public void growGrass(){

        //Calculation of the number of fields in the jungle
        int fieldsOnJungle = (rightTopCornerOfJungle.x - leftBottomCornerOfJungle.x) * (rightTopCornerOfJungle.y - leftBottomCornerOfJungle.y);
        int attempt = 0;

        //Grow grass in the Jungle
        while(attempt <= fieldsOnJungle * 2){
            int x = random.nextInt(rightTopCornerOfJungle.x - leftBottomCornerOfJungle.x) + leftBottomCornerOfJungle.x;
            int y = random.nextInt(rightTopCornerOfJungle.y - leftBottomCornerOfJungle.y) + leftBottomCornerOfJungle.y;
            Point grassField = new Point(x, y);
            if(!isOccupied(grassField)){
                grasses.put(grassField, new Grass(grassField, grassEnergy, "Jungle"));
                break;
            }
            attempt++;
        }

        //Calculation of the number of fields in the Steppe
        int fieldsOnMap = rightTopCorner.x * rightTopCorner.y;
        int fieldsOnSteppe = fieldsOnMap - fieldsOnJungle;
        attempt = 0;

        //Grow grass in the Steppe
        while(attempt <= fieldsOnSteppe * 3){
            int x = random.nextInt(rightTopCorner.x);
            int y = random.nextInt(rightTopCorner.y);
            Point grassField = new Point(x,y);
            Point tmp2 = new Point(rightTopCornerOfJungle.x - 1, rightTopCornerOfJungle.y - 1);
            if(!isOccupied(grassField) && !(grassField.follows(leftBottomCornerOfJungle) && grassField.precedes(tmp2))){
                grasses.put(grassField, new Grass(grassField, grassEnergy, "Steppe"));
                break;
            }
            attempt++;
        }
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            if(!animals.containsKey(animal.getPosition())){
                animals.put(animal.getPosition(), new LinkedList<>());
            }
            animals.get(animal.getPosition()).add(animal);
            return true;
        }
        System.out.println("E");
        return false;
    }

    @Override
    public void run() {
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());

        allAnimals.forEach(Animal::changeDirection);           //Every animal must turn
        allAnimals.forEach(animal -> animal.move(moveEnergy)); //Every animal must move
    }

    @Override
    public boolean isOccupied(Point position) {
        if(animals.get(position) != null) return true;
        return grasses.get(position) != null;
    }

    @Override
    public Object objectAt(Point position) {
        int dominantGene = getDominantGene();
        if(animals.get(position) != null)
            return animals.get(position).get(0);
        return grasses.get(position);
    }


    public int numberAllAnimalsOnMap(){
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        return allAnimals.size();
    }

    public int numberAllGrassesOnMap(){
        return grasses.size();
    }

    public void animalEatGrass(){
        List<Grass> grassesPossibleToEat = new ArrayList<>(this.grasses.values());
        grassesPossibleToEat.forEach(grass -> {
            if(animals.containsKey(grass.getPosition())){
                //We sort animals to have the greatest animal energy in the field
                animals.get(grass.getPosition()).sort(Comparator.comparing(Animal::getActualEnergy));
                int theGreatestEnergy = animals.get(grass.getPosition()).get(0).getActualEnergy();

                //We create a list of animals with the same amount of energy in the field
                List<Animal> animalsWithTheSameAmountOfEnergy = new LinkedList<>();
                for(Animal animal : animals.get(grass.getPosition())){
                    if(animal.getActualEnergy() == theGreatestEnergy){
                        animalsWithTheSameAmountOfEnergy.add(animal);
                    }
                }

                //We calculate how much energy each animal gets
                int energyFromGrass = grassEnergy / animalsWithTheSameAmountOfEnergy.size();

                //Each animal eats the appropriate part of the grass
                for(Animal animal : animalsWithTheSameAmountOfEnergy){
                    animal.eat(energyFromGrass);
                }

                //Remove the grass from field
                grasses.remove(grass.getPosition());
            }
        });
    }

    private Point getChildField(Point adult){
        LinkedList<Point> ListOfFreeFields = new LinkedList<>();
        if(!isOccupied(repairPositionOnMap(new Point(adult.x, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x, adult.y + 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y + 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x + 1, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x + 1, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y - 1), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y - 1));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y));
        if(!isOccupied(repairPositionOnMap(new Point(adult.x - 1, adult.y + 1), this))) ListOfFreeFields.add(new Point(adult.x - 1, adult.y + 1));
        if(ListOfFreeFields.size() == 0) {
            ListOfFreeFields.add(new Point(adult.x, adult.y + 1)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y + 1)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y)); ListOfFreeFields.add(new Point(adult.x + 1, adult.y - 1));
            ListOfFreeFields.add(new Point(adult.x, adult.y - 1)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y - 1)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y)); ListOfFreeFields.add(new Point(adult.x - 1, adult.y + 1));
        }
        int randomIndex = random.nextInt(ListOfFreeFields.size());
        return ListOfFreeFields.get(randomIndex);
    }

    public void copulationAnimal(int birthDay){
        LinkedList<Animal> childAnimals = new LinkedList<>();
        animals.forEach((position, listOfAnimals) -> {
            if(listOfAnimals.size() >= 2){
                listOfAnimals.sort(Comparator.comparing(Animal::getActualEnergy));

                Animal firstAnimal;
                Animal secondAnimal;
                List<Animal> animalsToCopulate = new LinkedList<>();

                //The first animal in the list has a different amount of energy every second
                if(listOfAnimals.get(0).getActualEnergy() != listOfAnimals.get(1).getActualEnergy()) {
                    firstAnimal = listOfAnimals.get(0);
                    animalsToCopulate.add(listOfAnimals.get(1));
                    for (int i = 2; i < listOfAnimals.size(); i++) {
                        if (listOfAnimals.get(i).getActualEnergy() == listOfAnimals.get(1).getActualEnergy()) {
                            animalsToCopulate.add(listOfAnimals.get(i));
                        } else break;
                    }
                    int randomIndex = random.nextInt(animalsToCopulate.size());
                    secondAnimal = listOfAnimals.get(randomIndex + 1);
                }
                //The first animal in the list has the same energy as the second
                else{
                    animalsToCopulate.add(listOfAnimals.get(0));
                    for (int i = 1; i < listOfAnimals.size(); i++) {
                        if (listOfAnimals.get(i).getActualEnergy() == listOfAnimals.get(0).getActualEnergy()) {
                            animalsToCopulate.add(listOfAnimals.get(i));
                        } else break;
                    }
                    int randomFirstIndex = random.nextInt(animalsToCopulate.size());
                    int randomSecondIndex;
                    do{
                        randomSecondIndex = random.nextInt(animalsToCopulate.size());
                    }while (randomFirstIndex == randomSecondIndex);
                    firstAnimal = listOfAnimals.get(randomFirstIndex);
                    secondAnimal = listOfAnimals.get(randomSecondIndex);
                }

                //Create a child it is possible
                if(firstAnimal.canAnimalCopulate() && secondAnimal.canAnimalCopulate()){
                    Point childField = repairPositionOnMap(getChildField(position),this);

                    Animal child = Animal.copulation(listOfAnimals.get(0), listOfAnimals.get(1), childField, this, birthDay);
                    childAnimals.add(child);
                    historyAllAnimals.add(child);
                }
            }
        });
        for(Animal child : childAnimals){
            place(child);
        }
    }

    public void removeDeadAnimal(int day){
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());

        allAnimals.forEach(animal -> {
            if(animal.getActualEnergy() <= 0 && animals.containsKey(animal.getPosition())){
                this.animals.get(animal.getPosition()).remove(animal);
                if(this.animals.get(animal.getPosition()).isEmpty()) {
                    this.animals.remove(animal.getPosition());
                }
                animal.setDeathDay(day);
            }
        });
    }

    public void putStartAnimals(int number){
        Animal animal;
        for(int i = 0; i < number; i++){
            int x, y;
            do{
                x = random.nextInt(rightTopCorner.x);
                y = random.nextInt(rightTopCorner.y);
            } while (isOccupied(new Point(x, y)));
            animal = new Animal(new Point(x, y), startAnimalEnergy, this, 1);
            place(animal);
            historyAllAnimals.add(animal);
        }
    }

    public void positionChanged(Point oldPosition, Animal animal){
        animals.get(oldPosition).remove(animal);

        if(animals.get(oldPosition).isEmpty()) animals.remove(oldPosition);
        this.place(animal);
    }

    public int[] geneProportion(){
        int[] genesProportion = new int[8];
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        for(int i = 0; i < 8; i++) genesProportion[i] = 0;
        allAnimals.forEach(animal -> {
            for(int i = 0; i < 8; i++){
                genesProportion[i] += animal.genome.genesAmount[i];
            }
        });
        return genesProportion;
    }

    public int getDominantGene(){
        int[] dominantGenes = new int[8];
        for(int i = 0; i < 8; i++);
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());

        for(Animal animal : allAnimals){
            dominantGenes[animal.genome.dominantGene]++;
        }

        int dominant = 0;
        for(int i = 1; i < 8; i++){
            if(dominantGenes[i] > dominantGenes[dominant])
                dominant = i;
        }

        return dominant;
    }

    public double averageChildrenNumber(){
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        int sum = 0;
        for(Animal animal : allAnimals){
            sum += animal.getChildren();
        }
        return (double) sum / allAnimals.size();
    }

    public double averageAgeNumber(){
        int sumAge = 0;
        int count = 0;
        for(Animal animal : historyAllAnimals){
            if(animal.getDeathDay() != -1){
                sumAge += animal.getAge();
                count++;
            }
        }

        if(count == 0) return 0.0;
        return (double) sumAge / count;
    }

    public double averageEnergyNumber(){
        List<Animal> allAnimals = animals.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
        int sum = 0;
        for(Animal animal : allAnimals){
            sum += animal.getActualEnergy();
        }
        return (double) sum / allAnimals.size();
    }

    public List<Animal> getHistoryAllAnimals() {
        return historyAllAnimals;
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(leftBottomCorner, rightTopCorner);
    }
}