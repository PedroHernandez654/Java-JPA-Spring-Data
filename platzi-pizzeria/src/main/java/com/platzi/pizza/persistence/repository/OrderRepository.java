package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.OrderEntity;
import com.platzi.pizza.persistence.projection.OrderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByMethodIn(List<String> methods);

    //Esta es una consulta nativa de SQL para poder liberar todas las funciones que tiene SQL
    //Se le agrega el nativeQuery por que si no lo interpetra como JPQL
    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<OrderEntity> findCustomerOrders(@Param("id") String idCustomer);

    //Tener cuidado con los espacios en la consulta, por que pueden haber errores de syntax
    @Query(value =
            "SELECT po.id_order AS idOrder, cu.name AS customerName, po.date AS orderDate," +
            "   po.total AS orderTotal, group_concat(pi.name) AS pizzaName " +
            "FROM pizza_order po " +
            "   INNER JOIN customer cu ON po.id_customer = cu.id_customer " +
            "   INNER JOIN order_pizza oi ON po.id_order = oi.id_order " +
            "   INNER JOIN pizza pi on oi.id_pizza = pi.id_pizza " +
            "WHERE po.id_order = :orderId " +
            "group by po.id_order, cu.name, po.date, po.total", nativeQuery = true)
    OrderSummary findSummary(@Param("orderId")int orderId);

    //De esta manera se llama al store procedure de nuestra BD
    @Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("id_customer") String idCustomer,@Param("method") String method);
}
