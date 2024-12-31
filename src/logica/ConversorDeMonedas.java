package logica;

import modulos.Moneda;
import modulos.ObtenerMoneda;

public class ConversorDeMonedas {
    public double convertirMoneda(String monedaBase, String monedaObjetivo, double monto) {
        ObtenerMoneda obtenerMoneda = new ObtenerMoneda();
        Moneda moneda = obtenerMoneda.getMoneda(monedaBase, monedaObjetivo);

        try {
            return monto * moneda.tasa();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error. Número no válido: " + e.getMessage());
        }
    }
}
