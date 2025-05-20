package com.platzi.pizza.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;
    @Column(name = "id_customer", nullable = false, length = 15)
    private String idCustomer;
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date;
    @Column(nullable = false, columnDefinition = "DECIMAL(6,2)")
    private Double total;
    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String method;
    @Column(name = "additional_notes", length = 200)
    private String additionalNotes;


    @OneToOne(fetch = FetchType.LAZY)/*Esto significa que no va a cargar la relación en caso de que no la utilices*/
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer", insertable = false, updatable = false)
    @JsonIgnore//Si no queremos todo el modelo del cliente
    private CustomerEntity customer;
    //Se le coloca el nombre del atributo que está en la clase que tiene ManyToOne o sea con la relación contraria
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)/*Esto siginifica que si o si me traiga la relación*/
    private List<OrderItemEntity> items;
}
