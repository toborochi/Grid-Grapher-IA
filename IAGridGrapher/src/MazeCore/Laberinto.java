package MazeCore;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Laberinto {

    Queue<Point> ins;
    public LinkedList<int[][]> results = new LinkedList<>();
    public LinkedList<Integer> steps = new LinkedList<>();
    int c = 0;
    public int answers = 0;
    public int maxSteps = 0;

    public void setAnswers(int m) {
        answers = m;
    }
    
    public void setSteps(int m) {
        maxSteps = m;
    }

    private void mostrar(int m[][]) {
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[i].length; ++j) {
                System.out.print("" + m[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("--------------");
    }

    int floodSize = 0;

    private void flood(int m[][], int i, int j) {
        if (!(m[i][j] == 0 && i >= 0 && i < m.length && j >= 0 && j < m[0].length)) {
            return;
        }
        m[i][j] = 1;
        floodSize++;
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = L1.removeFirst();
            flood(m, R.fil, R.col);
        }

    }

    public Queue<Point> Instrucciones(int m[][], int i, int j, int ifin, int jfin) {

        m[i][j] = 0;

        floodSize = 0;
        int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        flood(copy, i, j);

        System.out.println("Tamano Area: " + floodSize);

        m[i][j] = 0;

        LinkedList<Point> L = new LinkedList<>();
        results.clear();
        steps.clear();

        ins = new LinkedList();
        c = 0;

        // SIN HEURISITCA / TODAS LAS SOLUCIONES
        // laberintoA(m, i, j, 1,L);
        // laberintoB(m, i, j, 1,L);
        // laberintoC(m, i, j, ifin, jfin, 1, L);
        // torreA(m, i, j, 1,L);
        // torreB(m, i, j, 1,L);
        // torreC(m, i, j, ifin, jfin, 1, L);
        // alfilA(m, i, j, 1,L);
        // alfilB(m, i, j, 1,L);
        // alfilC(m, i, j, ifin, jfin, 1, L);
        // reinaA(m, i, j, 1,L);
        // reinaB(m, i, j, 1,L);
        // reinaC(m, i, j, ifin, jfin, 1, L);
        
        // CON HEURISITCA
        // laberintoHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
         //laberintoHeuristica2(m, i, j, ifin, jfin, 1, L); // PtoP
        // laberintoHeuristica3(m, i, j, ifin, jfin, 1, L); // Distancia Vertical/Horizontal
        
        // reyHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
        // reyHeuristica2(m, i, j, ifin, jfin, 1, L);  // PtoP
        // reyHeuristica3(m, i, j, ifin, jfin, 1, L);  //  Distancia Vertical/Horizontal

        // caballoHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
        // caballoHeuristica2(m, i, j, ifin, jfin, 1, L);  // PtoP
        // caballoHeuristica3(m, i, j, ifin, jfin, 1, L);  //  Distancia Vertical/Horizontal

        // alfilHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
         alfilHeuristica2(m, i, j, ifin, jfin, 1, L);  // PtoP
        // alfilHeuristica3(m, i, j, ifin, jfin, 1, L);  //  Distancia Vertical/Horizontal

        // reinaHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
        // reinaHeuristica2(m, i, j, ifin, jfin, 1, L);  // PtoP
        // reinaHeuristica3(m, i, j, ifin, jfin, 1, L);  //  Distancia Vertical/Horizontal
        
        // torreHeuristica(m, i, j, ifin, jfin, 1, L);  // Manhattan 
         torreHeuristica2(m, i, j, ifin, jfin, 1, L);  // PtoP
        // torreHeuristica3(m, i, j, ifin, jfin, 1, L);  //  Distancia Vertical/Horizontal
        
        System.out.println("InicioX: " + i);
        System.out.println("InicioY: " + j);
        System.out.println("FinY: " + ifin);
        System.out.println("FinY: " + jfin);
        System.out.println("Soluciones: " + c);
        System.out.println("Movimientos: " + ins.size());
        System.out.println(ins);
        return ins;
    }

    public boolean posValida(int m[][], int i, int j) {

        return i >= 0 && i < m.length
                && j >= 0 && j < m[i].length && (m[i][j] == 0);
    }

    /* //////////////////////////////////////////////////////////////////////////////
     Problemas sin Heuristica
    ///////////////////////////////////////////////////////////////////////////////*/
    // LABERINTO
    private LinkedList<Regla> reglasAplicables(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        if (posValida(m, i, j - 1)) {
            L1.add(new Regla(i, j - 1));
        }
        if (posValida(m, i - 1, j)) {
            L1.add(new Regla(i - 1, j));
        }
        if (posValida(m, i, j + 1)) {
            L1.add(new Regla(i, j + 1));
        }
        if (posValida(m, i + 1, j)) {
            L1.add(new Regla(i + 1, j));
        }

        return L1;
    }

    // TODOS LOS CAMINOS POSIBLES
    private void laberintoA(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        m[i][j] = paso;

        // ANADIR ESTO
        //mostrar(m);
        steps.add(paso);
        c++;
        acum.addLast(new Point(i, j));
        int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        results.add(copy);
        for (int k = 0; k < acum.size(); ++k) {
            ins.add((Point) acum.get(k).clone());
        }

        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                laberintoA(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // TODOS LOS CAMINOS QUE RECORREN TODAS LAS CELDAS
    private void laberintoB(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        acum.addLast(new Point(i, j));
        m[i][j] = paso;

        if (paso == floodSize) {
            // ANADIR ESTO
            //mostrar(m);
            steps.add(paso);
            c++;
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
        }
        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                laberintoB(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // TODOS LOS CAMINOS DE PUNTO INICIO A FIN (ARBITRARIOS)
    private void laberintoC(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        acum.addLast(new Point(i, j));

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }

        }

        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                laberintoC(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // TORRE
    private LinkedList<Regla> reglasAplicablesTorre(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();

        int pi, pj;

        pi = i - 1;
        pj = j;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
        }

        pi = i;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pj--;
        }

        pi = i + 1;
        pj = j;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
        }

        pi = i;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pj++;
        }

        return L1;
    }

    // TODOS LOS CAMINOS POSIBLES
    private void torreA(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        m[i][j] = paso;

        // ANADIR ESTO
        //mostrar(m);
        steps.add(paso);
        c++;
        acum.addLast(new Point(i, j));
        int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        results.add(copy);
        for (int k = 0; k < acum.size(); ++k) {
            ins.add((Point) acum.get(k).clone());
        }

        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                torreA(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    private void torreB(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        acum.addLast(new Point(i, j));
        m[i][j] = paso;

        if (paso == floodSize) {
            // ANADIR ESTO
            //mostrar(m);
            steps.add(paso);
            c++;
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                torreB(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    private void torreC(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        acum.addLast(new Point(i, j));

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }

        }

        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                torreC(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // ALFIL
    private LinkedList<Regla> reglasAplicablesAlfil(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();

        int pi, pj;

        pi = i - 1;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
            pj--;
        }

        pi = i + 1;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
            pj--;
        }

        pi = i + 1;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
            pj++;
        }

        pi = i - 1;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
            pj++;
        }

        return L1;
    }

    // TODOS LOS CAMINOS POSIBLES
    private void alfilA(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        m[i][j] = paso;

        // ANADIR ESTO
        //mostrar(m);
        steps.add(paso);
        c++;
        acum.addLast(new Point(i, j));
        int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        results.add(copy);
        for (int k = 0; k < acum.size(); ++k) {
            ins.add((Point) acum.get(k).clone());
        }

        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                alfilA(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // TODOS LOS CAMINOS QUE RECORREN TODAS LAS CELDAS
    private void alfilB(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        acum.addLast(new Point(i, j));
        m[i][j] = paso;

        if (paso == floodSize) {
            // ANADIR ESTO
            //mostrar(m);
            steps.add(paso);
            c++;
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                alfilB(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // TODOS LOS CAMINOS DE PUNTO INICIO A FIN (ARBITRARIOS)
    private void alfilC(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        acum.addLast(new Point(i, j));

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }

        }

        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                alfilC(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    // REINA
    private LinkedList<Regla> reglasAplicablesReina(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();

        int pi, pj;

        pi = i - 1;
        pj = j;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
        }

        pi = i - 1;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
            pj--;
        }

        pi = i;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pj--;
        }

        pi = i + 1;
        pj = j - 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
            pj--;
        }

        pi = i + 1;
        pj = j;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
        }

        pi = i + 1;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi++;
            pj++;
        }

        pi = i;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pj++;
        }

        pi = i - 1;
        pj = j + 1;
        while (posValida(m, pi, pj)) {
            L1.add(new Regla(pi, pj));
            pi--;
            pj++;
        }

        return L1;
    }

    private void reinaA(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        m[i][j] = paso;

        // ANADIR ESTO
        //mostrar(m);
        steps.add(paso);
        c++;
        acum.addLast(new Point(i, j));
        int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        results.add(copy);
        for (int k = 0; k < acum.size(); ++k) {
            ins.add((Point) acum.get(k).clone());
        }

        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                reinaA(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    private void reinaB(int m[][], int i, int j, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }
        acum.addLast(new Point(i, j));
        m[i][j] = paso;

        if (paso == floodSize) {
            // ANADIR ESTO
            //mostrar(m);
            steps.add(paso);
            c++;
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
        }
        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                reinaB(m, R.fil, R.col, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    private void reinaC(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        acum.addLast(new Point(i, j));

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }

        }

        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        if (c < answers || answers == -1) {
            while (!L1.isEmpty()) {
                Regla R = L1.removeFirst();
                reinaC(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                m[R.fil][R.col] = 0;

            }
        }

        // ANADIR ESTO
        acum.removeLast();
    }

    /* //////////////////////////////////////////////////////////////////////////////
     Problemas con Heuristica
     ///////////////////////////////////////////////////////////////////////////////*/
    public static Regla mejorRegla(LinkedList<Regla> L, int ifin, int jfin) {
        int k = 0;
        int minimo = Integer.MAX_VALUE;
        for (int i = 0; i < L.size(); ++i) {
            int ival = L.get(i).fil;
            int jval = L.get(i).col;
            int m = manhattan(ival, jval, ifin, jfin);
            if (m <= minimo) {
                minimo = m;
                k = i;
            }
        }
        return L.remove(k);
    }

    public static Regla mejorReglaPtoP(LinkedList<Regla> L, int ifin, int jfin) {
        int k = 0;
        double minimo = Double.MAX_VALUE;
        for (int i = 0; i < L.size(); ++i) {
            int ival = L.get(i).fil;
            int jval = L.get(i).col;
            double m = PtoP(ival, jval, ifin, jfin);
            if (m <= minimo) {
                minimo = m;
                k = i;
            }
        }
        return L.remove(k);
    }

    public static Regla mejorReglaDistance(LinkedList<Regla> L, int ifin, int jfin) {
        int k = 0;
        double minimo = Double.MAX_VALUE;
        for (int i = 0; i < L.size(); ++i) {
            int ival = L.get(i).fil;
            int jval = L.get(i).col;
            double m = distanceRect(ival, jval, ifin, jfin);
            if (m <= minimo) {
                minimo = m;
                k = i;
            }
        }
        return L.remove(k);
    }

    private static int manhattan(int a, int b, int c, int d) {
        return Math.abs(c - a) + Math.abs(d - b);
    }

    private static double PtoP(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static double distanceRect(double x1, double y1, double x2, double y2) {
        return Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    private void laberintoHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j) && paso>25) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);
        //mostrar(m);
        
        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                laberintoHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                // ANADIR ESTO
                acum.removeLast();
                m[R.fil][R.col] = 0;
            }
            

        }

    }

    private void laberintoHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                laberintoHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                // ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }

    private void laberintoHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicables(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                laberintoHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                // ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }

    private LinkedList<Regla> reglasAplicablesRey(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        if (posValida(m, i, j - 1)) {
            L1.add(new Regla(i, j - 1));
        }
        if (posValida(m, i - 1, j - 1)) {
            L1.add(new Regla(i - 1, j - 1));
        }

        if (posValida(m, i - 1, j)) {
            L1.add(new Regla(i - 1, j));
        }

        if (posValida(m, i - 1, j + 1)) {
            L1.add(new Regla(i - 1, j + 1));
        }

        if (posValida(m, i, j + 1)) {
            L1.add(new Regla(i, j + 1));
        }

        if (posValida(m, i + 1, j + 1)) {
            L1.add(new Regla(i + 1, j + 1));
        }

        if (posValida(m, i + 1, j)) {
            L1.add(new Regla(i + 1, j));
        }

        if (posValida(m, i + 1, j - 1)) {
            L1.add(new Regla(i + 1, j - 1));
        }

        return L1;
    }

    private void reyHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesRey(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reyHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void reyHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesRey(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reyHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void reyHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesRey(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reyHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    public LinkedList<Regla> reglasAplicablesCaballo(int m[][], int i, int j) {
        LinkedList<Regla> L1 = new LinkedList();
        
        
        
        // Izquierda
        if (posValida(m, i+1, j - 2)) {
            L1.add(new Regla(i+1, j - 2));
        }
        
        if (posValida(m, i-1, j - 2)) {
            L1.add(new Regla(i-1, j - 2));
        }
        
        // Arriba
        if (posValida(m, i - 2, j-1)) {
            L1.add(new Regla(i - 2, j-1));
        }
        
        if (posValida(m, i - 2, j+1)) {
            L1.add(new Regla(i - 2, j+1));
        }
        
        // Derecha
        if (posValida(m, i-1, j + 2)) {
            L1.add(new Regla(i-1, j + 2));
        }
        
        if (posValida(m, i+1, j + 2)) {
            L1.add(new Regla(i+1, j + 2));
        }
        
        // Abajo
        if (posValida(m, i + 2, j+1)) {
            L1.add(new Regla(i + 2, j+1));
        }
        
        if (posValida(m, i + 2, j-1)) {
            L1.add(new Regla(i + 2, j-1));
        }

        return L1;
    }
    
    private void caballoHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesCaballo(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                caballoHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void caballoHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesCaballo(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                caballoHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void caballoHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesCaballo(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                caballoHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    
    private void alfilHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                alfilHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void alfilHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                alfilHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void alfilHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesAlfil(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                alfilHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    
    private void reinaHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reinaHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void reinaHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reinaHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void reinaHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesReina(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                reinaHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    
    
    private void torreHeuristica(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorRegla(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                torreHeuristica(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void torreHeuristica2(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaPtoP(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                torreHeuristica2(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }
    
    private void torreHeuristica3(int m[][], int i, int j, int ifin, int jfin, int paso, LinkedList<Point> acum) {

        if (!posValida(m, i, j)) {
            return;
        }

        // ANADIR ESTO
        Point p = new Point(i, j);
        acum.addLast(p);

        m[i][j] = paso;
        //mostrar(m);
        if (i == ifin && j == jfin) {
            c++;
            steps.add(paso);
            int[][] copy = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
            results.add(copy);
            //mostrar(copy);
            for (int k = 0; k < acum.size(); ++k) {
                ins.add((Point) acum.get(k).clone());
            }
            return;
        }

        LinkedList<Regla> L1 = reglasAplicablesTorre(m, i, j);
        while (!L1.isEmpty()) {
            Regla R = mejorReglaDistance(L1, ifin, jfin);
            if ((c < answers || answers == -1) && paso<maxSteps) {
                torreHeuristica3(m, R.fil, R.col, ifin, jfin, paso + 1, acum);
                //ANADIR ESTO
                acum.removeLast();
            }
            m[R.fil][R.col] = 0;

        }

    }

}
