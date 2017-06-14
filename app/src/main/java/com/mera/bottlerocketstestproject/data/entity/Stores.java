package com.mera.bottlerocketstestproject.data.entity;

import java.util.List;


public class Stores {
    private List<JStore> stores;

    public Stores(List<JStore> jStores) {
        this.stores = jStores;
    }

    public List<JStore> getStores() {
        return stores;
    }
}
