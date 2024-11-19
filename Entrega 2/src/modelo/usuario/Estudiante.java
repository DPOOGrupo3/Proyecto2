package modelo.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.LearningPath;
import modelo.Progreso;
import modelo.actividad.Actividad;

public class Estudiante extends Usuario{
	private List<LearningPath> caminosInscritos;
	
	private Map<String, List<String>> actividadesPendientes;
	
	private Map<String, Progreso> progresos;
	
	public Estudiante(String nombre, String email, String contraseña) {
		super(nombre, email, contraseña);
		this.progresos = new HashMap<String, Progreso>();
	}
	
	
	public void inscribirCamino(LearningPath camino) {
		caminosInscritos.add(camino);
		Progreso newProgreso = new Progreso(this, camino);
		progresos.put(camino.getID(), newProgreso);
		ArrayList<String> actividades = new ArrayList<>();
		for (Actividad a : camino.getActivdades()) {
			// if (actividad no es obligatoria) { no agregar la actividad a la lista de actividades pendientes}
			actividades.add(a.getID());
		}
		actividadesPendientes.put(camino.getID(), actividades);
	}
	
	public List<String> getActividadesPendientes(String LearningPathID){
		return this.actividadesPendientes.get(LearningPathID);
	}
	
	public Progreso getProgresoLP(String LearningPathID) {
		Progreso progreso = this.progresos.get(LearningPathID);
		return progreso;
	}
	
	public void hacerQuiz(LearningPath lp, String quizID) {
		
		this.actividadesPendientes.get(lp.getID()).remove(quizID);
		this.progresos.get(lp.getID()).avance(quizID);
	}
	
	public void subirTarea(LearningPath lp, String tareaID) {
		this.actividadesPendientes.get(lp.getID()).remove(tareaID);
		this.progresos.get(lp.getID()).avance(tareaID);
	}
	
	public void subirParcial(LearningPath lp, String ParcialID) {
		this.actividadesPendientes.get(lp.getID()).remove(ParcialID);
		this.progresos.get(lp.getID()).avance(ParcialID);
	}
	
	public void revisarRecurso(LearningPath lp, String recursoID) {
		this.actividadesPendientes.get(lp.getID()).remove(recursoID);
		this.progresos.get(lp.getID()).avance(recursoID);
	}
	
	public void realizarEncuesta() {
		this.actividadesPendientes.get(lp.getID()).remove(quizID);
		this.progresos.get(lp.getID()).avance(quizID);
	}
	
    @Override
    public String toString() {
        return super.toString() + " - Estudiante";
    }

	
}