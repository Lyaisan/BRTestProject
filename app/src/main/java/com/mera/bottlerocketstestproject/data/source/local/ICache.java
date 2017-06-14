package com.mera.bottlerocketstestproject.data.source.local;

import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.mera.bottlerocketstestproject.data.entity.Stores;

import java.util.List;

public interface ICache {
    Stores getStores();
    void save(List<JStore> stores);
}
