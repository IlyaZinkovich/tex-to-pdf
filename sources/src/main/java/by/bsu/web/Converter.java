package by.bsu.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Converter {

	private static Task task = null;

	public static void main(String[] args) throws FileNotFoundException {
        parseFile(new File(args[0]));
        String output = convertFile();
        File resultFile = new File("output.tex");
        try(PrintWriter pw = new PrintWriter(resultFile)) {
            pw.print(output);
            pw.flush();
            pw.close();
        }
    }

    private static boolean parseFile(File file) {
        try {
            Scanner s = new Scanner(file);
            Graph graph;
            String str;
            if (!s.hasNextLine()) {
                s.close();
                return false;
            }
            str = s.nextLine();
            if (!str.startsWith("/*|I|*/")) {
                s.close();
                return false;
            }
            String str1 = str.substring("/*|I|*/".length());
            int I = Integer.parseInt(str1);
            if (!s.hasNextLine()) {
                s.close();
                return false;
            }
            str = s.nextLine();
            if (!str.startsWith("/*|U|*/")) {
                s.close();
                return false;
            }
            str1 = str.substring("/*|U|*/".length());
            int U = Integer.parseInt(str1);
            graph = new Graph(I, U);
            for (int i = 0; i < U; i++) {
                if (!s.hasNextLine()) {
                    s.close();
                    return false;
                }
                str = s.nextLine();
                if (!str.startsWith("{")) {
                    s.close();
                    return false;
                }
                str1 = str.substring("{".length(), str.indexOf(","));
                int begin = Integer.parseInt(str1);
                str1 = str.substring(str.indexOf(",") + 1, str.indexOf("}"));
                int end = Integer.parseInt(str1);
                graph.addEdge(begin, end);
            }
            if (!s.hasNextLine()) {
                s.close();
                return false;
            }
            str = s.nextLine();
            if (!str.startsWith("/*|K|*/")) {
                s.close();
                return false;
            }
            str1 = str.substring("/*|K|*/".length());
            int K = Integer.parseInt(str1);
            graph.setK(K);
            for (int i = 1; i <= K; i++) {
                if (!s.hasNextLine()) {
                    s.close();
                    return false;
                }
                str = s.nextLine();
                if (!str.equals("/*widetilde{U}^" + i + "*/")) {
                    s.close();
                    return false;
                }
                for (int j = 0; j < graph.getU(); j++) {
                    if (!s.hasNextLine()) {
                        s.close();
                        return false;
                    }
                    str = s.nextLine();
                    if (!str.startsWith("/*{")) {
                        s.close();
                        return false;
                    }
                    str1 = str.substring("/*{".length(), str.indexOf(","));
                    int begin = Integer.parseInt(str1);
                    str1 = str.substring(str.indexOf(",") + 1, str.indexOf("}*/"));
                    int end = Integer.parseInt(str1);
                    str1 = str.substring(str.indexOf("}*/") + 3);
                    int including = Integer.parseInt(str1);
                    if (including == 1) {
                        graph.setEdge(new Graph.Edge(begin, end), i);
                    }
                }
            }
            task = new Task(graph);
            if (!s.hasNextLine()){
                s.close();
                return false;
            }
            str = s.nextLine();

            for (int i = 1; i <= graph.getK(); i++) {
                if (str.equals("/*widetilde{U}_1^" + i + "*/")) {
                    for (int j = 0; j < graph.getU(); j++) {
                        if (!s.hasNextLine()) {
                            s.close();
                            return false;
                        }
                        str = s.nextLine();
                        if (!str.startsWith("/*{")) {
                            s.close();
                            return false;
                        }
                        str1 = str.substring("/*{".length(), str.indexOf(","));
                        int begin = Integer.parseInt(str1);
                        str1 = str.substring(str.indexOf(",") + 1, str.indexOf("}*/"));
                        int end = Integer.parseInt(str1);
                        str1 = str.substring(str.indexOf("}*/") + 3);
                        int including = Integer.parseInt(str1);
                        if (including == 1) {
                            task.setD1Including(i, graph.getEdgeIndex(begin, end));
                        }
                    }
                    if (!s.hasNextLine()) {
                        s.close();
                        return false;
                    }
                    str = s.nextLine();
                }
            }

            if (str.equals("/*widetilde{U}_0*/")) {
                int count = 0;
                for (int j = 0; j < graph.getU(); j++) {
                    if (!s.hasNextLine()) {
                        s.close();
                        return false;
                    }
                    str = s.nextLine();
                    if (!str.startsWith("/*{")) {
                        s.close();
                        return false;
                    }
                    str1 = str.substring("/*{".length(), str.indexOf(","));
                    int begin = Integer.parseInt(str1);
                    str1 = str.substring(str.indexOf(",") + 1, str.indexOf("}*/"));
                    int end = Integer.parseInt(str1);
                    str1 = str.substring(str.indexOf("}*/") + 3);
                    int including = Integer.parseInt(str1);
                    if (including == 1) {
                        task.setD0Including(graph.getEdgeIndex(begin, end));
                        count++;
                    }
                }

                if (!s.hasNextLine()) {
                    s.close();
                    return false;
                }
                str = s.nextLine();
                for (int i = 1; i <= graph.getK(); i++) {
                    if (str.equals("/*widetilde{U}_0^" + i + "*/")) {
                        for (int j = 0; j < count; j++) {
                            if (!s.hasNextLine()) {
                                s.close();
                                return false;
                            }
                            str = s.nextLine();
                            if (!str.startsWith("/*{")) {
                                s.close();
                                return false;
                            }
                            str1 = str.substring("/*{".length(), str.indexOf(","));
                            int begin = Integer.parseInt(str1);
                            str1 = str.substring(str.indexOf(",") + 1, str.indexOf("}*/"));
                            int end = Integer.parseInt(str1);
                            str1 = str.substring(str.indexOf("}*/") + 3);
                            int including = Integer.parseInt(str1);
                            if (including == 0) {
                                task.removeD0Including(i, graph.getEdgeIndex(begin, end));
                            }
                        }
                        if (!s.hasNextLine()) {
                            s.close();
                            return false;
                        }
                        str = s.nextLine();
                    }
                }
            }

            if (!str.startsWith("/*q*/")) {
                s.close();
                return false;
            }

            str1 = str.substring("/*q*/".length());
            int q = Integer.parseInt(str1);

            task.setQ(q);

            while (s.hasNextLine() && (str = s.nextLine()).startsWith("/*lambda_")) {
                String s1 = str.substring("/*lambda_".length(), str.indexOf("^"));
                String s2 = str.substring(str.indexOf("^") + 1, str.indexOf("*/"));
                String s3 = str.substring(str.indexOf("*/") + 2);
                int begin = Integer.parseInt(s1.substring(0, 1));
                int end = Integer.parseInt(s1.substring(1));
                int k = Integer.parseInt(s2.substring(0, 1));
                int qValue = Integer.parseInt(s2.substring(1));
                int value = Integer.parseInt(s3);
                task.setLambda(qValue, k, graph.getEdgeIndex(begin, end), value);
            }

            while (str.startsWith("/*d_")) {
                String s1 = str.substring("/*d_".length(), str.indexOf("^"));
                String s2 = str.substring(str.indexOf("^") + 1, str.indexOf("*/"));
                String s3 = str.substring(str.indexOf("*/") + 2);
                int begin = Integer.parseInt(s1.substring(0, 1));
                int end = Integer.parseInt(s1.substring(1));
                int index = Integer.parseInt(s2);
                int value = Integer.parseInt(s3);
                if (index == 0) {
                    task.setD0Value(graph.getEdgeIndex(begin, end), value);
                } else {
                    task.setD1Value(index, graph.getEdgeIndex(begin, end), value);
                }
                if (s.hasNextLine()) {
                    str = s.nextLine();
                } else {
                    s.close();
                    return false;
                }
            }

            while (str.startsWith("/*a_")) {
                String s1 = str.substring("/*a_".length(), str.indexOf("^"));
                String s2 = str.substring(str.indexOf("^") + 1, str.indexOf("*/"));
                String s3 = str.substring(str.indexOf("*/") + 2);
                int i = Integer.parseInt(s1);
                int k = Integer.parseInt(s2);
                int value = Integer.parseInt(s3);
                task.setA(i, k, value);
                if (s.hasNextLine()) {
                    str = s.nextLine();
                } else {
                    s.close();
                    return false;
                }
            }

            for (int i = 0; i < q; i++) {
                if (!str.startsWith("/*alpha_")) {
                    s.close();
                    return false;
                }
                String s1 = str.substring("/*alpha_".length(), str.indexOf("*/"));
                String s2 = str.substring(str.indexOf("*/") + 2);
                int qValue = Integer.parseInt(s1);
                int value = Integer.parseInt(s2);
                task.setAlpha(qValue, value);
                if (s.hasNextLine()) {
                    str = s.nextLine();
                } else {
                    if (i == q - 1) {
                        s.close();
                        return true;
                    } else {
                        s.close();
                        return false;
                    }
                }
            }
            if (!s.hasNextLine()) {
                s.close();
                return true;
            } else {
                s.close();
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private static String convertFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("\\documentclass{article}\n"
                + "\\usepackage{amssymb,amsmath}\n"
                + "\\usepackage[english,russian]{babel}\n"
                + "\\usepackage{graphicx}\n");
        sb.append("\\begin{document}\n"
                + "\\begin{center}\n"
                + "\\bigskip\n");
        int I = task.getGraph().getI();
        int U = task.getGraph().getU();
        int K = task.getGraph().getK();
        int q = task.getQ();
        int[][] nets = task.getGraph().getNets();
        for (int k = 1; k <= K; k++) {
            for (int i = 1; i <= I; i++) {
                StringBuilder sb1 = new StringBuilder();
                boolean isFirst = true;
                StringBuilder sb2 = new StringBuilder();
                for (int j = 0; j < U; j++) {
                    if (nets[k - 1][j] == 1) {
                        Graph.Edge edge = task.getGraph().getEdgeByIndex(j);
                        if(edge.getBegin() == i) {
                            if (!isFirst) {
                                sb1.append("+");
                            }
                            sb1.append("x_{"+ i + edge.getEnd() + "}^{" + k + "}");
                            isFirst = false;
                        }
                        if(edge.getEnd() == i) {
                            sb2.append("-x_{"+ edge.getBegin() + i + "}^{" + k + "}");
                        }
                    }
                }
                if (sb1.toString().equals("") && sb2.toString().equals("")) {
                    continue;
                } else {
                    sb1.append(sb2.toString() + "=" + task.getA(i, k));
                    sb.append("$" + sb1.toString() + "$\\\\\n");
                }
            }
            sb.append("\\bigskip\n");
        }

        for (int qValue = 1; qValue <= q; qValue++) {
            StringBuilder sb1 = new StringBuilder();
            boolean isFirst = true;
            for (int i = 0; i < U; i++) {
                for (int k = 1; k <= K; k++) {
                    if (nets[k - 1][i] == 1) {
                        Graph.Edge edge = task.getGraph().getEdgeByIndex(i);
                        int lambda = task.getLambda(qValue, k, i);
                        if (lambda != 0) {
                            if (!isFirst) {
                                sb1.append("+");
                            }
                            if (lambda == 1) {
                                sb1.append("x_{" + edge.getBegin() + edge.getEnd() + "}^{" + k + "}");
                            } else {
                                sb1.append(lambda + "x_{" + edge.getBegin() + edge.getEnd() + "}^{" + k + "}");
                            }
                            isFirst = false;
                        }
                    }
                }
            }
            sb1.append("=" + task.getAlpha(qValue));
            sb.append("$" + sb1.toString() + "$\\\\\n");
            sb.append("\\bigskip\n");
        }

        for (int i = 0; i < U; i++) {
            boolean isFirst = true;
            StringBuilder sb1 = new StringBuilder();
            for (int k = 1; k <= K; k++) {
                if (nets[k - 1][i] == 1 && task.getD0Including(k, i) == 1) {
                    Graph.Edge edge = task.getGraph().getEdgeByIndex(i);
                    if (!isFirst) {
                        sb1.append("+");
                    }
                    sb1.append("x_{" + edge.getBegin() + edge.getEnd() + "}^{" + k + "}");
                    isFirst = false;
                }
            }
            int d0Value = task.getD0Value(i);
            if (!sb1.toString().equals("")) {
                sb1.append("=" + d0Value);
                sb.append("$" + sb1.toString() + "$\\\\\n");
            }
        }
        sb.append("\\bigskip\n");

        boolean isVisited = false;
        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < U; i++) {
                if (nets[k - 1][i] == 1 && task.getD1Including(k, i) == 1) {
                    Graph.Edge edge = task.getGraph().getEdgeByIndex(i);
                    String str1 = "0\\leq x_{" + edge.getBegin() + edge.getEnd() + "}^{" + k + "}\\leq" + task.getD1Value(k, i);
                    sb.append("$" + str1 + "$\\\\\n");
                    isVisited = true;
                }
            }
        }
        if (isVisited) {
            sb.append("\\bigskip\n");
        }
        sb.append("\\includegraphics{graph}\\\\\n"
                + "\\bigskip\n");
        sb.append("\\end{center}\n"
                + "\\end{document}");
        return sb.toString();
    }
}
