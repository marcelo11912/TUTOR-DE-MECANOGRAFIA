
package com.mycompany.tutormecanografia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pangramas {
    public List<String> cargarPangramasDesdeArchivo(String nombreArchivo) {
        List<String> pangramas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                pangramas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pangramas;
    }
    
}
