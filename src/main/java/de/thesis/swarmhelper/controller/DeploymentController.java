package de.thesis.swarmhelper.controller;

import de.thesis.swarmhelper.domain.ContainerSpecification;
import de.thesis.swarmhelper.domain.DockerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import de.thesis.swarmhelper.service.DeploymentManagerService;

@RestController
public class DeploymentController {

    private DeploymentManagerService deploymentManagerService;

    @Autowired
    public DeploymentController(DeploymentManagerService deploymentManagerService) {
        this.deploymentManagerService = deploymentManagerService;
    }

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    void addNode(@RequestBody ContainerSpecification specification) {
        deploymentManagerService.addContainerSpecification(specification);
    }


    @RequestMapping(value = "/deploy", method = RequestMethod.GET)
    ResponseEntity<String> getDockerCompose() {
        String specs = deploymentManagerService.composeFile();
        return ResponseEntity.ok(specs);
    }

}
