package com.danqiu.myapplication.bean;

/**
 * Created by Administrator on 2018/12/18.
 */

public class DataUserBean {
    public String name;
    public String age;

    public DataUserBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    //此注解：当data属性值发生改变在setter中发出通知
   // @Bindable
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }
   // @Bindable
    public void setAge(String age) {
        this.age = age;
    }
}
