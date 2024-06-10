package com.politecnicomalaga.entidadbancaria;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Cuenta cuenta = inicializarCuenta();
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("A. Dar datos de la cuenta.");
            System.out.println("B. Cargar Cliente de la cuenta desde fichero JSON.");
            System.out.println("C. Realizar ingreso efectivo.");
            System.out.println("D. Realizar retirada efectivo.");
            System.out.println("E. Grabar cuenta a fichero JSON.");
            System.out.println("F. Cargar cuenta desde fichero JSON.");
            System.out.println("G. Exportar datos a Texto (toString) hacia pantalla y fichero.");
            System.out.println("H. Salir.");

            System.out.print("Ingrese su opción: ");
            String opcion = scanner.nextLine().toUpperCase();

            switch (opcion) {
                case "A":
                    cuenta = inicializarCuenta();
                    break;
                case "B":
                    cargarClienteDesdeJSON(cuenta);
                    break;
                case "C":
                    realizarIngresoEfectivo(cuenta);
                    break;
                case "D":
                    realizarRetiradaEfectivo(cuenta);
                    break;
                case "E":
                    grabarCuentaAFicheroJSON(cuenta);
                    break;
                case "F":
                    cuenta = cargarCuentaDesdeFicheroJSON();
                    break;
                case "G":
                    exportarDatosTexto(cuenta);
                    break;
                case "H":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static Cuenta inicializarCuenta() {
        System.out.println("\nInicializando cuenta...");
        System.out.print("Ingrese el código de la cuenta: ");
        String ccc = scanner.nextLine();
        System.out.print("Ingrese el saldo de la cuenta: ");
        float saldo = Float.parseFloat(scanner.nextLine());
        System.out.print("Ingrese la fecha de apertura de la cuenta: ");
        String fechaApertura = scanner.nextLine();
        return new Cuenta(ccc, saldo, fechaApertura);
    }

    private static void cargarClienteDesdeJSON(Cuenta cuenta) {
        System.out.print("Ingrese la ruta del archivo JSON del cliente: ");
        String rutaArchivo = scanner.nextLine();
        try {
            Cliente cliente = objectMapper.readValue(new File(rutaArchivo), Cliente.class);
            cuenta.setClienteCuenta(cliente);
            System.out.println("Cliente cargado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el cliente desde el archivo JSON: " + e.getMessage());
        }
    }

    private static void realizarIngresoEfectivo(Cuenta cuenta) {
        System.out.print("Ingrese la cantidad a ingresar: ");
        float cantidad = Float.parseFloat(scanner.nextLine());
        System.out.print("Ingrese la fecha de la operación: ");
        String fechaOperacion = scanner.nextLine();
        cuenta.ingresarEfectivo(cantidad, fechaOperacion);
        System.out.println("Ingreso de efectivo realizado correctamente.");
    }

    private static void realizarRetiradaEfectivo(Cuenta cuenta) {
        System.out.print("Ingrese la cantidad a retirar: ");
        float cantidad = Float.parseFloat(scanner.nextLine());
        System.out.print("Ingrese la fecha de la operación: ");
        String fechaOperacion = scanner.nextLine();
        cuenta.retirarEfectivo(cantidad, fechaOperacion);
        System.out.println("Retirada de efectivo realizada correctamente.");
    }

    private static void grabarCuentaAFicheroJSON(Cuenta cuenta) {
        System.out.print("Ingrese la ruta del archivo JSON para guardar la cuenta: ");
        String rutaArchivo = scanner.nextLine();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(rutaArchivo), cuenta);
            System.out.println("Cuenta guardada exitosamente en el archivo JSON.");
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta en el archivo JSON: " + e.getMessage());
        }
    }

    private static Cuenta cargarCuentaDesdeFicheroJSON() {
        System.out.print("Ingrese la ruta del archivo JSON de la cuenta: ");
        String rutaArchivo = scanner.nextLine();
        try {
            return objectMapper.readValue(new File(rutaArchivo), Cuenta.class);
        } catch (IOException e) {
            System.out.println("Error al cargar la cuenta desde el archivo JSON: " + e.getMessage());
            return null;
        }
    }

    private static void exportarDatosTexto(Cuenta cuenta) {
        System.out.println("\nDatos de la cuenta:");
        System.out.println(cuenta.toString());
        System.out.print("Ingrese la ruta del archivo de texto para exportar los datos: ");
        String rutaArchivo = scanner.nextLine();
        try {
            objectMapper.writeValue(new File(rutaArchivo), cuenta.toString());
            System.out.println("Datos exportados exitosamente a archivo de texto.");
        } catch (IOException e) {
            System.out.println("Error al exportar los datos a archivo de texto: " + e.getMessage());
        }
    }
}
