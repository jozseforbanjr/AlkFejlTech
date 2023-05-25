package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//standard REST vegpontok definialasa, amin keresztul lehet elerni az adatokat
@RestController //automatikusan dolgozza fel a json fajlokat
@RequestMapping("/api/v1/runner") //az api/v1/runner az alapertelmezett URL a REST kommunikaciohoz
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private ShoeRepository shoeRepository;

    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository,
                                LapTimeRepository lapTimeRepository,
                                ShoeRepository shoeRepository)
    {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.shoeRepository = shoeRepository;
    }

    @GetMapping("/{id}") //konkret runner-re lehet rakerdezni (pl. /localhost:8080/api/v1/runner/1)
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }

    @GetMapping("") //ha nincs megadva semmi a ...:8080/api/v1/ utan az URLben, akkor
    // entitasokbol allo listat general, majd REST controller leven JSON-t ad vissza
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime") //POST, adatbevitel, JSON alapon
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }
    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    // 4. feladat REST lekerdezesi vegpont: atlageletkor
    @GetMapping("/averageage")
    public double getAverageAge() {
        List<RunnerEntity> runnerEntityList = runnerRepository.findAll(); //Entity lista -> foreach
        long sumAge       = 0;
        double averageage = 0;
        int runnerCount   = 0;
        //runnerEntityList.forEach((runner) -> {
        for (RunnerEntity runner: runnerEntityList) {
            if (runner != null) {
                sumAge += (long) runner.getAge();
                runnerCount += 1;
            }
        };

        averageage = (double) sumAge / runnerCount;
        return averageage;
    }
    // F10.: REST vegpont: cipo tipus modositashoz
    @PostMapping("/{id}/setshoetype")
    public ResponseEntity setShoeType(@PathVariable Long id, @RequestBody ShoeTypeRequest shoeTypeRequest) {
        // a kod nem tartalmazza a Runner.Id <-> Shoe.Id kapcsolatot!
        // Az adatbetoltes logikaja (Runner.Id = Shoe.Id) alapjan azonban OK
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        ShoeEntity shoe = shoeRepository.findById(shoeTypeRequest.getShoeId()).orElse(null);
        if (runner != null && shoe != null) {
            //nem kell uj elem, csak feluliras
            runner.setShoe(shoe);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    public static class ShoeTypeRequest {

        private long shoeId;

        public long getShoeId() {
            return shoeId;
        }

        public void setShoeId(long shoeId) { this.setShoeId(shoeId); } //this.shoeID = shoeID helyett
    }
}
