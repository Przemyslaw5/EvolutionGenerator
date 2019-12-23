package agh.iet.evolution.mapElements.animal;

import agh.iet.evolution.map.World;
import agh.iet.evolution.mapElements.AbstractMapElement;
import agh.iet.evolution.parametersObject.MapDirection;
import agh.iet.evolution.parametersObject.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Animal extends AbstractMapElement {

    public final Genome genome;
    private MapDirection direction;
    private World world;
    private int children = 0;
    private int age = 0;
    private int birthDay;
    private int deathDay = -1;
    private int scion = 0;
    private List<Animal> ancestors = new LinkedList<>();

    public Animal(Point startPosition, int startEnergy, World world, int birthDay) {
        super(startPosition, startEnergy);
        genome = new Genome();
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.birthDay = birthDay;
    }

    public Animal(Point startPosition, int startEnergy, Animal animal1, Animal animal2, World world, int birthDay) {
        super(startPosition, startEnergy);
        genome = new Genome(animal1.genome, animal2.genome);
        direction = MapDirection.getRandomDirection();
        this.world = world;
        this.birthDay = birthDay;

        //Assignment of ancestors from one parent
        this.ancestors.addAll(animal1.getAncestors());
        this.ancestors.add(animal1);
        boolean isSecondParent = true;
        for(Animal animal : ancestors){
            if(animal.equals(animal2)){
                isSecondParent = false;
                break;
            }
        }
        //Checking if the other parent is no longer the ancestor of the first parent
        if(isSecondParent) this.ancestors.add(animal2);
        //Checking the ancestors of the second parent and assigning them to the child
        for(Animal animal : animal2.getAncestors()){
            boolean isAnotherAncestor = true;
            for(Animal myAncestor : this.ancestors){
                if(myAncestor.equals(animal)){
                    isAnotherAncestor = false;
                    break;
                }
            }
            if(isAnotherAncestor) this.ancestors.add(animal);
        }

        //Increase the number of scions by 1 for each ancestor
        for(Animal animal : this.ancestors){
            animal.scion++;
        }
    }

    public List<Animal> getAncestors() {
        return ancestors;
    }

    public boolean canAnimalCopulate(){
        return this.getActualEnergy() >= (int)(this.getStartEnergy() * 0.5);
    }

    public static Animal copulation(Animal a1, Animal a2, Point childPosition, World world, int birthDay){
        int firstAdultEnergy = (int) (0.25 * a1.getActualEnergy());
        a1.setActualEnergy(a1.getActualEnergy() - firstAdultEnergy);
        int secondAdultEnergy = (int) (0.25 * a2.getActualEnergy());
        a2.setActualEnergy(a2.getActualEnergy() - secondAdultEnergy);
        int childEnergy = firstAdultEnergy + secondAdultEnergy;
        a1.children++;
        a2.children++;
        return new Animal(childPosition, childEnergy, a1, a2, world, birthDay);
    }

    public void eat(int energyFromGrass){
        this.setActualEnergy(this.getActualEnergy() + energyFromGrass);
    }

    public void changeDirection(){
        int numberToTurn = this.genome.getRandomGene();
        for(int i = 0; i < numberToTurn; i++){
            this.direction = this.direction.next();
        }
    }

    public void move(int moveEnergy){

        //If the animal goes beyond the map, it appears from the other side
        Point newPosition = this.getPosition().add(this.direction.toUnitVector());
        newPosition = World.repairPositionOnMap(newPosition, world);

        this.age++;

        setActualEnergy(getActualEnergy() - moveEnergy);

        //Change position
        Point oldPosition = this.getPosition();
        this.setActualPosition(newPosition);
        world.positionChanged(oldPosition, this);
    }

    public int getChildren() {
        return children;
    }

    public int getAge() {
        return age;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getNumberScions() {
        return scion;
    }

    public int getNumberAncestors(){
        return this.ancestors.size();
    }

    @Override
    public String toString() {
        return "A";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return children == animal.children &&
                age == animal.age &&
                birthDay == animal.birthDay &&
                deathDay == animal.deathDay &&
                scion == animal.scion &&
                Objects.equals(genome, animal.genome) &&
                direction == animal.direction &&
                Objects.equals(world, animal.world) &&
                Objects.equals(ancestors, animal.ancestors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genome, direction, world, children, age, birthDay, deathDay, scion, ancestors);
    }
}
