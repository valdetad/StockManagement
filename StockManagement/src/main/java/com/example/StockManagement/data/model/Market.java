package com.example.StockManagement.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "market")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Purchase> purchases;


}
