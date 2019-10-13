package de.thesis.swarmhelper.controller;

import de.thesis.swarmhelper.domain.DockerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.thesis.swarmhelper.service.NodeService;

import java.util.List;

@RestController
public class NodeController {

    private NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @RequestMapping(value = "/node", method = RequestMethod.POST)
    void addNode(@RequestBody DockerNode dockerNode) {
        nodeService.addNode(dockerNode);
    }

    @RequestMapping(value = "/node", method = RequestMethod.GET)
    ResponseEntity<List<DockerNode>> getNode() {
        List<DockerNode> dockerNodes = nodeService.getNodes();
        return ResponseEntity.ok(dockerNodes);
    }

}
