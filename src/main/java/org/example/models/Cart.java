package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private Integer closed;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @Column(name = "creation_time")
    private Long creationTime;

}
