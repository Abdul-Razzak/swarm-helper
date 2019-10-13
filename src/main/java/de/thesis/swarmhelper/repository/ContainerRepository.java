package de.thesis.swarmhelper.repository;

import de.thesis.swarmhelper.domain.ContainerSpecification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface ContainerRepository extends CrudRepository<ContainerSpecification, UUID> {

    List<ContainerSpecification> findAllByIdIsNotNull();

}
