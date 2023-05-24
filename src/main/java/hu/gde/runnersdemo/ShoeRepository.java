package hu.gde.runnersdemo;
// F7.: Shoe repository letrehozasa Shoe entitashoz
import org.springframework.data.jpa.repository.JpaRepository;

// Long: a ShoeEntity ID tipusa
public interface ShoeRepository extends JpaRepository<ShoeEntity, Long > {
}
