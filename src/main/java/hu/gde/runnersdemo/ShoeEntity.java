package hu.gde.runnersdemo;
// F7.: Shoe entitas letrehozasa

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shoeId;

    private String shoeName;
    @JsonIgnore
    @OneToMany //(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)//
    private List<RunnerEntity> runners = new ArrayList<>();

    public Long getShoeId() {
        return shoeId;
    }

    public void setShoeId(Long shoeId) {
        this.shoeId = shoeId;
    }

    public String getshoeName() {
        return shoeName;
    }

    public void setshoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public List<RunnerEntity> getRunners() {
        return runners;
    }

    //setRunners nem celszeru, nem kell

    //contructor
    public ShoeEntity() {

    }
}
