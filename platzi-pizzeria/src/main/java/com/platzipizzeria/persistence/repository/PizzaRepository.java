package com.platzipizzeria.persistence.repository;

import com.platzipizzeria.persistence.entity.PizzaEntity;
import com.platzipizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {

   // Corregido: Available con 'a-i'
   List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

   // Retorna Optional para que funcione .orElseThrow() en el Service
   Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

   List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

   List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);

   List<PizzaEntity> findTop3ByAvailableTueAndPriceLessThanOrderByPriceAcs(double price);

   int countByVeganTrue();

   @Query(value =
           "UPDATE pizza " +
                   "SET price = :#{#newPizzaPrice.newPrice} " +
                   "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}", nativeQuery = true)
   @Modifying
   void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);
}