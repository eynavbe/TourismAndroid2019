package com.example.myprojectandroid;

public class WeatherJson {
    String id;
    String name;
    String api;

    public WeatherJson(String id, String name, String api) {
        this.id = id;
        this.name = name;
        this.api = api;
    }

    public WeatherJson() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return "WeatherJson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", api='" + api + '\'' +
                '}';
    }
}
