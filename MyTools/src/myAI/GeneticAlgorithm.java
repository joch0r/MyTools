package myAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import myTools.Valuator;
import myTools.ValuatorComparator;

public class GeneticAlgorithm<GENTYPE> {

    private List<List<GENTYPE>> population;
    private ValuatorComparator<List<GENTYPE>> comparator;
    private int populationSize;
    private int individualLength;
    private int generation;
    private double selectionSize;
    private double mutationChance;
    private Random random;
    private List<GENTYPE> genPool;
    private Valuator<List<GENTYPE>> valuator;

    public GeneticAlgorithm(Set<GENTYPE> genPool, Valuator<List<GENTYPE>> valuator, int populationSize, int individualLength,
            double selectionSize, double mutationChance) {
        this.population = new ArrayList<List<GENTYPE>>();
        this.valuator = valuator;
        this.comparator = new ValuatorComparator<List<GENTYPE>>(valuator);
        this.populationSize = populationSize;
        this.individualLength = individualLength;
        this.generation = 0;
        this.selectionSize = selectionSize;
        this.mutationChance = mutationChance;
        this.random = new Random();

        // urgh kan geen random element uit een set halen
        this.genPool = new ArrayList<GENTYPE>(genPool);

        initializePopulation();
    }

    public void run(int nrOfGenerations) {
        for (int g = 0; g < nrOfGenerations; g++) {
            nextGeneration();
        }
    }

    public void nextGeneration() {
        debugPopulation();
        population = mutate(recombine(selectSurvivors()));
        generation++;
    }

    public List<List<GENTYPE>> getPopulation() {
        return population;
    }

    private void debugPopulationFull() {
        System.out.println(String.format("generation %s:", generation));
        for (List<GENTYPE> individual : population) {
            System.out.println(String.format("individual: %s - score: %s", individual, valuator.valuate(individual)));
        }
    }

    private void debugPopulation() {
        Set<List<GENTYPE>> bestIndividuals = new HashSet<List<GENTYPE>>();
        double bestScore = Double.NEGATIVE_INFINITY;
        for (List<GENTYPE> individual : population) {
            double score = valuator.valuate(individual);
            if (score > bestScore) {
                bestIndividuals.clear();
                bestIndividuals.add(individual);
                bestScore = score;
            } else {
                if (score == bestScore) {
                    bestIndividuals.add(individual);
                    bestScore = score;
                }
            }
        }
        System.out.println(String.format("generation: %s - best individuals: %s - score: %s", generation, bestIndividuals, bestScore));
    }

    private List<List<GENTYPE>> mutate(List<List<GENTYPE>> group) {
        List<List<GENTYPE>> result = new ArrayList<List<GENTYPE>>();

        for (List<GENTYPE> individual : group) {
            List<GENTYPE> mutatedIndividual = new ArrayList<GENTYPE>();
            for (GENTYPE gen : individual) {
                if (random.nextDouble() < mutationChance) {
                    mutatedIndividual.add(genPool.get(random.nextInt(genPool.size())));
                } else {
                    mutatedIndividual.add(gen);
                }
            }
            result.add(mutatedIndividual);
        }
        return result;
    }

    private List<List<GENTYPE>> recombine(List<List<GENTYPE>> group) {
        List<List<GENTYPE>> result = new ArrayList<List<GENTYPE>>();

        for (int i = 0; i < populationSize; i++) {
            result.add(recombine(group.get(random.nextInt(group.size())), group.get(random.nextInt(group.size()))));
        }
        return result;
    }

    private List<GENTYPE> recombine(List<GENTYPE> individual1, List<GENTYPE> individual2) {
        // error als ws1.size != ws2.size?

        List<GENTYPE> result = new ArrayList<GENTYPE>();
        for (int i = 0; i < Math.min(individual1.size(), individual2.size()); i++) {
            if (random.nextBoolean()) {
                result.add(individual1.get(i));
            } else {
                result.add(individual2.get(i));
            }
        }

        return result;
    }

    private List<List<GENTYPE>> selectSurvivors() {
        List<List<GENTYPE>> result = new ArrayList<List<GENTYPE>>();

        List<List<GENTYPE>> orderedPopulation = new ArrayList<List<GENTYPE>>(population);
        orderedPopulation.addAll(population);
        Collections.sort(orderedPopulation, comparator);

        for (int i = 0; i < populationSize * selectionSize; i++) {
            result.add(orderedPopulation.get(i));
        }

        return result;
    }

    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            List<GENTYPE> individual = new ArrayList<GENTYPE>();
            for (int g = 0; g < individualLength; g++) {
                individual.add(genPool.get(random.nextInt(genPool.size())));
            }
            population.add(individual);
        }
    }

    public static void main(String[] args) {

        final Valuator<List<Integer>> SOME_VALUATOR = new Valuator<List<Integer>>() {

            @Override
            public double valuate(List<Integer> o) {
                // TODO Auto-generated method stub
                return o.get(0) - o.get(1);
            }
        };

        Set<Integer> genPool = new HashSet<Integer>();

        for (int i = 0; i < 1000; i++) {
            genPool.add(i);
        }

        GeneticAlgorithm<Integer> test = new GeneticAlgorithm<Integer>(genPool, SOME_VALUATOR, 100, 2, 0.2, 0.1);
        test.run(100);
    }
}
