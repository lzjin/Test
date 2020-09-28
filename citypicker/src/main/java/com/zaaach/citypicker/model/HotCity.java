package com.zaaach.citypicker.model;

import com.zaaach.citypicker.db.DBConfig;

public class HotCity extends City {

    public HotCity(String name, String province, String code) {
        super(name, province, DBConfig.CITY_TAG_TITLE_HOT_ALL, code);
    }
}
