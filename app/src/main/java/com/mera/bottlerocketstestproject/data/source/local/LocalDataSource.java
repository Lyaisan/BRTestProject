package com.mera.bottlerocketstestproject.data.source.local;

import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.mera.bottlerocketstestproject.data.entity.Stores;

import java.util.List;

public class LocalDataSource implements ICache {

    @Override
    public Stores getStores() {
        return new Stores(JStore.findAll());
    }

    @Override
    public void save(List<JStore> stores) {
        JStore.deleteAll(JStore.class);
        for (JStore store : stores) {
            store.save();
        }
    }
}
