package com.company;

public class Vertex {
    private String name;
    private int mark;

    public Vertex(String name, int mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() { return name; }
    public int getMark() { return mark; }

    public void setMark(int mark) { this.mark = mark; }
}
