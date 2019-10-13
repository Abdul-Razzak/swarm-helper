package de.thesis.swarmhelper.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DockerNode {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "total_memory")
    BigDecimal totalMemory;

    @Column(name = "total_cpu")
    BigDecimal totalCpu;

    @OneToMany(mappedBy="dockerNode", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN) // Changing the fetch profile you can solve the problem
    private List<ContainerSpecification> specifications = new ArrayList<>();

    public void addContainerSpecification(ContainerSpecification containerSpecification) {
        specifications.add(containerSpecification);
        containerSpecification.setDockerNode(this);
    }

    @Override
    public String toString() {
        return "DockerNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalMemory=" + totalMemory +
                ", totalCpu=" + totalCpu +
                '}';
    }
}
