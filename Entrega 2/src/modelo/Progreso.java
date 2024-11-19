package modelo;

import java.util.List;
import java.util.ArrayList;

import modelo.LearningPath;
import modelo.actividad.Actividad;

public class Progreso {
	private Double porcentaje;
	private LearningPath camino;
	private List<Actividad> actividadesTerminadas;
	
	public Progreso(LearningPath camino) {
		porcentaje = 0.0;
		this.camino = camino;
	}
	
	public void agregarActividad(Actividad actividad) {
		actividadesTerminadas.add(actividad);
		recalcularProgreso();
	}
	
	public void recalcularProgreso() {
		int cantTerminadas = actividadesTerminadas.size();
		int cantTotal = camino.getCantidadActivdades();
		porcentaje = (double) (cantTerminadas/cantTotal);
	}
}