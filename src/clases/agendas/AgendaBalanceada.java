
package clases.agendas;

import clases.Agenda;
import clases.Turno;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class AgendaBalanceada extends Agenda {

    private Map<LocalDate, Integer> balanceo;

    public AgendaBalanceada(String propietario, String descripcion) {
        super(propietario, descripcion);
        this.balanceo = new HashMap<>();
    }

    @Override
    public boolean añadirTurno(String franja, LocalDate fecha) {
        if (!balanceo.containsKey(fecha)) {
            balanceo.put(fecha, 1);
        } else {
            int cont = balanceo.get(fecha);
            cont = cont + 1;
            balanceo.replace(fecha, cont);
        }
        return super.añadirTurno(franja, fecha);
    }

    @Override
    public boolean reservar(String usuario, Turno turno) {
        LocalDate fecha = turno.getFecha();
        if (balanceo.containsKey(fecha)) {
            int cont = balanceo.get(fecha);
            cont = cont - 1;
            balanceo.replace(fecha, cont);
        }
        return super.reservar(usuario, turno);
    }

    @Override
    public boolean cancelarReserva(String usuario, Turno t) {
        LocalDate fecha = t.getFecha();
        if (!balanceo.containsKey(fecha)) {
            balanceo.put(fecha, 1);
        } else {
            int cont = balanceo.get(fecha);
            cont = cont + 1;
            balanceo.replace(fecha, cont);
        }
        return super.cancelarReserva(usuario, t);
    }

    public LocalDate getMinDiaDisponible() {
        int min = Integer.MAX_VALUE;
        LocalDate seleccion = null;
        for (LocalDate ld : balanceo.keySet()) {
            int n = balanceo.get(ld);
            if (n < min) {
                min = n;
                seleccion = ld;
            }
        }

        return seleccion;
    }

    @Override
    public Turno getTurnoLibre() {
        LocalDate fecha = getMinDiaDisponible();
        for(Turno t : this.turnos) {
            if(t.getFecha().equals(fecha)) {
                return t;
            }
        }
        
        return null;
    }

    @Override
    public AgendaBalanceada clone() {
        AgendaBalanceada a = (AgendaBalanceada) super.clone();
        a.balanceo = new HashMap<>(a.balanceo);
        return a;
    }
}
