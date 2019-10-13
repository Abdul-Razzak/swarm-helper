package de.thesis.swarmhelper.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContainerSpecification {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "application_name")
    String applicationName;

    @Column(name = "related_to")
    String relatedTo;

    @Column(name = "memory_consumption")
    BigDecimal memoryConsumption;

    @Column(name = "cpu_consumption")
    BigDecimal cpuConsumption;

    @Column(name = "deployed_on")
    String deployedOn;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "docker_node_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private DockerNode dockerNode;

    @Override
    public String toString() {
        return "ContainerSpecification{" +
                "id=" + id +
                ", applicationName='" + applicationName + '\'' +
                ", dependentOn='" + relatedTo + '\'' +
                ", memoryConsumption=" + memoryConsumption +
                ", cpuConsumption=" + cpuConsumption +
                ", deployedOn='" + deployedOn + '\'' +
                '}';
    }
}
