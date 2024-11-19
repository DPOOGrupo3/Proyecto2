package modelo;

import java.util.ArrayList;
import java.util.List;

import modelo.actividad.Actividad;
import modelo.usuario.*;
import persistencia.CentralPersistencia;

public class CentralLogica {
	private List<Profesor> profesores;
	private List<Estudiante> estudiantes;
	private List<LearningPath> caminos;
	private List<Actividad> actividades;
	private CentralPersistencia centralP = new CentralPersistencia();
	private Usuario user;
	
	public CentralLogica() {
		profesores = new ArrayList<Profesor>();
		estudiantes = new ArrayList<Estudiante>();
		caminos = new ArrayList<LearningPath>();
		actividades = new ArrayList<Actividad>();
	}
	
	public void cargarDatos() {
		centralP.cargarDatos(profesores, estudiantes, caminos, actividades);
	}
	
	public void guardarDatos() {
		centralP.guardarDatos(profesores, estudiantes, caminos, actividades);
	}
	
	public Usuario iniciarSesion(String email, String contrasena) {
		for (Profesor profesor: profesores) {
			if (profesor.iniciarSesion(email, contrasena)) {
				user = profesor;
				break;
			}
		}
		for (Estudiante estudiante: estudiantes) {
			if (estudiante.iniciarSesion(email, contrasena)) {
				user = estudiante;
				break;
			}
		}
		return user;
	}
	
	
	
	public LearningPath crearLearningPath(String titulo, String descripcion, String objetivo, List<Actividad> activdades) {
		LearningPath camino = ((Profesor) user).crearLearningPath(titulo, descripcion, objetivo, activdades);
		caminos.add(camino);
		return camino;
	}
	
	public Actividad crearActividad(String descripcion, String objetivo, String tipo, int nivelDificultad, Double duracionEsperada, List<String> preRequisitos, String recurso, String tipoRecurso, List<String> ejercicios, List<String> preguntas, List<String> respuestas, double calificacionMin) {
		List<Actividad> listaPreRequisitos = new ArrayList<Actividad>();
		for (String pre: preRequisitos) {
			listaPreRequisitos.add(encontrarActividadPorID(pre));
		}
		Actividad actividad = ((Profesor) user).crearActividad(descripcion, objetivo, tipo, nivelDificultad, duracionEsperada, listaPreRequisitos, recurso, tipoRecurso, ejercicios, preguntas, respuestas, calificacionMin);
		actividades.add(actividad);
		return actividad;
	}
	
	private LearningPath encontrarLearningPathPorID(String IDCamino) {
		for (LearningPath camino: caminos) {
			if (camino.getID().equals(IDCamino)) {
				return camino;
			}
		}
		return new LearningPath("", "", "", "", new ArrayList<Actividad>());
	}
	
	private Actividad encontrarActividadPorID(String IDActividad) {
		for (Actividad actividad: actividades) {
			if (actividad.getID().equals(IDActividad)) {
				return actividad;
			}
		}
		return null;
	}
	
	private LearningPath encontrarLearningPathPorIDActividad(String IDActividad) {
		for (LearningPath camino: caminos) {
			for (Actividad actividad: camino.getActivdades()) {
				if (actividad.getID().equals(IDActividad)) {
					return camino;
				}
			}
		}
		return null;
	}
	
	public void copiarActividad(String IDActividad) {
	    Actividad actividadOriginal = encontrarActividadPorID(IDActividad);
	    if (actividadOriginal != null) {
	        Actividad actividadCopia = actividadOriginal.copy();
	        actividades.add(actividadCopia);
	    }
	}
	
