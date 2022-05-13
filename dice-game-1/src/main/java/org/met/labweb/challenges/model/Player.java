package org.met.labweb.challenges.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Player {
    private Integer id;
    private String name;
    private String bet;
    private Integer score;
}
