package quizappmainappforamiusa.example.quizapp;

class Fav {

    private String name,date,topic;

    Fav(String name, String date,String topic) {
        this.name = name;
        this.date = date;
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}