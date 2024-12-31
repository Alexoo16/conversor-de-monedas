package modulos;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ObtenerMoneda {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    public Moneda getMoneda(String base, String objetivo) {
        String apiKey = obtenerApiKey();

        try {
            HttpResponse<String> response = enviarSolicitudApi(base, apiKey);
            Map<String, Object> apiResponse = parsearRespuestaApi(response);

            double tasa = obtenerTasaDeConversion(apiResponse, objetivo);
            Moneda moneda = new Moneda(base, objetivo, tasa);

            new GeneradorArchivoJSON().guardarJSON(moneda);

            return moneda;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la moneda: " + e.getMessage(), e);
        }
    }

    private String obtenerApiKey() {
        String apiKey = System.getenv("API_KEY_ConversorDeMonedas");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API Key no encontrada.");
        }
        return apiKey;
    }

    private HttpResponse<String> enviarSolicitudApi(String base, String apiKey) throws Exception {
        URI direccion = URI.create(String.format(API_URL, apiKey, base));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private Map<String, Object> parsearRespuestaApi(HttpResponse<String> response) {
        Gson gson = new Gson();
        return gson.fromJson(response.body(), Map.class);
    }

    private double obtenerTasaDeConversion(Map<String, Object> apiResponse, String objetivo) {
        Map<String, Double> tasas = (Map<String, Double>) apiResponse.get("conversion_rates");
        if (!tasas.containsKey(objetivo)) {
            throw new RuntimeException("Tasa de conversión no disponible.");
        }
        return tasas.get(objetivo);
    }
}
