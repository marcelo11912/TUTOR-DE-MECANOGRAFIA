package com.mycompany.tutormecanografia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AplicacionTecladoVirtual {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame marco = new JFrame("Teclado Virtual con Monitoreo");
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelTecladoVirtual panelTeclado = new PanelTecladoVirtual();
            marco.add(panelTeclado);

            marco.pack();
            marco.setVisible(true);
        });
    }
}

class PanelTecladoVirtual extends JPanel {

    public JTextField campoTexto;
    private JLabel pangramaActual;
    private List<String> pangramas;
    private Random random;
    private int correctas;
    private int incorrectas;

    public PanelTecladoVirtual() {
        setLayout(new BorderLayout());
        correctas = 0;
        incorrectas = 0;
        campoTexto = new JTextField(20);
        add(campoTexto, BorderLayout.NORTH);

        pangramaActual = new JLabel("");
        pangramaActual.setHorizontalAlignment(JLabel.CENTER);
        add(pangramaActual, BorderLayout.CENTER);

        JPanel panelTeclado = new JPanel(new GridLayout(5, 7));

        String[] etiquetasTeclas = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", " ",
            "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "0", ".", ",", "*", "!", "?", ":", "Borrar",};

        for (String etiquetaTecla : etiquetasTeclas) {
            JButton boton = new JButton(etiquetaTecla);
            boton.addActionListener(new EscuchadorBotonTecla(this));
            panelTeclado.add(boton);
        }

        add(panelTeclado, BorderLayout.SOUTH);
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/src/pangramas.txt";

        System.out.println(currentDir);

        pangramas = cargarPangramasDesdeArchivo(filePath);

        random = new Random();
        mostrarPangramaAleatorio();
    }

    public void actualizarEstadisticas(boolean esCorrecto) {
        if (esCorrecto) {
            correctas++;
        } else {
            incorrectas++;
        }
    }

    private List<String> cargarPangramasDesdeArchivo(String nombreArchivo) {
        List<String> pangramas = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                pangramas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pangramas;
    }

    public void mostrarPangramaAleatorio() {
        int indicePangramaAleatorio = random.nextInt(pangramas.size());
        pangramaActual.setText(pangramas.get(indicePangramaAleatorio));
    }

    public String getPangramaActual() {
        return pangramaActual.getText();
    }

    public String getTextoUsuario() {
        return campoTexto.getText();
    }
}

class EscuchadorBotonTecla implements ActionListener {

    private PanelTecladoVirtual panelTeclado;

    public EscuchadorBotonTecla(PanelTecladoVirtual panelTeclado) {
        this.panelTeclado = panelTeclado;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if ("Retroceso".equals(comando)) {
            // Manejo del retroceso
            String texto = panelTeclado.getTextoUsuario();
            if (!texto.isEmpty()) {
                panelTeclado.campoTexto.setText(texto.substring(0, texto.length() - 1));
            }
        } else {
            // Agregar la tecla presionada al campo de texto
            panelTeclado.campoTexto.setText(panelTeclado.campoTexto.getText() + comando);

            // Verificar la precisión del usuario
            String pangramaActual = panelTeclado.getPangramaActual();
            String textoUsuario = panelTeclado.getTextoUsuario();
            if (pangramaActual.startsWith(textoUsuario)) {
                panelTeclado.actualizarEstadisticas(true);

            } else {
                panelTeclado.actualizarEstadisticas(false);

            }
        }
    }
}
