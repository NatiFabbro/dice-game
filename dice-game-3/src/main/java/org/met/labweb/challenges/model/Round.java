package org.met.labweb.challenges.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Round {
    ONE("****************************************************" +
            "\n***************** ROUND 1. FIGHT!! *****************" +
            "\n****************************************************"),
    TWO("****************************************************" +
            "\n************** ROUND 2. FINISH THEM!! **************" +
            "\n****************************************************");

    public String message;
}
