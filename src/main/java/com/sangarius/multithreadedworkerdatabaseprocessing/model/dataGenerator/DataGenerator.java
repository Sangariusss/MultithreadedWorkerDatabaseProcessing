package main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.dataGenerator;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities.Worker;

public class DataGenerator {

    private static final Faker faker = new Faker();
    private static final String[] educationLevels = {"High School", "Bachelor's Degree", "Master's Degree", "PhD"};

    public static List<Worker> generateWorkers(int count) {
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = faker.random().nextInt(educationLevels.length);
            String education = educationLevels[randomIndex];

            workers.add(new Worker(
                i,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.random().nextInt(65),
                faker.random().nextBoolean() ? "Male" : "Female",
                education,
                faker.random().nextInt(30),
                faker.random().nextInt(100000),
                faker.random().nextInt(2000)
            ));
        }
        return workers;
    }
}