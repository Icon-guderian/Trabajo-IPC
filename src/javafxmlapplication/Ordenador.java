/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Booking;

/**
 *
 * @author david
 */
public class Ordenador {
    
    
    public static void ordenarPorFechaYHora(List<Booking> b) {
        LocalDateTime ahora = LocalDateTime.now();
        Collections.sort(b, new Comparator<Booking>() {
                
                @Override
                public int compare(Booking objeto1, Booking objeto2) {
                    LocalDateTime fechaHoraObjeto1 = objeto1.getFechaHora();
                    LocalDateTime fechaHoraObjeto2 = objeto2.getFechaHora();

                    // Calcula la diferencia entre las fechas y horas de los objetos con la fecha y hora actual
                    long diferenciaEnMinutosObjeto1 = Math.abs(ahora.until(fechaHoraObjeto1, ChronoUnit.MINUTES));
                    long diferenciaEnMinutosObjeto2 = Math.abs(ahora.until(fechaHoraObjeto2, ChronoUnit.MINUTES));

                    // Compara las diferencias y devuelve el resultado
                    return Long.compare(diferenciaEnMinutosObjeto1, diferenciaEnMinutosObjeto2);
                }
            });
        }
}
