package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private int V;
    private int A;
    private HashMap<Vertex, HashMap<Arc, Integer>> G = new HashMap<>();

    private Vertex alpha = new Vertex(null, 0);

    private ArrayList<Vertex> vertexes = new ArrayList<>();
    private ArrayList<Arc> arcs = new ArrayList<>();

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    // Возвращаем индекс первой смежной с данной вершины. Если смежной нет – -1
    public int first(Vertex v) {
        // Тут мы проходимся по строке матрицы
        for (HashMap.Entry<Arc, Integer> entry : G.get(v).entrySet()) {
            // Ключ – имя столбца, значение – число на пересечении столбца и строки
            Arc key = entry.getKey();
            Integer value = entry.getValue();

            // Если значение неравно 0, значит у нас есть дуга
            if (value == 1) {
                // По ключу достаём вторую вершину
                Vertex other = key.getOther(v);
                // Из дополнительного массива достаём индекс
                // Индекс формируется при добавлении вершины (добавлена позже – больше индекс)
                int i = 0;
                for (Vertex vertex : vertexes) {
                    if (vertex.equals(other)) {
                        break;
                    }
                    i++;
                }
                return i;
            }
        }

        // Если смежной вершины нет, возвращаем -1, стандартная практика
        return -1;
    }

    // Возвращаем индекс вершины смежной с данной, следующей за индексом (локальным)
    // Типа вернуть вторую смежную (i = 1)
    public int next(Vertex v, int i) {
        int j = -1;
        for (HashMap.Entry<Arc, Integer> entry : G.get(v).entrySet()) {
            Arc key = entry.getKey();
            Integer value = entry.getValue();

            // Если значение неравно 0, значит у нас есть дуга
            if (value == 1) {
                j++;
                // Эта проверка покажет, является ли эта вершина нужной нам
                if (j - 1 != i) continue;
                // По ключу достаём вторую вершину
                Vertex other = key.getOther(v);
                // Из дополнительного массива достаём индекс
                // Индекс формируется при добавлении вершины (добавлена позже – больше индекс)
                int k = 0;
                for (Vertex vertex : vertexes) {
                    if (vertex.equals(other)) {
                        break;
                    }
                    k++;
                }
                return k;
            }
        }

        // Если никакая вершина до этого не вернулась (а значит не подошла нам),
        // вернётся -1
        return -1;
    }

    // Получаем вершину по индексу среди смежных
    public Vertex vertex(Vertex v, int i) {
        int j = -1;
        for (HashMap.Entry<Arc, Integer> entry : G.get(v).entrySet()) {
            Arc key = entry.getKey();
            Integer value = entry.getValue();

            // Если значение неравно 0, значит у нас есть дуга
            if (value != 0) {
                j++;
                // Эта проверка покажет, является ли эта вершина нужной нам
                if (j != i) continue;
                // По ключу достаём вторую вершину
                Vertex other = key.getOther(v);
                return other;
            }
        }

        // Если никакая вершина до этого не вернулась (а значит не подошла нам),
        // вернётся нулевая вершина
        return alpha;
    }

    // Добавляем узел
    public void addVertex(String name, int mark) {
        // Создаём вершину и кладём с массив вершин
        Vertex v = new Vertex(name, mark);
        vertexes.add(v);

        // Создаём строку связей новой вершины. Связей пока нет, везде 0
        HashMap<Arc, Integer> connects = new HashMap<>();
        for (Arc arc : arcs) {
            connects.put(arc, 0);
        }

        // Кладём вершину в матрицу
        G.put(v, connects);
    }

    // Добавляем дугу
    public void addArc(Vertex v, Vertex w, int price) {
        if (v == null || w == null) {
            System.out.println("Какой-то вершины нет");
            return;
        }

        // Создаём дугу и кладём с массив дуг
        Arc a = new Arc(v, w, price);
        arcs.add(a);

        // В каждую строку матрицы добавляем новый столбец
        for (Vertex vertex : vertexes) {
            if (vertex.equals(v)) G.get(vertex).put(a, 1);
            else if (vertex.equals(w)) G.get(vertex).put(a, -1);
            else G.get(vertex).put(a, 0);
        }
    }

    // Перегруженный метод, чтоб добавлять дуги к вершинам, обращаясь к ним по именам
    public void addArc(String vName, String wName, int price) {
        Vertex[] vw = getNames(vName, wName);
        Vertex v = vw[0];
        Vertex w = vw[1];

        addArc(v, w, price);
    }

    // Удаляем вершину
    public void delVertex(String name) {
        // Переменная для присвоения этой вершины
        Vertex owrVertex = null;

        // Удаляем вершину из массива вершин и её строку из матрицы
        for (Vertex vertex : vertexes) {
            if (vertex.getName().equals(name)) {
                owrVertex = vertex;
                vertexes.remove(vertex);
                G.remove(vertex);
                break;
            }
        }

        // Если мы нашли такую вершину, то удаляем все связи с ней
        // Для этого удаляем все столбцы с этой вершиной
        if (owrVertex != null) {
            for (HashMap<Arc, Integer> value : G.values()) {
                for (Arc key : value.keySet()) {
                    // Смотрим, находится ли данная вершина в проверяемой дуге
                    if (key.contains(owrVertex)) {
                        value.remove(key);
                        return;
                    }
                }
            }
            System.out.println("Вершины с именем " + name + " нет");
        }
    }

    // Удаляем дугу
    public void delArc(Vertex v, Vertex w) {
        // Удаляем дугу из списка дуг
        for (Arc arc : arcs) {
            if (arc.contains(v) && arc.contains(w)) {
                arcs.remove(arc);
                break;
            }
        }

        // Удаляем дугу из матрицы
        for (HashMap<Arc, Integer> value : G.values()) {
            for (Arc key : value.keySet()) {
                // Смотрим, находятся ли обе вершины в проверяемой дуге
                if (key.contains(v) && key.contains(w)) {
                    value.remove(key);
                    return;
                }
            }
        }
        System.out.println("(" + v.getName() + ", " + w.getName() + "): " + "такой дуги нет");
    }

    // Перегруженный метод для удаления дуги по именам вершин
    public void delArc(String vName, String wName) {
        Vertex[] vw = getNames(vName, wName);
        Vertex v = vw[0];
        Vertex w = vw[1];

        delArc(v, w);
    }

    // Меняем значение вершины
    public void editVertex(String name, int mark) {
        for (Vertex vertex : vertexes) {
            if (vertex.getName().equals(name)) {
                vertex.setMark(mark);
                return;
            }
        }
        System.out.println("Вершины с именем " + name + " нет");
    }

    // Меняем вес дуги
    public void editArc(Vertex v, Vertex w, int price) {
        for (Arc arc : arcs) {
            if (arc.contains(v) && arc.contains(w)) {
                arc.setPrice(price);
                return;
            }
        }
        System.out.println("(" + v.getName() + ", " + w.getName() + "): " + "такой дуги нет");
    }

    // Перегруженный метод для редактирования дуги по именам вершин
    public void editArc(String vName, String wName, int price) {
        Vertex[] vw = getNames(vName, wName);
        Vertex v = vw[0];
        Vertex w = vw[1];

        editArc(v, w, price);
    }

    // Метод для получения вершин по именам
    public Vertex[] getNames(String vName, String wName) {
        Vertex v = null;
        Vertex w = null;

        for (Vertex vertex : vertexes) {
            if (vertex.getName().equals(vName)) v = vertex;
            else if (vertex.getName().equals(wName)) w = vertex;
        }

        return new Vertex[]{v, w};
    }

    // Проверка являются ли вершины соседями
    public Boolean isNeighbors(Vertex v, Vertex w) {
        for (Arc arc : arcs) {
            if (arc.contains(v) && arc.contains(w)) {
                return true;
            }
        }
        return false;
    }

    // Метод для вывода всей матрицы
    public void printMatrix() {
        // Шапка таблицы
        String arcNames = "";
        for (Arc arc : arcs) {
            arcNames += "\t(" + arc.getFirst().getName()
                    + ", " + arc.getSecond().getName() + ")";
        }
        System.out.println(arcNames);

        for (Vertex vertex : vertexes) {
            String vertexName = vertex.getName();

            String connections = "" ;

            for (Arc arc : arcs) {
                connections += "\t\t" + G.get(vertex).get(arc).toString();
            }

            System.out.println(vertexName + connections);
        }
    }
}
