package main;

import clases.Agenda;
import clases.Turno;
import clases.agendas.AgendaBalanceada;
import clases.agendas.AgendaBasica;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Programa {

    public static void main(String[] args) {

        AgendaBasica abs = new AgendaBasica("Erique", "Tutorías");
        abs.añadirTurno("09:30 - 10:00", LocalDate.of(2018, Month.DECEMBER, 12));
        abs.añadirTurno("10:30 - 11:00", LocalDate.of(2018, Month.DECEMBER, 12));
        abs.añadirTurno("10:30 - 11:00", LocalDate.of(2018, Month.DECEMBER, 13));

        AgendaBalanceada abl = new AgendaBalanceada("Enrique", "Revisión de Examen");
        abl.añadirTurno("12:00 - 12:30", LocalDate.of(2018, Month.DECEMBER, 12));
        abl.añadirTurno("13:30 - 14:00", LocalDate.of(2018, Month.DECEMBER, 12));
        abl.añadirTurno("11:00 - 11:30", LocalDate.of(2018, Month.DECEMBER, 13));
        abl.añadirTurno("12:30 - 13:00", LocalDate.of(2018, Month.DECEMBER, 13));
        abl.añadirTurno("13:00 - 13:30", LocalDate.of(2018, Month.DECEMBER, 13));

        List<Agenda> lista = new ArrayList<>();
        lista.add(abs);
        lista.add(abl);

        for (Agenda a : lista) {
            System.out.println(a.toString());
            System.out.print("Turnos para el 13 de Diciembre: ");
            System.out.println(a.getTurnosPorDia(LocalDate.of(2018, Month.DECEMBER, 13)));
            a.reservar("Juan");
            a.reservar("Juan");
            System.out.println(".:TURNOS QUE SE HAN RESERVADO");
            for (Turno t : a.getReservas().keySet()) {
                System.out.println(t.toString());
            }
            System.out.println(".:TURNOS DEL 13 DE DICIEMBRE");
            for (Turno t : a.getTurnos()) {
                if (t.getFecha().equals(LocalDate.of(2018, Month.DECEMBER, 13))) {
                    if (a.isTurnoOcupado(t)) {
                        System.out.println(t.toString());
                    }
                }
            }
            ArrayList<Turno> turnos = new ArrayList<>(a.getReservas().keySet());
            a.cancelarReserva("Juan", turnos.get(0));
            if (a instanceof AgendaBalanceada) {
                System.out.print(".:DIA CON MAYOR DISPONIBILIDAD");
                LocalDate fecha = ((AgendaBalanceada) a).getMinDiaDisponible();
                System.out.println(fecha.toString());
                AgendaBalanceada copia = (AgendaBalanceada) ((AgendaBalanceada) a).clone();
                copia.ajustarDias(7);
                System.out.println(".:TURNOS DE LA COPIA:.");
                for (Turno t : copia.getTurnos()) {
                    System.out.println(t.toString());
                }
            }
            System.out.println("\n-------------\n");
        }
    }

}
