package com.limewater.entity;

import javax.persistence.*;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Entity
public class Seller {

    @Id
    @Column(name = "SELLER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sellerId;

    private String seller;

}
