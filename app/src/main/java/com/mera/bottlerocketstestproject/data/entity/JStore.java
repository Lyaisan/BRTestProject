package com.mera.bottlerocketstestproject.data.entity;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Entity of DB
 */
public class JStore extends SugarRecord {
    private String address;
    private String city;
    private String name;
    private String latitude;
    private String zipcode;
    private String storeLogoURL;
    private String phone;
    private String longitude;
    private String storeID;
    private String state;

    public JStore(){
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStoreLogoURL() {
        return storeLogoURL;
    }

    public String getPhone() {
        return phone;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getStoreID() {
        return storeID;
    }

    public String getState() {
        return state;
    }

    /**
     * Getting all rows from table
     */
    public static List<JStore> findAll(){
        List<JStore> list = new ArrayList<>();
        Iterator<JStore> iterator = findAll(JStore.class);
        while (iterator.hasNext()){
            JStore store = iterator.next();
            list.add(store);
        }
        return list;
    }

    /**
     * Finding store by storeID
     */
    public static JStore findStoreById(String storeID){
        List<JStore> selectedStores = JStore.find(JStore.class, "STORE_ID = ?", String.valueOf(storeID));
        if (selectedStores != null && selectedStores.size() > 0){
            return selectedStores.get(0);
        }
        return null;
    }
}
