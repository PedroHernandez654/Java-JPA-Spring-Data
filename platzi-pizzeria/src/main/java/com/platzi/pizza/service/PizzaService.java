package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import com.platzi.pizza.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//Este es el import que necesitamos para el page de nuestro servicio
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pagSortRepository;

    //Inyección de dependencias
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pagSortRepository = pagSortRepository;
    }
    //Los parametros que necesitamos para que podamos utilizar nuestro paginación es "Número de página y cuantos
    //elementos vamos a mostrar
    public Page<PizzaEntity> getAll(int page, int elements){
        //Esto nos permite hacer consultas de SQL y convertirlas a clases JAVA
        //Esto es JDBC
        //return this.jdbcTemplate.query("SELECT * FROM pizza WHERE available = 0", new BeanPropertyRowMapper<>(PizzaEntity.class));
        //return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
        Pageable pageRequest = PageRequest.of(page,elements);
        return this.pagSortRepository.findAll(pageRequest);
    }



    public Page<PizzaEntity>getAvailable(int page, int elements, String sortBy, String sortDirection){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        //De esta manera hacemos uso del ordanamiento con Dirección
        Sort sort  = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page,elements, sort);

        return this .pagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()-> new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity>getWith(String ingredient){
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity>getWithout(String ingredient){
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }
    public List<PizzaEntity>getCheapest(double price){
        return  this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizzaEntity){
        return this.pizzaRepository.save(pizzaEntity);
    }

    public void delete(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }

    //Esta anotación nos brinda las propiedades de ACID
    //Al igual que podemos limitar hasta cuando si hacer o no un rollback ya que puede que 1 acción no dependa de TODO
    @Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED)
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaRepository.updatePrice(dto);
        this.sendEmail();
    }

    private void sendEmail(){
        throw new EmailApiException();
    }

    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }
}
