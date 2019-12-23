package agh.iet.evolution;

import agh.iet.evolution.map.World;
import agh.iet.evolution.mapElements.animal.Animal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {

    private int WIDTH;
    private int HEIGHT;
    private int GRASS_ENERGY;
    private int START_ANIMAL_ENERGY;
    private double JUNGLE_RATIO;
    private int MOVE_ENERGY;
    private int NUMBER_OF_ANIMALS;

    private static JSONArray animalsList = new JSONArray();
    private static JSONArray daysList = new JSONArray();

    public JSONReader(){
        String path = "src/main/resources/parameters.json";
        try{
            String contest = new String((Files.readAllBytes(Paths.get(path))));
            JSONObject reader = new JSONObject(contest);

            this.WIDTH = reader.getInt("width");
            this.HEIGHT = reader.getInt("height");
            this.GRASS_ENERGY = reader.getInt("grassEnergy");
            this.START_ANIMAL_ENERGY = reader.getInt("startAnimalEnergy");
            this.JUNGLE_RATIO = reader.getDouble("jungleRatio");
            this.MOVE_ENERGY = reader.getInt("moveEnergy");
            this.NUMBER_OF_ANIMALS = reader.getInt("numberOfAnimals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAnimalToJSONFile(World world, int animalNumber) {

        Animal animal = world.getHistoryAllAnimals().get(animalNumber);

        JSONObject animalDetails = new JSONObject();
        animalDetails.put("Animal ID", animalNumber + 1);
        animalDetails.put("Birth", animal.getBirthDay());
        animalDetails.put("Age", animal.getAge());
        animalDetails.put("Died", animal.getDeathDay());
        animalDetails.put("Genome", animal.genome.printGenesFirst() + animal.genome.printGenesSecond());
        animalDetails.put("Number of children", animal.getChildren());
        animalDetails.put("Dominant gene", animal.genome.dominantGene);
        animalDetails.put("Number of ancestors", animal.getNumberAncestors());
        animalDetails.put("Number of scions", animal.getNumberScions());

        animalsList.put(animalDetails);
    }

    public static void writeDayToJSONFile(StatisticsOfDay statisticsOfDay){

        JSONObject dayDetails = new JSONObject();
        dayDetails.put("Day: ", statisticsOfDay.getDay());
        dayDetails.put("Number of animals: ", statisticsOfDay.getNumberOfAnimals());
        dayDetails.put("Number of grasses: ", statisticsOfDay.getNumberOfGrass());
        dayDetails.put("Dominant gene: ", statisticsOfDay.getDominateGene());
        dayDetails.put("Average energy: ", String.format("%.2f", statisticsOfDay.getAverageEnergy()));
        dayDetails.put("Average age: ", String.format("%.2f", statisticsOfDay.getAverageAge()));
        dayDetails.put("Average number of children: ", String.format("%.2f", statisticsOfDay.getAverageNumberOfChildren()));

        daysList.put(dayDetails);
    }


    public static void saveAnimalsToFile(){
        //Write JSON file
        try (FileWriter file = new FileWriter("src/main/resources/animals.json")) {

            file.write(animalsList.toString(4));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDaysToFile(){
        //Write JSON file
        try (FileWriter file = new FileWriter("src/main/resources/days.json")) {

            file.write(daysList.toString(4));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getGRASS_ENERGY() {
        return GRASS_ENERGY;
    }

    public int getSTART_ANIMAL_ENERGY() {
        return START_ANIMAL_ENERGY;
    }

    public double getJUNGLE_RATIO() {
        return JUNGLE_RATIO;
    }

    public int getMOVE_ENERGY() {
        return MOVE_ENERGY;
    }

    public int getNUMBER_OF_ANIMALS() {
        return NUMBER_OF_ANIMALS;
    }
}
