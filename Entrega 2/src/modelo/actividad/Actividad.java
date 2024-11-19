package modelo.actividad;

import java.util.ArrayList;
import java.util.List;

public abstract class Actividad {
	private String ID;
	private String descripcion;
	private String objetivo;
	private String tipo;
	private int nivelDificultad;
	private Double duracionEsperada;
	private List<Actividad> preRequisitos;
	private String resultado;
	
	public Actividad(String descripcion, String objetivo, String tipo, int nivelDificultad, Double duracionEsperada, List<Actividad> preRequisitos) {
		this.ID = "";
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.tipo = tipo;
		this.nivelDificultad = nivelDificultad;
		this.duracionEsperada = duracionEsperada;
		this.preRequisitos = preRequisitos;
		resultado = "Incompleto";
	}

	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void cambiarDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void cambiarObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void cambiarTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getNivelDificultad() {
		return nivelDificultad;
	}

	public void cambiarDuracionEsperada(Double duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}
	
	public double getDuracionEsperada() {
		return duracionEsperada;
	}

	public void cambiarNivelDificultad(int nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}
	
	public List<Actividad> getPreRequisitos() {
		return preRequisitos;
	}

	public void agregarPreRequisito(Actividad preRequisito) {
		preRequisitos.add(preRequisito);
	}
	
	public void eliminarPreRequisito(Actividad preRequisito) {
		preRequisitos.remove(preRequisito);
	}
	
	public String getResultado() {
		return resultado;
	}
	
	public void cambiarResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public Actividad copy() {
	    
	    List<Actividad> copiaPreRequisitos = new ArrayList<>();
	    for (Actividad preRequisito : preRequisitos) {
	        copiaPreRequisitos.add(preRequisito.copy()); 
	    }

	    
	    Actividad clon = new Actividad(this.descripcion, this.objetivo, this.tipo, this.nivelDificultad, this.duracionEsperada, copiaPreRequisitos) {
	        @Override
	        public Actividad copy() {
	            return null;
	        }

	        @Override
	        public void editarContenido(Object cambio) {}

	        @Override
	        public Object obtenerInformacion() {
	            return null;
	        }
	    };
	    clon.setID(generarNuevoID());
	    clon.cambiarResultado(this.resultado);
	    
	    return clon;
	}
	
	private String generarNuevoID() {
	    return "ID_" + System.currentTimeMillis(); // Ejemplo simple usando la marca de tiempo para un ID único
	}
	
	public abstract void editarContenido(Object cambio);
	
	public abstract Object obtenerInformacion();
	
	@Override
	public String toString() {
		String cadenaActividades = "";
		if (preRequisitos.size() > 0) {
			for (Actividad actividad: preRequisitos) {
				cadenaActividades += actividad.getID() + "//";
			}
		}else {
			cadenaActividades = "NA";
		}
		return ID + "%" + descripcion + "%" + objetivo + "%" + tipo + "%" + nivelDificultad + "%" + duracionEsperada + "%" + cadenaActividades + "%";
	}
	
	
	
	
}