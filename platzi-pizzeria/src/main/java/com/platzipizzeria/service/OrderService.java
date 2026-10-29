package com.platzipizzeria.service;


import com.platzipizzeria.persistence.entity.OrderEntity;
import com.platzipizzeria.persistence.projection.OrderSummary;
import com.platzipizzeria.persistence.repository.OrderRepository;
import com.platzipizzeria.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SITE = "S";

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        List<OrderEntity> orders = orderRepository.findAll();
        orders.forEach(o-> System.out.println(o.getCustomer().getName()) );
        return this.orderRepository.findAll();
    }

    public List<OrderEntity> getTodayOrders() {
        LocalDateTime today = LocalDate.now().atTime(0, 0);
        return this.orderRepository.finAllByDateAfter(today);
    }
    public List<OrderEntity> getOutsideOrders() {
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return this.orderRepository.findAllByMethodIn(methods);
    }
    @Secured("ROLE ADMIN")
    public List<OrderEntity> getCustomerOrders(@Param("id") String idCustomer) {
        return this.orderRepository.findCustomerOrders(idCustomer);
    }
    public OrderSummary getSummany(int orderId) {
        return this.orderRepository.findSummary(orderId);
    }
    public boolean saveRandomOrder(RandomOrderDto randomOrderDto) {
        return this.orderRepository.saveRandomOrder(randomOrderDto.getIdCustomer(), randomOrderDto.getMethod());
    }

}
