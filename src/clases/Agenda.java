package clases;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Agenda implements Cloneable {

    
    protected Set<Turno> turnos;
    protected Map<Turno, String> reservas;
    protected String propietario;
    protected String descripcion;
 

    protected Agenda(String propietario, String descripcion) {
        this.propietario = propietario;
        this.descripcion = descripcion;
        this.turnos = new HashSet<>();
        this.reservas = new HashMap<>();
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Turno> getTurnos() {
        return new HashSet<>(this.turnos);
    }
    
    public Map<Turno, String> getReservas() {
        return new HashMap<>(this.reservas);
    }

    public boolean añadirTurno(String franja, LocalDate fecha) {
        Turno tr = new Turno(franja, fecha);
        return turnos.add(tr);
    }

    public void ajustarDias(int dias) {
        if (dias >= 0) {
            for (Turno t : turnos) {
                LocalDate fecha = t.getFecha().plusDays(dias);
                t.setFecha(fecha);
            }
        } else {
            for (Turno t : turnos) {
                t.getFecha().minusDays(dias);
            }
        }
    }

    public Set<Turno> getTurnosPorDia(LocalDate fecha) {
        Set<Turno> conjunto = new HashSet<>();
        for (Turno t : turnos) {
            if (t.getFecha().equals(fecha)) {
                conjunto.add(t);
            }
        }

        return conjunto;
    }

    public boolean reservar(String usuario, Turno turno) {
        if (turnos.contains(turno)) {
            if(!isTurnoOcupado(turno)) {
                reservas.put(turno, usuario);
                return true;
            }
        }

        return false;
    }

    public abstract Turno getTurnoLibre();
    public Turno reservar(String usuario) {
        Turno t = getTurnoLibre();
        if (t != null) {
            reservar(usuario, t);
            return t;
        }

        return null;
    }

   public String getUsuarioPorTurno(Turno t) {
        if (!reservas.isEmpty()) {
            return reservas.get(t);
        }

        return null;
    }
    
    public boolean isTurnoOcupado(Turno t) {
        return (getUsuarioPorTurno(t) != null);
    }
    
    public boolean cancelarReserva(String usuario, Turno t) {
        if(reservas.containsKey(t)) {
            String str = reservas.get(t);
            if(str.equals(usuario)) {
                reservas.remove(t);
                return true;
            }
        }
        
        return false;
    }

    @Override
    public Agenda clone() {
        try {
            Agenda a = (Agenda) super.clone();
            a.turnos = new HashSet<>(a.turnos);
            a.reservas = new HashMap<>();
            return a;
        } catch(CloneNotSupportedException e) {
            System.err.println("ERROR EN LA CLONACION: " + e.getMessage());
        }
        
        return null;
    }
}
