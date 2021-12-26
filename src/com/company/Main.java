package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Main {

    public static HashMap<Vertex, String> color = new HashMap<>();
    public static Stack<Vertex> stack = new Stack<>();
    public static ArrayList<Vertex> vertexes = null;

    // Эта штука сейчас вроде работает, но выводит весь стэк, а не только ту часть, где есть петля
    public static void printStack(Stack s, Vertex w) {
        System.out.println(Arrays.toString(stack.stream().map((v) -> v.getName()).toArray()));
        stack.pop();
    }

    public static void dfs(Graph g, Vertex v) {
        stack.push(v);
        color.replace(v, "grey");

        Vertex first = vertexes.get(g.first(v));
        if (color.get(first) != "grey") dfs(g, first);
        else if (color.get(first) == "grey") {
            stack.push(first);
            printStack(stack, first);
        }

        int i = 0;
        while (g.next(v, i) != -1) {
            Vertex w = vertexes.get(g.next(v, i));
            if (color.get(w) != "grey") dfs(g, w);
            else if (color.get(w) == "grey") {
                stack.push(w);
                printStack(stack, w);
            }
            i++;
        }

        stack.pop();
        color.replace(v, "black");
    }

    public static void main(String[] args) {
	    Graph g = new Graph();
        g.addVertex("a", 5);
        g.addVertex("b", 5);
        g.addVertex("c", 5);
        g.addVertex("d", 5);
        g.addVertex("e", 5);
        g.addVertex("f", 5);

        g.addArc("a", "d", 7);
        g.addArc("a", "b", 13);
        g.addArc("b", "c", 2);
        g.addArc("c", "a", 3);
        g.addArc("c", "e", 3);
        g.addArc("d", "c", 5);
        g.addArc("d", "e", 9);
        g.addArc("d", "a", 8);
        g.addArc("d", "b", 2);
        g.addArc("e", "a", 8);
        g.addArc("e", "f", 8);
        g.addArc("f", "d", 8);

        g.printMatrix();

        vertexes = g.getVertexes();
        for (Vertex vertex : vertexes) color.put(vertex, "white");

//        System.out.println(vertexes.get(0).getName());
        dfs(g, vertexes.get(0));

//        g.delArc("d", "c");
//        g.delVertex("e");

    }
}
