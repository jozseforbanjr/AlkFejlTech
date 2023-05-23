package hu.gde.runnersdemo;

import org.springframework.data.jpa.repository.JpaRepository;

//interfesz a Runners demo egyes adatait a JPA elmenti/lekeri
public interface RunnerRepository extends JpaRepository<RunnerEntity,Long > {
}
