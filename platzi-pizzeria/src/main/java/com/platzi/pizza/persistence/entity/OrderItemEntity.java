package com.platzi.pizza.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="order_pizza")
@IdClass(OrderItemId.class)
@Getter
@Setter
@NoArgsConstructor
public class OrderItemEntity {
    @Id
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;
    @Id
    @Column(name = "id_item", nullable = false)
    private Integer idItem;
    @Column(name = "id_pizza", nullable = false)
    private Integer idPizza;
    @Column(nullable = false, columnDefinition = "Decimal(2,1)")
    private Double quantity;
    @Column(nullable = false, columnDefinition = "Decimal(5,2)")
    private Double price;

    //Siempre las relaciones son al final como buena práctica
    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", insertable = false, updatable = false)
    @JsonIgnore
    /*Al tener un llamado igual en OrderEntity se hace un loop al tratar de formar el JSON por lo que es necesario
    poner este JsonIgnore para que no entre en loop infinito*/
    private OrderEntity order;

    @OneToOne
    //De esta manera aplicamos la relación con la columna de la tabla
    @JoinColumn(name = "id_pizza", referencedColumnName = "id_pizza", insertable = false, updatable = false)
    private PizzaEntity pizza;
}
