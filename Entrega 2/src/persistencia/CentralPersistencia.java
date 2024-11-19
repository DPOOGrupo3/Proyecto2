package persistencia;

import java.io.IOException;
import java.util.List;

import modelo.LearningPath;
import modelo.actividad.Actividad;
import modelo.usuario.*;

public class CentralPersistencia {
	private static String RutaUsuarios = "datos/usuarios.json";
	private static String RutaCaminos = "datos/caminos.json";
	private static String RutaActividades = "datos/actividades.json";
	private PersistenciaUsuarios persistenciaU = new PersistenciaUsuarios();
	private PersistenciaLearningPaths persistenciaC = new PersistenciaLearningPaths();
	private PersistenciaActividades persistenciaA = new PersistenciaActividades();
	
	public void cargarDatos(List<Profesor> profesores, List<Estudiante> estudiantes, List<LearningPath> caminos, List<Actividad> actividades) {
		try {
			cargarActividades(actividades);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			cargarLearningPaths(caminos, actividades);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			cargarUsusarios(profesores, estudiantes, caminos);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void cargarActividades(List<Actividad> actividades) throws IOException {
		persistenciaA.cargarArchivo(RutaActividades, actividades);
	}
	
	private void cargarLearningPaths(List<LearningPath> caminos, List<Actividad> actividades) throws IOException {
		persistenciaC.cargarArchivo(RutaCaminos, caminos, actividades);
	}
	
	private void cargarUsusarios(List<Profesor> profesores, List<Estudiante> estudiantes, List<LearningPath> caminos) throws IOException {
		persistenciaU.cargarArchivo(RutaUsuarios, profesores, estudiantes, caminos);
	}
	
	public void guardarDatos(List<Profesor> profesores, List<Estudiante> estudiantes, List<LearningPath> caminos, List<Actividad> actividades) {
		try {
			guardarUsusarios(profesores, estudiantes);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			guardarLearningPaths(caminos);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			guardarActividades(actividades);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void guardarUsusarios(List<Profesor> profesores, List<Estudiante> estudiantes) throws IOException {
		persistenciaU.guardarArchivo(RutaUsuarios, profesores, estudiantes);
	}
	
	private void guardarLearningPaths(List<LearningPath> caminos) throws IOException {
		persistenciaC.guardarArchivo(RutaCaminos, caminos);
	}
	
	private void guardarActividades(List<Actividad> actividades) throws IOException {
		persistenciaA.guardarArchivo(RutaActividades, actividades);
	}
}