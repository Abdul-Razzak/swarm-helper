package de.thesis.swarmhelper.domain;

import lombok.Data;

@Data
public class PlacementConstraints {
    Placement placement;

    public PlacementConstraints toPlacementConstraints(String nodeName) {
        PlacementConstraints constraints = new PlacementConstraints();
        Placement placement = new Placement();
        placement.setConstraints("node.labels.app == " + nodeName);
        constraints.setPlacement(placement);
        return constraints;
    }
}


