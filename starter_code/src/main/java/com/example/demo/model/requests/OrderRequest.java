package com.example.demo.model.requests;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {
    @JsonProperty
    private Long id;

    @JsonProperty
    private List<Item> items;

    @JsonProperty
    private User user;

    @JsonProperty
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
