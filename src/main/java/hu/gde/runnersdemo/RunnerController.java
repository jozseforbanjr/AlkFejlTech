package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
// a webrol (HTTP protokollal) beerkezo kereseket kezeli
@Controller
public class RunnerController {

    @Autowired
    private RunnerRepository runnerRepository;
    @Autowired
    private LapTimeRepository lapTimeRepository;
    @GetMapping("/runners") //minden runner
    public String getAllRunners(Model model) {
        List<RunnerEntity> runners = runnerRepository.findAll();
        model.addAttribute("runners", runners);
        return "runners";
    }

    @GetMapping("/runner/{id}")
    public String getRunnerById(@PathVariable Long id, Model model) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        RunnerService runnerService = new RunnerService(runnerRepository);
        if (runner != null) {
            model.addAttribute("runner", runner);
            //MVC modell rendje miatt kulon van az uzleti logika (szamolas) -> runnerService
            double averageLaptime = runnerService.getAverageLaptime(runner.getRunnerId());
            model.addAttribute("averageLaptime", averageLaptime);
            return "runner";
        } else {
            // handle error when runner is not found
            return "error";
        }
    }

    @GetMapping("/runner/{id}/addlaptime")
    public String showAddLaptimeForm(@PathVariable Long id, Model model) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            model.addAttribute("runner", runner);
            LapTimeEntity laptime = new LapTimeEntity();
            laptime.setLapNumber(runner.getLaptimes().size() + 1);
            model.addAttribute("laptime", laptime);
            return "addlaptime";
        } else {
            // handle error when runner is not found
            return "error";
        }
    }
    @PostMapping("/runner/{id}/addlaptime")
    public String addLaptime(@PathVariable Long id, @ModelAttribute LapTimeEntity laptime) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            laptime.setRunner(runner);
            laptime.setId(null);
            runner.getLaptimes().add(laptime);
            runnerRepository.save(runner);
        } else {
            // handle error when runner is not found
        }
        return "redirect:/runner/" + id;
    }

}

