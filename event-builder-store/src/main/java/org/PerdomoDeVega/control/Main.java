package org.PerdomoDeVega.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
        public static void main(String[] args) {
            // Crear un marco (ventana)
            JFrame frame = new JFrame("Ejemplo de Eventos");
            frame.setSize(300, 200);

            // Crear un botón
            JButton button = new JButton("Clic aquí");

            // Crear un ActionListener para manejar el evento de clic en el botón
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("¡Se hizo clic en el botón!");
                }
            };

            // Asociar el ActionListener al botón
            button.addActionListener(actionListener);

            // Agregar el botón al marco
            frame.getContentPane().add(button);

            // Hacer visible el marco
            frame.setVisible(true);
        }
    }

