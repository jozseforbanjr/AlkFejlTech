package hu.gde.runnersdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//az alapertelmezett (init) adatok betoltese inditaskor
@Component
public class DataLoader implements CommandLineRunner {

    private final RunnerRepository runnerRepository;
    //F.7: Shoe repository feltoltes
    private final ShoeRepository shoeRepository;

    @Autowired
    public DataLoader(RunnerRepository runnerRepository, ShoeRepository shoeRepository) {
        this.runnerRepository = runnerRepository;
        this.shoeRepository = shoeRepository;
    }

    @Override
    public void run(String... args) {
        //shoentity
        ShoeEntity shoe1 = new ShoeEntity();
        shoe1.setshoeName("Nike Air J20");
        shoeRepository.save(shoe1);

        ShoeEntity shoe2 = new ShoeEntity();
        shoe2.setshoeName("Adidas ProRun 2023");
        shoeRepository.save(shoe2);

        ShoeEntity shoe3 = new ShoeEntity();
        shoe3.setshoeName("Tisza FutniJo Allterrain");
        shoeRepository.save(shoe3);

        // create default runner entity
        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setRunnerName("Tomi");
        runnerEntity.setAveragePace(310);
        runnerEntity.setAge(45);
        runnerEntity.setShoe(shoe1);

        // create default laptime entities and add them to the runner entity
        LapTimeEntity laptime1 = new LapTimeEntity();
        laptime1.setLapNumber(1);
        laptime1.setTimeSeconds(120);
        laptime1.setRunner(runnerEntity);

        LapTimeEntity laptime2 = new LapTimeEntity();
        laptime2.setLapNumber(2);
        laptime2.setTimeSeconds(110);
        laptime2.setRunner(runnerEntity);

        runnerEntity.getLaptimes().add(laptime1);
        runnerEntity.getLaptimes().add(laptime2);

        runnerRepository.save(runnerEntity); //Elso futo, eddigi 2 futokor adatainak elmentese

        RunnerEntity runnerEntity2 = new RunnerEntity();
        runnerEntity2.setRunnerName("Zsuzsi");
        runnerEntity2.setAveragePace(290);
        runnerEntity2.setAge(28);
        runnerEntity2.setShoe(shoe2);

        // create default laptime entities and add them to the runner entity
        LapTimeEntity laptime3 = new LapTimeEntity();
        laptime3.setLapNumber(1);
        laptime3.setTimeSeconds(95);
        laptime3.setRunner(runnerEntity2);

        LapTimeEntity laptime4 = new LapTimeEntity();
        laptime4.setLapNumber(2);
        laptime4.setTimeSeconds(100);
        laptime4.setRunner(runnerEntity2);

        runnerEntity2.getLaptimes().add(laptime3);
        runnerEntity2.getLaptimes().add(laptime4);

        runnerRepository.save(runnerEntity2);

        // feladat 3: 3. futo
        RunnerEntity runnerEntity3 = new RunnerEntity();
        runnerEntity3.setRunnerName("Marcsi");
        runnerEntity3.setAveragePace(292);
        runnerEntity3.setAge(24);
        runnerEntity3.setShoe(shoe3);

        // create default laptime entities and add them to the runner entity
        LapTimeEntity laptime5 = new LapTimeEntity();
        laptime5.setLapNumber(1);
        laptime5.setTimeSeconds(100);
        laptime5.setRunner(runnerEntity3);

        LapTimeEntity laptime6 = new LapTimeEntity();
        laptime6.setLapNumber(2);
        laptime6.setTimeSeconds(118);
        laptime6.setRunner(runnerEntity3);

        runnerEntity3.getLaptimes().add(laptime5);
        runnerEntity3.getLaptimes().add(laptime6);

        runnerRepository.save(runnerEntity3);
    }
}

