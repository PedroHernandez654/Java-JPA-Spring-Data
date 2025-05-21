package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
    /*De esta manera podemos regresar solo 1 VALOR
    PizzaEntity findFirstByAvailableTrueAndNameIgnoreCase(String name);*/

    //Manera en la que podemos mandar o no el dato
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);
    int countByVeganTrue();

    /*
    @Query(value = "UPDATE pizza SET price = :newPrice " +
            "WHERE id_pizza = :idPizza",nativeQuery = true)
    void updatePrice(@Param("idPizza")int idPizza, @Param("newPrice")double newPrice);*/

    //Uso de Spring Expression Language
    @Query(value = "UPDATE pizza SET price = :#{#newPizzaPrice.newPrice} " +
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}",nativeQuery = true)
    //Un @Query solo hace por defecto un SELECT, si necesitamos que haga otras operaciones se debe poner Modifying
    @Modifying
    void updatePrice(@Param("newPizzaPrice")UpdatePizzaPriceDto newPizzaPrice);
}
