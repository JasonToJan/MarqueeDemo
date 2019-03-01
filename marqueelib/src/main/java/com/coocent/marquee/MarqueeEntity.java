package com.coocent.marquee;

public class MarqueeEntity {
    private String name;//名称
    private String color;//带#颜色值

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return color;
    }
}
