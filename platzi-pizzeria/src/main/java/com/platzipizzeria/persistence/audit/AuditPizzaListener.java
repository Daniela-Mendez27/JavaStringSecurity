package com.platzipizzeria.persistence.audit;

import com.platzipizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {
    private PizzaEntity currentValue;



    @PostLoad
    public void postLoad(PizzaEntity entity) {
        System.out.println("Post Load");
        this.currentValue = SerializationUtils.clone(entity);

    }

    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity) {
        System.out.println("POST Persist OR UPDATE");
        System.out.println("OLD VALUE" + this.currentValue.toString());
        System.out.println("NEW VALUE" + entity.toString());
    }

    @PreRemove
    public void onPreDelete(PizzaEntity entity) {
        System.out.println(entity.toString());
    }
}