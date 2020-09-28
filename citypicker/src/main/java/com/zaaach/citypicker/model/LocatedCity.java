package com.zaaach.citypicker.model;

import com.zaaach.citypicker.db.DBConfig;

public class LocatedCity extends City {

    public LocatedCity(String name, String province, String code) {
        super(name, province, DBConfig.CITY_TAG_TITLE_HISTORY_ALL, code);
    }
}
