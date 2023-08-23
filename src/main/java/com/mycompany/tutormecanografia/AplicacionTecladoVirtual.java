
package com.mycompany.tutormecanografia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplicacionTecladoVirtual {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame marco = new JFrame("Teclado Virtual");
            marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            PanelTecladoVirtual panelTeclado = new PanelTecladoVirtual();
            marco.add(panelTeclado);

            marco.pack();
            marco.setVisible(true);
        });
    }
}

class PanelTecladoVirtual extends JPanel {
    public PanelTecladoVirtual() {
        setLayout(new BorderLayout());

        JTextField campoTexto = new JTextField(20);
        add(campoTexto, BorderLayout.NORTH);

        JPanel panelTeclado = new JPanel(new GridLayout(4, 7));

       String[] etiquetasTeclas = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z",
            "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "0",".", ",", "*","!", "?", ":", "Borrar"
        };
        for (String etiquetaTecla : etiquetasTeclas) {
            JButton boton = new JButton(etiquetaTecla);
            boton.addActionListener(new EscuchadorBotonTecla(campoTexto));
            panelTeclado.add(boton);
        }

        add(panelTeclado, BorderLayout.CENTER);
    }
}

class EscuchadorBotonTecla implements ActionListener {
    private JTextField campoTexto;

    public EscuchadorBotonTecla(JTextField campoTexto) {
        this.campoTexto = campoTexto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if ("Borrar".equals(comando)) {
            // Manejo del retroceso
            String texto = campoTexto.getText();
            if (!texto.isEmpty()) {
                campoTexto.setText(texto.substring(0, texto.length() - 1));
            }
        } else {
            // Agregar la tecla presionada al campo de texto
            campoTexto.setText(campoTexto.getText() + comando);
        }
    }
}
