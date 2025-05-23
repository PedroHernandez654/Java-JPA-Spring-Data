package com.platzi.pizza.persistence.audit;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import jakarta.persistence.*;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;

@MappedSuperclass
public class AuditPizzaListener {
    private PizzaEntity currentValue;

    @PostLoad
    public void postLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);
    }

    //Esto hace que DESPUÉS de realizar la acción haga lo siguiente pero existe el PrePersist que lo ejecuta ANTES de hacer la acción
    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity){

        System.out.println("POST PERSIST OR UPDATE");
        System.out.println("OLD VALUE:" + this.currentValue);
        System.out.println("NEW VALUE:" + entity.toString());
    }

    @PreRemove
    public void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}
