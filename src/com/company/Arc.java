package com.company;

public class Arc {
    private Vertex first;
    private Vertex second;
    private int price;

    public Arc(Vertex first, Vertex second, int price) {
        this.first = first;
        this.second = second;
        this.price = price;
    }

    public Vertex getFirst() { return first; }
    public Vertex getSecond() { return second; }
    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public Vertex getOther(Vertex v) {
        return (first.equals(v)) ? second : (second.equals(v)) ? first : null;
    }

    public Boolean contains(Vertex v) {
        return (first.equals(v) || second.equals(v)) ? true : false;
    }
}
