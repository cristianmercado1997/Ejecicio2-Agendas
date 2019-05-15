
package clases.agendas;

import clases.Agenda;
import clases.Turno;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class AgendaBasica extends Agenda {

    private Set<String> usuarios;

    public AgendaBasica(String propietario, String descripcion) {
        super(propietario, descripcion);
        this.usuarios = new HashSet<>();
    }

    @Override
    public boolean reservar(String usuario, Turno t) {
        if (!this.usuarios.contains(usuario)) {
            this.usuarios.add(usuario);
            return super.reservar(usuario, t);
        }

        return false;
    }

    @Override
    public Turno getTurnoLibre() {
        LocalDate fechaActual = LocalDate.now();
        Turno tr = null;
        for (Turno t : this.turnos) {
            if (!isTurnoOcupado(t)) {
                LocalDate fecha = t.getFecha();
                if (fecha.isBefore(fechaActual)) {
                    fechaActual = fecha;
                    tr = t;
                }
            }
        }

        return tr;
    }

    @Override
    public AgendaBasica clone() {
        AgendaBasica a = (AgendaBasica) super.clone();
        a.usuarios = new HashSet<>(a.usuarios);
        return a;
    }
}
