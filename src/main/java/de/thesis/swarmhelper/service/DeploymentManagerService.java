package de.thesis.swarmhelper.service;

import de.thesis.swarmhelper.domain.ContainerSpecification;
import de.thesis.swarmhelper.domain.DockerNode;
import de.thesis.swarmhelper.repository.ContainerRepository;
import de.thesis.swarmhelper.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DeploymentManagerService {

    @Autowired
    private ContainerRepository repository;

    @Autowired
    private NodeRepository nodeRepository;

    public void addContainerSpecification(ContainerSpecification specification) {
        List<DockerNode> dockerNodes = nodeRepository.findAllByIdIsNotNull();
        DockerNode preferredNode = null;
        if(!"".equalsIgnoreCase(specification.getRelatedTo())) {
            for (DockerNode dockerNode : dockerNodes) {
                for (ContainerSpecification containerSpecification : dockerNode.getSpecifications()) {
                    if (containerSpecification.getApplicationName().equalsIgnoreCase(specification.getRelatedTo())) {
                        if (dockerNode.getTotalCpu().compareTo(specification.getCpuConsumption()) != -1
                                && dockerNode.getTotalMemory().compareTo(specification.getMemoryConsumption()) != -1) {
                            preferredNode = dockerNode;
                        }
                    }
                }
            }
        }
        if(preferredNode==null) {
            for(DockerNode dockerNode: dockerNodes) {
                if (dockerNode.getTotalCpu().compareTo(specification.getCpuConsumption()) != -1
                        && dockerNode.getTotalMemory().compareTo(specification.getMemoryConsumption()) != -1) {
                    preferredNode = dockerNode;
                    break;
                }
            }
        }

        if (Objects.isNull(preferredNode) == false) {
            deployAndUpdateNode(specification, preferredNode);
        }
    }

    private DockerNode deployAndUpdateNode(ContainerSpecification specification, DockerNode dockerNode) {
            dockerNode.setTotalCpu(dockerNode.getTotalCpu().subtract(specification.getCpuConsumption()));
            dockerNode.setTotalMemory(dockerNode.getTotalMemory().subtract(specification.getMemoryConsumption()));
            nodeRepository.deleteAllById(dockerNode.getId());
            dockerNode.addContainerSpecification(specification);
            return nodeRepository.save(dockerNode);
    }

    public List<ContainerSpecification> getContainers() {
        List<ContainerSpecification> containerSpecifications= repository.findAllByIdIsNotNull();
        return containerSpecifications;
    }

    public String composeFile() {

        Map<String, Object> data = new HashMap<String, Object>();
        List<DockerNode> dockerNodes = nodeRepository.findAllByIdIsNotNull();
        Map<String, Object> appConfigs = new HashMap<String, Object>();
        for(DockerNode node: dockerNodes) {
            for(ContainerSpecification specification: node.getSpecifications()) {
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("image", "user/" + specification.getApplicationName() + ":latest");
                innerMap.put("ports", "8080:8080");
                innerMap.put("networks", "webnet");
                appConfigs.put(specification.getApplicationName(), innerMap);
            }
        }
        data.put("services", appConfigs);
        data.put("version", "3");
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);
        return writer.toString();
    }
}
