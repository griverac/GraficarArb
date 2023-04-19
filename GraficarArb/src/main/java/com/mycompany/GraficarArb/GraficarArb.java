package com.mycompany.GraficarArb;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GraficarArb {

    static class Nodo {
        String valor;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(String valor) {
            this.valor = valor;
            izquierdo = null;
            derecho = null;
        }
    }

    static Nodo crearArbol(String expresion) {
        expresion = expresion.replaceAll("\\s+", ""); // Eliminar espacios en blanco
        if (expresion.length() == 0) {
            return null;
        }

        // Buscar el operador de menor precedencia en la expresión
        int balance = 0;
        for (int i = expresion.length() - 1; i >= 0; i--) {
            char c = expresion.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            } else if (balance == 0 && (c == '+' || c == '-' || c == '*' || c == '/')) {
                Nodo nodo = new Nodo(Character.toString(c));
                nodo.izquierdo = crearArbol(expresion.substring(0, i));
                nodo.derecho = crearArbol(expresion.substring(i + 1));
                return nodo;
            }
        }

        // Si no se encuentra un operador, se asume que la expresión es un valor
        if (expresion.charAt(0) == '(' && expresion.charAt(expresion.length() - 1) == ')') {
            return crearArbol(expresion.substring(1, expresion.length() - 1));
        }

        return new Nodo(expresion);
    }

    static void imprimirArbol(Nodo raiz) {
        if (raiz == null) {
            return;
        }

        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            int nivelSize = cola.size();

            for (int i = 0; i < nivelSize; i++) {
                Nodo nodo = cola.poll();
                System.out.print(nodo.valor + " ");

                if (nodo.izquierdo != null) {
                    cola.add(nodo.izquierdo);
                }

                if (nodo.derecho != null) {
                    cola.add(nodo.derecho);
                }
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        String expresion;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Ingrese una expresión aritmética: ");
            expresion = sc.nextLine();
        }

        Nodo raiz = crearArbol(expresion);
        System.out.println("Árbol de Expresiones: ");
        imprimirArbol(raiz);
    }
}