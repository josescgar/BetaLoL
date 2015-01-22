package com.escobeitor.betalol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Represents a League of Lengends Champion
 * Created by escobeitor on 1/21/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable
public class Champion {

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @DatabaseField(columnName = FIELD_ID, uniqueIndex = true, id = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
