package agh.iet.evolution;

public class StatisticsOfDay {

    private final int day;
    private final int numberOfAnimals;
    private final int numberOfGrass;
    private final int dominateGene;
    private final double averageEnergy;
    private final double averageAge;
    private final double averageNumberOfChildren;

    public StatisticsOfDay(int day, int numberOfAnimals, int numberOfGrass, int dominateGene, double averageEnergy, double averageAge, double averageNumberOfChildren) {
        this.day = day;
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfGrass = numberOfGrass;
        this.dominateGene = dominateGene;
        this.averageEnergy = averageEnergy;
        this.averageAge = averageAge;
        this.averageNumberOfChildren = averageNumberOfChildren;
    }

    public int getDay() {
        return day;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfGrass() {
        return numberOfGrass;
    }

    public int getDominateGene() {
        return dominateGene;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public double getAverageNumberOfChildren() {
        return averageNumberOfChildren;
    }
}
