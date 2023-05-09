package com.ccoins.coins.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Code")
@Table(name = "CODES")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="per_person")
    private boolean perPerson;

    @Column(name="one_use")
    private boolean oneUse;

    @Column(name="fk_prize")
    private Long prize;

    @Column(name="points")
    private Long points;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_match", referencedColumnName = "id")
    private Match match;
}
