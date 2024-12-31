package modulos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GeneradorArchivoJSON {
    private static final String ARCHIVO_JSON = "conversion.json";

    private final Gson gson;

    public GeneradorArchivoJSON() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarJSON(Moneda moneda) throws IOException {
        List<Moneda> historial = cargarHistorialExistente();
        historial.add(moneda);
        guardarHistorial(historial);
    }

    private List<Moneda> cargarHistorialExistente() {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(archivo)) {
            List<Moneda> historialExistente = gson.fromJson(reader, new TypeToken<List<Moneda>>(){}.getType());
            return historialExistente != null ? historialExistente : new ArrayList<>();
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Error al leer el archivo JSON. Creando un nuevo historial...");
            return new ArrayList<>();
        }
    }

    private void guardarHistorial(List<Moneda> historial) throws IOException {
        try (FileWriter escritura = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(historial, escritura);
        }
    }
}
