package de.thesis.swarmhelper.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import de.thesis.swarmhelper.domain.DockerNode;

import javax.transaction.Transactional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface NodeRepository extends CrudRepository<DockerNode, UUID> {

    List<DockerNode> findAllByIdIsNotNull();

    DockerNode findDistinctById(UUID uuid);

    @Transactional
    void deleteAllById(UUID uuid);
}