	public void eliminarLearningPathCreado(String IDCamino) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		if (!camino.equals(null)) {
			((Profesor) user).eliminarLearningPathCreado(camino);
			caminos.remove(camino);
		}
	}
	
	public void cambiarTituloLearningPath(String IDCamino, String titulo) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		if (!camino.equals(null)) {
			((Profesor) user).cambiarTituloLearningPath(camino, titulo);
		}
	}
	
	public void cambiarDescrpcionLearningPath(String IDCamino, String descripcion) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		if (!camino.equals(null)) {
			((Profesor) user).cambiarDescripcionLearningPath(camino, descripcion);
		}
	}
	
	public void cambiarObjetivoLearningPath(String IDCamino, String objetivo) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		if (!camino.equals(null)) {
			((Profesor) user).cambiarObjetivoLearningPath(camino, objetivo);
		}
	}
	
	public void agregarActividadLearningPath(String IDCamino, String IDActividad) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		Actividad actividadAgregar = encontrarActividadPorID(IDActividad);
		if (!camino.equals(null) && !actividadAgregar.equals(null)) {
			((Profesor) user).agregarActividadLearningPath(camino, actividadAgregar);
		}
	}
	
	public void eliminarsActividadLearningPath(String IDCamino, String IDActividad) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		Actividad actividadEliminar = encontrarActividadPorID(IDActividad);
		if (!camino.equals(null) && !actividadEliminar.equals(null)) {
			((Profesor) user).eliminarActividadLearningPath(camino, actividadEliminar);
			actividades.remove(actividadEliminar);
		}
	}
	
	public void cambiarDescrpcionActividad(String IDActividad, String descripcion) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		((Profesor) user).cambiarDescripcionActividad(actividad, descripcion);
	}
	
	public void cambiarObjetivoActividad(String IDActividad, String objetivo) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		if (!actividad.equals(null)) {
			((Profesor) user).cambiarObjetivoActividad(actividad, objetivo);
		}
	}
	
	public void cambiarDuracionEsperadaActividad(String IDActividad, Double duracion) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		LearningPath camino = encontrarLearningPathPorIDActividad(IDActividad);
		if (!actividad.equals(null) && !camino.equals(null)) {
			((Profesor) user).cambiarDuracionEsperadaActividad(actividad, duracion, camino);
		}
	}
	
	public void cambiarNivelDificultadActividad(String IDActividad, int dificultad) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		LearningPath camino = encontrarLearningPathPorIDActividad(IDActividad);
		if (!actividad.equals(null) && !camino.equals(null)) {
			((Profesor) user).cambiarNivelDificultadActividad(actividad, dificultad, camino);
		}
	}
	
	public void agregarPreRequisitosActividad(String IDActividad, String IDActividadAgregar) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		Actividad actividadAgregar = encontrarActividadPorID(IDActividadAgregar);
		if (!actividad.equals(null) && !actividadAgregar.equals(null)) {
			((Profesor) user).agregarPreRequisitosActividad(actividad, actividadAgregar);
		}
	}
	
	public void eliminarPreRequisitosActividad(String IDActividad, String IDActividadEliminar) {
		Actividad actividad = encontrarActividadPorID(IDActividad);
		Actividad actividadEliminar = encontrarActividadPorID(IDActividadEliminar);
		if (!actividad.equals(null) && !actividadEliminar.equals(null)) {
			((Profesor) user).eliminarPreRequisitosActividad(actividad, actividadEliminar);
		}
	}
	
	public void actualizarVersionLP(String IDCamino) {
		LearningPath camino = encontrarLearningPathPorID(IDCamino);
		camino.cambiarVersion();
	}
	

	public void registrarUsuario(Usuario nuevoUsuario) {
	    if (nuevoUsuario instanceof Profesor) {
	        profesores.add((Profesor) nuevoUsuario);
	    } else if (nuevoUsuario instanceof Estudiante) {
	        estudiantes.add((Estudiante) nuevoUsuario); 
	    } else {
	        throw new IllegalArgumentException("Tipo de usuario desconocido"); 
	    }
	}

	public List<LearningPath> obtenerLearningPathsProfesor(Profesor profesor) {
	    return profesor.getCaminosCreados();
	}

	public List<Actividad> obtenerActividadesIndependientes() {
	    List<Actividad> independientes = new ArrayList<>();
	    for (Actividad actividad : actividades) {
	        if (!esActividadParteDeLearningPath(actividad)) {
	            independientes.add(actividad);
	        }
	    }
	    return independientes;
	}

	private boolean esActividadParteDeLearningPath(Actividad actividad) {
	    for (LearningPath camino : caminos) {
	        if (camino.getActivdades().contains(actividad)) {
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * Elimina una actividad del sistema.
	 * Si la actividad está asociada a algún Learning Path, también se elimina de ellos.
	 * @param idActividad El ID de la actividad a eliminar.
	 */
	public void eliminarActividad(String idActividad) {
	    Actividad actividadAEliminar = encontrarActividadPorID(idActividad);

	    if (actividadAEliminar == null) {
	        System.out.println("Error: No se encontró la actividad con el ID proporcionado.");
	        return;
	    }

	    for (LearningPath camino : caminos) {
	        if (camino.getActivdades().contains(actividadAEliminar)) {
	            camino.eliminarActivdad(actividadAEliminar);
	            System.out.println("La actividad ha sido eliminada del Learning Path: " + camino.getID());
	        }
	    }

	    actividades.remove(actividadAEliminar);
	    System.out.println("Actividad eliminada exitosamente del sistema. ID: " + idActividad);
	}


	
	
}