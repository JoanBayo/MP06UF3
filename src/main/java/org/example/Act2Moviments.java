package org.example;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Act2Moviments {
    public static Connection conexio() throws SQLException {
        Connection con = null;
        String sURL = "jdbc:mariadb://localhost:3306/mp03uf6";
        con = DriverManager.getConnection(sURL, "estudiant", "Admin1234");
        return con;

    }

    public static void main(String[] args) throws SQLException {
        Scanner teclat = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Aquestes són les opcions disponibles:" +
                        "\n1- Comprar un producte" +
                        "\n2- Vendre un producte" +
                        "\n3- Calcular l' stock d'un producte i mostrar el seu preu mitja" +
                        "\n4- Calcular l' stock de tots els productes i mostrar el seu preu mitja" +
                        "\n5- Llistar els moviments d'un producte ordenats per la data del moviment" +
                        "\n6- Sortir" +
                        "\n- Que vols fer? ");
                int resposta = teclat.nextInt();

                if (resposta == 1) {
                    comprarProducte();
                }
                if (resposta == 2) {
                    vendreProducte();
                }
                if (resposta == 3) {
                    calcularStockproducte();
                }
                if (resposta == 4) {
                    calcularStocksProductes();
                }
                if (resposta == 5) {
                    llistarMovimientsProducte();
                }
                if (resposta == 6) {
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void llistarMovimientsProducte() {
        Connection con;
        Statement stmt;
        try {
            String sentenciaSQL = "SELECT * from movimentProductes order by data;";
            con = conexio();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String dataProducte = rs.getString("data");
                float preuProducte = Float.valueOf(rs.getString("preu"));
                int idMovimentProducte = Integer.parseInt(rs.getString("idMoviment"));
                float quantitatProducte = Float.valueOf(rs.getString("quantitat"));
                int idProducte = Integer.parseInt(rs.getString("idProducte"));
                String resultat = MessageFormat.format("Producte id: {0}, moviment: {1}, preu: {2} euros , data: {3}, quantitat: {4}.", idProducte, idMovimentProducte, preuProducte, dataProducte, quantitatProducte);
                System.out.println(resultat);
            }
        } catch (Exception e) {
            System.out.println("Algo a fallat, torna a provar-ho");
        }
    }

    private static void calcularStocksProductes() {
        List<String> llistaProductes = new ArrayList<>();
        Connection con;
        Statement stmt;
        String sentenciaSQL;
        ResultSet rs;

        try {
            sentenciaSQL = ("SELECT * from movimentProductes");
            con = conexio();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                int idProducte = Integer.parseInt(rs.getString("idProducte"));
                if (!llistaProductes.contains(String.valueOf(idProducte))) {
                    llistaProductes.add(String.valueOf(idProducte));
                }
            }

            for (String llistaProductes2 : llistaProductes) {
                sentenciaSQL = MessageFormat.format("SELECT * from movimentProductes where idProducte={0};", llistaProductes2);
                con = conexio();
                stmt = con.createStatement();

                rs = stmt.executeQuery(sentenciaSQL);

                float stock = 0;
                float Preumitja = 0;

                while (rs.next()) {
                    stock += Float.parseFloat(rs.getString("quantitat"));
                    float preuTotal1 = 0;
                    preuTotal1 += Float.parseFloat(rs.getString("quantitat"));
                    Preumitja += preuTotal1 / stock;


                }
                String resultat = MessageFormat.format("-> Producte: {0}, Stock: {1}, Preu Mitja: {2}€.", llistaProductes2, stock, Preumitja);
                System.out.println(resultat);
            }

        } catch (Exception e) {
            System.out.println("Algo a fallat, torna a provar-ho");
        }

    }

    private static void calcularStockproducte() {
        Scanner teclat = new Scanner(System.in);
        Connection con;
        Statement stmt;
        try {
            System.out.print("\n-ID del producte: ");
            int idProducte = teclat.nextInt();
            String sentenciaSQL = MessageFormat.format("SELECT * from movimentProductes where idProducte={0};", idProducte);
            con = conexio();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            float stock = 0;
            float preuMitja = 0;

            while (rs.next()) {
                stock += Float.parseFloat(rs.getString("quantitat"));
                float preuTotal = 0;
                preuTotal += Float.parseFloat(rs.getString("quantitat"));
                preuMitja += preuTotal / stock;
            }

            String resultat = MessageFormat.format("-> Producte: {0}, Stock: {1}, Preu Mijta: {2}€.", idProducte, stock, preuMitja);
            System.out.println(resultat);
        } catch (Exception e) {
            System.out.println("Algo a fallat, torna a provar-ho");
        }
    }

    private static void vendreProducte() {
        Scanner teclat = new Scanner(System.in);
        Connection con;
        Statement stmt;
        try {
            System.out.print("\n-ID del producte a vendre: ");
            int idProducte = teclat.nextInt();
            System.out.print("Data de la venda (DD-MM-YYYY): ");
            String dataProducte = teclat.nextLine();
            while (true) {
                try {
                    if (dataProducte.split("-")[0].length() != 4 && dataProducte.split("-")[1].length() != 2 && dataProducte.split("-")[2].length() != 2) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("La data no és correcta torna a provar-ho");
                }
            }
            System.out.print("\n-Quantitat de productes a comprar: ");
            int quantitProducte = teclat.nextInt();
            System.out.print("-Preu del producte a comprar: ");
            float preuProducte = teclat.nextFloat() * -1;
            String sentenciaSQL = MessageFormat.format("INSERT INTO movimentProductes(idProducte,data,quantitat,preu) VALUES({0},\'\'{1}\'\',{2},{3})", idProducte, dataProducte, quantitProducte, preuProducte);

            con = conexio();
            stmt = con.createStatement();
            stmt.executeUpdate(sentenciaSQL);
            System.out.println("Producte venut correctament");
        } catch (Exception e) {
            System.out.println("\nAlgo a passat comprova que totes les dades introduides són correctes");
        }
    }

    private static void comprarProducte() {
        Scanner teclat = new Scanner(System.in);
        Connection con;
        Statement stmt;
        try {
            System.out.print("\n-ID del producte a comprar: ");
            int idProducte = teclat.nextInt();
            System.out.print("Data de la compra (DD-MM-YYYY): ");
            String dataProducte = teclat.nextLine();
            while (true) {
                try {
                    if (dataProducte.split("-")[0].length() != 4 && dataProducte.split("-")[1].length() != 2 && dataProducte.split("-")[2].length() != 2) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("La data no és correcta torna a provar-ho");
                }
            }
            System.out.print("\n-Quantitat de productes a comprar: ");
            int quantitProducte = teclat.nextInt();
            System.out.print("-Preu del producte a comprar: ");
            float preuProducte = teclat.nextFloat();
            String sentenciaSQL = MessageFormat.format("INSERT INTO movimentProductes(idProducte,data,quantitat,preu) VALUES({0},\'\'{1}\'\',{2},{3})", idProducte, dataProducte, quantitProducte, preuProducte);

            con = conexio();
            stmt = con.createStatement();
            stmt.executeUpdate(sentenciaSQL);
            System.out.println("Producte comprat correctament");
        } catch (Exception e) {
            System.out.println("\nAlgo a passat comprova que totes les dades introduides són correctes");
        }
    }
}