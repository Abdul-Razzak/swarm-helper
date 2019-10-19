package de.thesis.swarmhelper.domain;

import lombok.Data;

@Data
public class Placement {
    String constraints;

    //Example value:           - node.labels.app == WorkerNode1
}