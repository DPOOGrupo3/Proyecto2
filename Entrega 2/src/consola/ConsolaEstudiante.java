package consola;

import modelo.CentralLogica;
import modelo.LearningPath;
import modelo.usuario.Estudiante;
import java.io.IOException;
import java.util.List;

public class ConsolaEstudiante extends ConsolaBasica{
	
	
	private Estudiante estudianteA;
	private CentralLogica centralLogica;
	
	public ConsolaEstudiante(Estudiante estudianteActual, CentralLogica centralLogica) {
        this.estudianteA = estudianteActual;
        this.centralLogica = centralLogica;
    }
	
	/**
     * Método para ejecutar el menú principal del estudiante.
     */
	public void correrConsola() throws IOException {
        String[] opcionesMenu = {"Ver Learning Paths Disponibles", "Ver Progreso", "Salir"};
        int opcion = mostrarMenu("Menú Estudiante", opcionesMenu);

        switch (opcion) {
		    case 1:
		        verLearningPathsDisponibles();
		        break;
		    case 2:
		        verProgreso();
		        break;
		    case 3:
		        System.out.println("Saliendo de la consola del estudiante...");
		        break;
		    default:
		        System.out.println("Opción no válida.");
		        correrConsola();
		        break;
		}
    }

	private void verProgreso() {
		// TODO Auto-generated method stub
		
	}

	private void verLearningPathsDisponibles() {
		// TODO Auto-generated method stub
		
	}
	
	
}
