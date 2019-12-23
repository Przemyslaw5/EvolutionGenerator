package agh.iet.evolution.mapElements.animal;

import java.util.Arrays;
import java.util.Random;

public class Genome {

    private static final int numberOfGenes = 32;
    private static final int numberOfDifferentGenes = 8;
    public final int[] animalGenes = new int[numberOfGenes];
    public final int[] genesAmount = new int [numberOfDifferentGenes];
    public final int dominantGene;

    private static Random random = new Random();

    public Genome(){
        //Genome will have at least 1 gene of each type
        for(int i = 0; i < numberOfDifferentGenes; i++){
            animalGenes[i] = i;
        }

        //Fill the table with random genes and sort it
        for(int i = numberOfDifferentGenes; i < numberOfGenes; i++){
            animalGenes[i] = random.nextInt(numberOfDifferentGenes);
        }
        Arrays.sort(animalGenes);

        this.dominantGene = findDominateGene();
    }

    public Genome(Genome genomesAnimal1, Genome genomesAnimal2){

        //Find two divisions of the table
        int firstDivide = 0;
        int secondDivide = 0;
        while(firstDivide >= secondDivide){
            firstDivide = random.nextInt(numberOfGenes - 2) + 1;
            secondDivide = random.nextInt(numberOfGenes - 2) + 1;
        }

        //Choose the first animal
        boolean flag = random.nextBoolean();
        if(flag){
            Genome temp = genomesAnimal1;
            genomesAnimal1 = genomesAnimal2;
            genomesAnimal2 = temp;
        }

        //Division into 3 groups
        int group1 = random.nextInt(3);
        int group2 = random.nextInt(3);
        while (group1 == group2) group2 = random.nextInt(3);
        int group3 = 3 - (group1 + group2);

        //Assign genes from relevant groups and animals
        takeGenFromGroup(group1, firstDivide, secondDivide, this, genomesAnimal1);
        takeGenFromGroup(group2, firstDivide, secondDivide, this, genomesAnimal1);
        takeGenFromGroup(group3, firstDivide, secondDivide, this, genomesAnimal2);
        Arrays.sort(this.animalGenes);

        //If one gene is not in the genome
        repairGens(this);

        this.dominantGene = findDominateGene();
    }

    private void takeGenFromGroup(int group, int firstDivide, int secondDivide, Genome child, Genome adult){
        if(group == 0)
            System.arraycopy(adult.animalGenes, 0, child.animalGenes, 0, firstDivide);
        else if(group == 1)
            System.arraycopy(adult.animalGenes, firstDivide, child.animalGenes, firstDivide, secondDivide - firstDivide);
        else
            System.arraycopy(adult.animalGenes, secondDivide, child.animalGenes, secondDivide, numberOfGenes - secondDivide);
    }

    private int findDominateGene(){

        //Count the number of individual genes
        for(int genNumber : animalGenes){
            genesAmount[genNumber]++;
        }

        //Find the dominate gene
        int dominant = 0;
        for(int i = 1; i < 8; i++){
            if(genesAmount[i] > genesAmount[dominant])
                dominant = i;
        }
        return dominant;
    }

    private void repairGens(Genome genome){
        int[] genes = new int[numberOfDifferentGenes];

        boolean flag = true;

        while(flag){
            flag = false;
            for(int i = 0; i < numberOfDifferentGenes; i++) genes[i] = 0;
            for(int i = 0; i < numberOfGenes; i++){
                genes[genome.animalGenes[i]]++;
            }
            for(int i = 0; i < numberOfDifferentGenes; i++){
                if(genes[i] == 0){
                    int randomGene = random.nextInt(numberOfGenes);
                    genome.animalGenes[randomGene] = i;
                    flag = true;
                    Arrays.sort(genome.animalGenes);
                }
            }
        }
    }

    public int getRandomGene(){
        int index = random.nextInt(numberOfGenes);
        return animalGenes[index];
    }

    public String printGenesFirst(){
        String genes = Integer.toString(animalGenes[0]);
        for(int i = 1; i < 16; i++){
            genes += ", " + animalGenes[i];
        }
        return genes + ", ";
    }

    public String printGenesSecond(){
        String genes = Integer.toString(animalGenes[16]);
        for(int i = 17; i < 32; i++){
            genes += ", " + animalGenes[i];
        }
        return genes;
    }

    @Override
    public String toString() {
        return "Genome{" +
                "animalGenes=" + Arrays.toString(animalGenes) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return Arrays.equals(animalGenes, genome.animalGenes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(animalGenes);
    }
}
