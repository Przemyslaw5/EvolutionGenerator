package agh.iet.evolution;

import agh.iet.evolution.map.World;
import agh.iet.evolution.mapElements.animal.Animal;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    public World world;
    private Animal selectedAnimal = null;
    public int dayOfSimulation = 1;
    private List<StatisticsOfDay> statisticsOfDays = new LinkedList<>();

    public Simulation(){

        JSONReader configuration = new JSONReader();

        world = new World(configuration.getWIDTH(), configuration.getHEIGHT(), configuration.getGRASS_ENERGY(), configuration.getSTART_ANIMAL_ENERGY(), configuration.getJUNGLE_RATIO(), configuration.getMOVE_ENERGY());
        world.putStartAnimals(configuration.getNUMBER_OF_ANIMALS());
        addStatisticsForTheDay();
    }

    public void simulateDay(){
        dayOfSimulation++;
        world.growGrass();
        world.run();
        world.animalEatGrass();
        world.copulationAnimal(dayOfSimulation);
        world.removeDeadAnimal(dayOfSimulation);
        addStatisticsForTheDay();
    }

    public void addStatisticsForTheDay(){
        StatisticsOfDay statisticsOfDay = new StatisticsOfDay(dayOfSimulation, world.numberAllAnimalsOnMap(), world.numberAllGrassesOnMap(), world.getDominantGene(), world.averageEnergyNumber(), world.averageAgeNumber(), world.averageChildrenNumber());
        statisticsOfDays.add(statisticsOfDay);
    }

    private void getInformationAboutAnimal(int animalNumber){
        Animal animal = world.getHistoryAllAnimals().get(animalNumber);

        System.out.println("Animal ID: " + (animalNumber + 1));
        String died;
        if(world.getHistoryAllAnimals().get(animalNumber).getDeathDay() == -1)
            died = "Died: The animal is still alive";
        else
            died = "Died: " + animal.getDeathDay();
        System.out.println("Birth: " + animal.getBirthDay() + ", age: " + animal.getAge() + ", " + died);
        System.out.println("Genome: " + animal.genome.printGenesFirst() + animal.genome.printGenesSecond());
        System.out.println("Number of children: " + animal.getChildren() + ", dominant gene: " + animal.genome.dominantGene);
        System.out.println("Number of ancestors: " + animal.getNumberAncestors() + ", number of scions: " + animal.getNumberScions());
        System.out.println();
    }

    private void getInformationAboutDay(int dayNumber){
        StatisticsOfDay statisticsOfDay = statisticsOfDays.get(dayNumber);
        System.out.println("Day: " + statisticsOfDay.getDay());
        System.out.println("Number of animals: " + statisticsOfDay.getNumberOfAnimals());
        System.out.println("Number of grasses: " + statisticsOfDay.getNumberOfGrass());
        System.out.println("Dominant gene: " + statisticsOfDay.getDominateGene());
        System.out.println("Average energy: " + String.format("%.2f", statisticsOfDay.getAverageEnergy()));
        System.out.println("Average age: " + String.format("%.2f", statisticsOfDay.getAverageAge()));
        System.out.println("Average number of children: " + String.format("%.2f", statisticsOfDay.getAverageNumberOfChildren()));
        System.out.println();
    }

    public void showStatisticsAfterSimulation(){

        Scanner scanner = new Scanner(System.in);
        String choose;
        String day;
        boolean saveToFile;
        boolean showInTerminal;

        List<Animal> animals = world.getHistoryAllAnimals();

        boolean flag = true;

        while(flag){
            System.out.println("Hello!!! The simulation lasted " + dayOfSimulation + " days and gathered " + animals.size() + " animals. ");
            System.out.println("Do you want to know the history of the selected animal or you want to meet animals with the dominant gene which was gene number: " + world.getDominantGene() + "?");
            System.out.println("Choose an operation: ");
            System.out.println("Choose a number between 1 and " + (animals.size()) + " to learn the history of the selected animal");
            System.out.println("Choose the letter \"d\" to learn about animals with the dominant gene");
            System.out.println("Choose the letter \"a\" to learn about selected animal");
            System.out.println("Choose the letter \"n\" to learn about the history of animals that gave birth or died on the nth day");
            System.out.println("Precede the operation with the letter \"s\" to save the statistics to the file and display them in the terminal");
            System.out.println("Precede the operation with the letter \"S\" to save the statistics to the file and not display them in the terminal");
            System.out.println("Select the letter \"q\" to terminate the program");
            System.out.println();


            try{
                choose = scanner.nextLine();

                showInTerminal = true;
                saveToFile = false;

                if('s' == choose.charAt(0)) {
                    saveToFile = true;
                    showInTerminal = true;
                    choose = choose.substring(1);
                }
                if('S' == choose.charAt(0)) {
                    saveToFile = true;
                    showInTerminal = false;
                    choose = choose.substring(1);
                }
                System.out.println(choose);

                if("d".equals(choose)) {
                    for (int i = 0; i < animals.size(); i++) {
                        if (animals.get(i).genome.dominantGene == world.getDominantGene()) {
                            if(showInTerminal) getInformationAboutAnimal(i);
                            if(saveToFile) {
                                JSONReader.writeAnimalToJSONFile(world, i);
                            }
                        }
                    }
                }
                else if("a".equals(choose)){
                    if(selectedAnimal == null) {
                        System.out.println("You have not selected any animal on the map");
                        System.out.println();
                    }
                    else{
                        int animalNumber = -1;
                        for(int i = 0; i < world.getHistoryAllAnimals().size(); i++){
                            if(selectedAnimal.equals(world.getHistoryAllAnimals().get(i))){
                                animalNumber = i;
                                break;
                            }
                        }
                        if(showInTerminal) getInformationAboutAnimal(animalNumber);
                        if(saveToFile) JSONReader.writeAnimalToJSONFile(world, animalNumber);
                    }
                }
                else if("n".equals(choose)){
                    System.out.println("Choose the day number you are interested in between 1 and " + dayOfSimulation);
                    System.out.println();
                    day = scanner.nextLine();
                    int dayNumber = Integer.parseInt(day);
                    if(showInTerminal) getInformationAboutDay(dayNumber - 1);
                    if(saveToFile) JSONReader.writeDayToJSONFile(statisticsOfDays.get(dayNumber - 1));
                }
                else if("q".equals(choose)){
                    flag = false;
                    JSONReader.saveAnimalsToFile();
                    JSONReader.saveDaysToFile();
                }
                else{
                    int animalNumber = Integer.parseInt(choose);
                    animalNumber--;
                    if(animalNumber >= 0 && animalNumber < (animals.size())){
                        if(showInTerminal) getInformationAboutAnimal(animalNumber);
                        if(saveToFile) {
                            JSONReader.writeAnimalToJSONFile(world, animalNumber);
                        }
                    }
                    else{
                        System.out.println("There is no such animal");
                        System.out.println();
                    }
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("Illegal choose, please try again. ");
                System.out.println();
            }
        }
    }
    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
    }
}