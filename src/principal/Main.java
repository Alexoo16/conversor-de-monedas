package principal;

import logica.ConversorDeMonedas;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcion = obtenerOpcion();

            if (opcion == 9) {
                System.out.println("Gracias por usar el conversor de monedas. Que tenga un buen día :)");
                break;
            }

            String[] monedas = obtenerMonedasPorOpcion(opcion);
            if (monedas != null) {
                procesarConversion(monedas[0], monedas[1]);
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                =======================================
                \tSISTEMA CONVERSOR DE MONEDAS
                =======================================
                
                1) Dólar => Peso argentino
                2) Peso argentino => Dólar
                3) Dólar => Real brasileño
                4) Real brasileño => Dólar
                5) Dólar => Peso colombiano
                6) Peso colombiano => Dólar
                7) Otros
                
                9) Salir
                
                =======================================
                """);
        System.out.print("Elija una opción válida: ");
    }

    private static int obtenerOpcion() {
        try {
            int opcion = input.nextInt();
            if (opcion < 1 || (opcion > 7 && opcion != 9)) {
                throw new IllegalArgumentException("Opción inválida.");
            }
            return opcion;
        } catch (Exception e) {
            System.out.println("Error. " + e.getMessage());
            input.nextLine();
            return -1;
        }
    }

    private static String[] obtenerMonedasPorOpcion(int opcion) {
        String monedaBase, monedaObjetivo;
        switch (opcion) {
            case 1: return new String[]{"USD", "ARS"};
            case 2: return new String[]{"ARS", "USD"};
            case 3: return new String[]{"USD", "BRL"};
            case 4: return new String[]{"BRL", "USD"};
            case 5: return new String[]{"USD", "COP"};
            case 6: return new String[]{"COP", "USD"};
            case 7: return obtenerMonedasPersonalizadas();
            default: return null;
        }
    }

    private static String[] obtenerMonedasPersonalizadas() {
        System.out.print("\nDijite el código ISO de la divisa base: ");
        String monedaBase = input.next().trim().toUpperCase();
        System.out.print("\nDijite el código ISO de la divisa objetivo: ");
        String monedaObjetivo = input.next().trim().toUpperCase();
        return new String[]{monedaBase, monedaObjetivo};
    }

    private static void procesarConversion(String monedaBase, String monedaObjetivo) {
        System.out.print("\nIngrese el monto que desea convertir: ");
        try {
            double cantidad = input.nextDouble();
            double montoConvertido = new ConversorDeMonedas().convertirMoneda(monedaBase, monedaObjetivo, cantidad);
            if (cantidad <= 0) {
                throw new IllegalArgumentException("Monto inválido.");
            }
            System.out.println("\nEl valor " + cantidad + " [" + monedaBase + "] corresponde al valor final de => " + montoConvertido + " [" + monedaObjetivo + "]");
        } catch (Exception e) {
            System.out.println("Error. " + e.getMessage());
        }
    }
}
