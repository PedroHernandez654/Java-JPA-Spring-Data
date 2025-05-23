package com.platzi.pizza.persistence.entity;

import com.platzi.pizza.persistence.audit.AuditPizzaListener;
import com.platzi.pizza.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Table(name="pizza")
//Con esto hacemos que pueda utilizar la clase auditableEntity
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class})
@Getter
@Setter
@NoArgsConstructor              //Con esto heredamos las propiedades de la clase y se crean en BD los registros
public class PizzaEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pizza",nullable = false)
    private Integer idPizza;
    @Column(nullable = false, length = 30, unique = true)
    private String name;
    @Column(nullable = false, length = 150)
    private String description;
    @Column(nullable = false, columnDefinition = "Decimal(5,2)")
    private Double price;
    @Column()
    private Boolean vegetarian;
    @Column()
    private Boolean vegan;
    @Column(nullable = false)
    private Boolean available;


    @Override
    public String toString() {
        return "PizzaEntity{" +
                "idPizza=" + idPizza +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", available=" + available +
                '}';
    }
}
