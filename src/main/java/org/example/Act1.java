package org.example;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Scanner;

public class Act1 {

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
                        "\n1- Crear un producte" +
                        "\n2- Modificar un producte" +
                        "\n3- Eliminar un producte" +
                        "\n4- Llistar els productes" +
                        "\n5- Mostrar un producte" +
                        "\n6- Sortir" +
                        "\n- Que vols fer? ");
                int resposta = teclat.nextInt();

                if (resposta == 1) {
                    crearProdcute();
                }
                if (resposta == 2) {
                    seleccionaProductes();
                    modificarProducte();
                }
                if (resposta == 3) {
                    seleccionaProductes();
                    eliminarProducte();
                }
                if (resposta == 4) {
                    llistaproductes();
                }
                if (resposta == 5) {
                    mostrarProdcuteEspecific();
                }
                if (resposta == 6) {
                    break;
                }
            }
        }catch (Exception ignored){
        }
    }

    private static void mostrarProdcuteEspecific() {
        try {
            Scanner teclat = new Scanner(System.in);
            System.out.print("Introdueix la ID que vols mostrar: ");
            int idProducte = teclat.nextInt();
            String sentenciaSQL = "SELECT * from productes where idProducte=" + idProducte + ";";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            System.out.println("\nAquest és el producte amb aquesta id:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void llistaproductes() {
        Scanner teclat = new Scanner(System.in);
        while(true) {
            System.out.print("Aquestes són les opcions a llistar:" +
                    "\n1- Ordenats per ID" +
                    "\n2- Ordenats per nom" +
                    "\n3- Ordenats per preu" +
                    "\n4- Que continguin una cadena de caràcters al nom" +
                    "\n5- Tornar al menu inicial" +
                    "\n- Que vols fer? ");

            int resposta = teclat.nextInt();
            if (resposta == 1) {
                llistarPerId();
            }
            if (resposta == 2) {
                llistarPerNom();
            }
            if (resposta == 3) {
                llistarPerPreu();
            }
            if (resposta == 4) {
                llistaPerContingut();
            }
            if (resposta == 5) {
                break;
            }
        }
    }

    private static void llistaPerContingut() {
        try {
            Scanner teclat = new Scanner(System.in);
            System.out.println("Que a de incloure el producte (zz = Pizza");
            String contingut = teclat.next();
            String sentenciaSQL = "SELECT * from productes where nom like '%" + contingut + "%';";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            System.out.println("\nAixí queda la llista ordenada per la ID:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void llistarPerPreu() {
        try {
            String ascDesc = "asc";
            Scanner teclat = new Scanner(System.in);
            System.out.print("Vols ordenar de forma ascendent o descendent?" +
                    "\n1 -Ascendent" +
                    "\n2- Descendent" +
                    "\nSelecciona una opció: ");
            int ordre = teclat.nextInt();
            if (ordre == 2) {
                ascDesc = "desc";
            }
            System.out.println("Aquesta resposta no és possible, serà ordenat de forma ascendent\n");
            String sentenciaSQL = "SELECT * from productes ORDER BY preu " + ascDesc  +";";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            System.out.println("\nAixí queda la llista ordenada per la ID:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void llistarPerNom() {
        try {
            String ascDesc = "asc";
            Scanner teclat = new Scanner(System.in);
            System.out.print("Vols ordenar de forma ascendent o descendent?" +
                    "\n1 -Ascendent" +
                    "\n2- Descendent" +
                    "\nSelecciona una opció: ");
            int ordre = teclat.nextInt();
            if (ordre == 2) {
                ascDesc = "desc";
            }
            System.out.println("Aquesta resposta no és possible, serà ordenat de forma ascendent\n");
            String sentenciaSQL = "SELECT * from productes ORDER BY nom " + ascDesc  +";";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            System.out.println("\nAixí queda la llista ordenada per la ID:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void llistarPerId() {
        try {
            String ascDesc = "asc";
            Scanner teclat = new Scanner(System.in);
            System.out.print("Vols ordenar de forma ascendent o descendent?" +
                    "\n1 -Ascendent" +
                    "\n2- Descendent" +
                    "\nSelecciona una opció: ");
            int ordre = teclat.nextInt();
            if (ordre == 2) {
                ascDesc = "desc";
            }
            System.out.println("Aquesta resposta no és possible, serà ordenat de forma ascendent\n");
            String sentenciaSQL = "SELECT * from productes ORDER BY idProducte " + ascDesc  +";";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            System.out.println("\nAixí queda la llista ordenada per la ID:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void eliminarProducte() {
        try {
            Scanner teclat = new Scanner(System.in);
            Connection con = null;
            Statement stmt = null;
            System.out.print("Introdueix la ID del producte a eliminar: ");
            int id = teclat.nextInt();
            String sentenciaSQL = "DELETE FROM productes WHERE idProducte=" + id;

                con = conexio();
                stmt = con.createStatement();
                stmt.executeUpdate(sentenciaSQL);
            if (stmt != null){
                System.out.println("\n-> Aquesta ID, no es correcta\n");
            }else {
                System.out.println("\n-> Producte eliminat correctament\n");
            }
        }catch (Exception e){
            System.out.println("\n-> Algo a fallat, torna a provar-ho\n");
        }
    }

    private static void modificarProducte() {
        Scanner teclat = new Scanner(System.in);
        Connection con = null;
        Statement stmt = null;
        try {

        System.out.print("Introdueix la ID del producte a modificar: ");
        int id = teclat.nextInt();
        System.out.println();
        System.out.println("Introdueix els valors del nou producte:");
        System.out.print("-Nom: ");
        String nom = teclat.next();
        System.out.print("-Preu: ");
        int preu = teclat.nextInt();
        String sentenciaSQL = "UPDATE productes" +
                " SET nom = '" + nom +"',"+
                " preu = " + preu +
                " WHERE idProducte = " + id;
            con = conexio();
            stmt = con.createStatement();
            stmt.executeUpdate(sentenciaSQL);
            System.out.println("\n-> Producte modificat correctament\n");
        }catch (Exception e){
            System.out.println("\n-> Algo a fallat, torna a provar-ho\n");
        }
    }

    private static void crearProdcute() {
        Scanner teclat = new Scanner(System.in);
        try {

            Connection con = null;
            Statement stmt = null;
            System.out.println("Introduiex els valors del nou producte:");
            System.out.print("-Nom: ");
            String nom = teclat.nextLine();
            System.out.print("-Preu: ");
            int preu = teclat.nextInt();
            String sentenciaSQL = "INSERT INTO productes(nom,preu)" +
                    " VALUES('" + nom + "'," + preu + ")";
                con = conexio();
                stmt = con.createStatement();
                stmt.executeUpdate(sentenciaSQL);
                System.out.println("\n-> Producte afegit a la llista correctament\n");
        }catch (Exception e){
            System.out.println("\n-> Algo a fallat, torna a provar-ho\n");
        }
    }

    public static void seleccionaProductes() throws SQLException{
        try {
            String sentenciaSQL = "SELECT * from productes;";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            System.out.println("Aquest són els productes que es troben actualment a la llista:\n");
            while(rs.next()){
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("-> {0}- Nom: {1}, Preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }
            System.out.println();

            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            System.out.println("\n-> Algo a fallat, torna a provar-ho\n");
        }
    }
}