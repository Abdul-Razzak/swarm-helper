package de.thesis.swarmhelper.service;

import de.thesis.swarmhelper.domain.DockerNode;
import de.thesis.swarmhelper.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {

    @Autowired
    private NodeRepository repository;

    public void addNode(DockerNode dockerNode) {
        repository.save(dockerNode);
    }

    public List<DockerNode> getNodes() {
        List<DockerNode> dockerNodes= repository.findAllByIdIsNotNull();
        return dockerNodes;
    }
}
