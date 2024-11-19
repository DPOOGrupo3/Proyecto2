package modelo.actividad.examen;

import java.util.List;

import modelo.actividad.Actividad;

public abstract class Examen extends Actividad {
	private List<String> preguntas; //lista de preguntas
	
	public Examen(String descripcion, String objetivo, String tipo, int nivelDificultad, Double duracionEsperada, List<Actividad> preRequisitos, List<String> preguntas) {
		super(descripcion, objetivo, tipo, nivelDificultad, duracionEsperada, preRequisitos);
		this.preguntas = preguntas;
	}
	
	public List<String> getPreguntas() {
		return preguntas;
	}
	
	@Override
	public void editarContenido(Object preguntas) {
		if (preguntas.getClass() == List.class) {
			this.preguntas = (List<String>) preguntas;
		}
	}
	
	@Override
	public List<String> obtenerInformacion() {
		return getPreguntas();
	}
	
	@Override
	public String toString() {
		String cadena = "";
		if (preguntas.size() > 0) {
			for (String pregunta: preguntas) {
				cadena += pregunta + "//";
			}
			cadena.substring(0, cadena.length()-1);
		}else {
			cadena = "NA";
		}
		return super.toString() + cadena;
	}
}