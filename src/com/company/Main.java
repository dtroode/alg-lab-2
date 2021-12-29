package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    /*
    Хэш-таблица для цветов по каждой вершине.
    Стек для нахождения петлей.
    Массив вершин.
     */
    public static HashMap<Vertex, String> color = new HashMap<>();
    public static Stack<Vertex> stack = new Stack<>();
    public static ArrayList<Vertex> vertexes = null;


    // Метод для вывода стека
    public static void printStack(Stack s, Vertex w) {
        System.out.println(Arrays.toString(
                stack.stream().map((v)-> v.getName()).toArray()));
    }

    public static void dfs(Graph g, Vertex v) {
        // При входе в вершину добавляем её в стек и помечаем серой.
        // Пока мы не покинем вершину она будет оставаться серой.
        stack.push(v);
        color.replace(v, "grey");


        /*
        Для всех смежных вершин выполняем проверку на цвет:
        Если цвет не серый – запускаем алгоритм рекурсивно для этой вершины.
        Если цвет серый – значит мы нашли цикл, добавляем вершину в стек и выводим его.
         */

        int firstIndex = g.first(v);

        // Если смежные вершины существуют
        if (firstIndex != -1) {
            Vertex first = vertexes.get(firstIndex);
            if (color.get(first) != "grey") dfs(g, first);
            else if (color.get(first) == "grey") {
                stack.push(first);
                printStack(stack, first);
                stack.pop();
            }
        }

        int i = 0;
        while (g.next(v, i) != -1) {
            Vertex w = vertexes.get(g.next(v, i));
            if (color.get(w) != "grey") dfs(g, w);
            else if (color.get(w) == "grey") {
                stack.push(w);
                printStack(stack, w);
                stack.pop();
            }
            i++;
        }

        // Когда выходим из вершины достаём её из стека и меняем цвет на чёрный
        stack.pop();
        color.replace(v, "black");
    }

    public static void main(String[] args) {
//        Пример для проверки
//        Graph g = new Graph();
//        g.addVertex("a", 5);
//        g.addVertex("b", 5);
//        g.addVertex("c", 5);
//        g.addVertex("d", 5);
//        g.addVertex("e", 5);
//
//        g.addArc("a", "d", 7);
//        g.addArc("a", "b", 13);
//        g.addArc("b", "c", 2);
//        g.addArc("c", "a", 3);
//        g.addArc("c", "e", 3);
//        g.addArc("d", "c", 5);
//        g.addArc("d", "e", 9);
//        g.addArc("d", "a", 8);
//        g.addArc("d", "b", 2);
//        g.addArc("e", "a", 8);

//        g.printMatrix();

//        vertexes = g.getVertexes();
//        for (Vertex vertex : vertexes) color.put(vertex, "white");
//
//        for (Vertex vertex : vertexes) dfs(g, vertex);
    }
}
