package test;

import modelo.LearningPath;
import modelo.actividad.Actividad;
import modelo.usuario.Profesor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorTest {

    private Profesor profesor;
    private List<LearningPath> learningPaths;

    @BeforeEach
    void setUp() {
        profesor = new Profesor("Juan Pérez", "juan.perez@mail.com", "password123");
        learningPaths = new ArrayList<>();
    }

    @Test
    void testCrearLearningPath() {
        
        String titulo = "Introducción a la Programación";
        String descripcion = "Un camino para aprender programación desde cero.";
        String objetivo = "Dominar conceptos básicos.";
        List<Actividad> actividades = new ArrayList<>();

        
        LearningPath learningPath = profesor.crearLearningPath(titulo, descripcion, objetivo, actividades);

        
        assertNotNull(learningPath, "El Learning Path creado no debe ser nulo.");
        assertEquals(titulo, learningPath.getTitulo(), "El título del Learning Path no es correcto.");
        assertEquals(descripcion, learningPath.getDescripcion(), "La descripción del Learning Path no es correcta.");
        assertEquals(objetivo, learningPath.getObjetivo(), "El objetivo del Learning Path no es correcto.");
        assertTrue(profesor.getCaminosCreados().contains(learningPath), "El Learning Path no se añadió a la lista del profesor.");
    }

    @Test
    void testEliminarLearningPath() {
        
        LearningPath path1 = profesor.crearLearningPath("Camino 1", "Descripción 1", "Objetivo 1", new ArrayList<>());
        LearningPath path2 = profesor.crearLearningPath("Camino 2", "Descripción 2", "Objetivo 2", new ArrayList<>());
        
        
        assertEquals(2, profesor.getCaminosCreados().size(), "El profesor debe tener 2 Learning Paths creados.");

        
        profesor.eliminarLearningPathCreado(path1);

        
        assertEquals(1, profesor.getCaminosCreados().size(), "El profesor debe tener 1 Learning Path después de la eliminación.");
        assertFalse(profesor.getCaminosCreados().contains(path1), "El Learning Path eliminado no debe estar en la lista.");
    }

    @Test
    void testCambiarTituloLearningPath() {
        
        LearningPath path = profesor.crearLearningPath("Camino Inicial", "Descripción Inicial", "Objetivo Inicial", new ArrayList<>());

        
        profesor.cambiarTituloLearningPath(path, "Nuevo Título");

        
        assertEquals("Nuevo Título", path.getTitulo(), "El título del Learning Path no se actualizó correctamente.");
    }

    @Test
    void testCambiarDescripcionLearningPath() {
        
        LearningPath path = profesor.crearLearningPath("Camino Inicial", "Descripción Inicial", "Objetivo Inicial", new ArrayList<>());

        
        profesor.cambiarDescripcionLearningPath(path, "Nueva Descripción");

        
        assertEquals("Nueva Descripción", path.getDescripcion(), "La descripción del Learning Path no se actualizó correctamente.");
    }

    @Test
    void testCambiarObjetivoLearningPath() {
       
        LearningPath path = profesor.crearLearningPath("Camino Inicial", "Descripción Inicial", "Objetivo Inicial", new ArrayList<>());

        
        profesor.cambiarObjetivoLearningPath(path, "Nuevo Objetivo");

        
        assertEquals("Nuevo Objetivo", path.getObjetivo(), "El objetivo del Learning Path no se actualizó correctamente.");
    }

    @Test
    void testAgregarActividadLearningPath() {
        
        LearningPath path = profesor.crearLearningPath("Camino Inicial", "Descripción Inicial", "Objetivo Inicial", new ArrayList<>());
        Actividad actividad = new Actividad("Descripción Actividad", "Objetivo Actividad", "T", 2, 60.0, new ArrayList<>()) {
            @Override
            public Actividad copy() {
                return this;
            }

            @Override
            public void editarContenido(Object cambio) {
            }

            @Override
            public Object obtenerInformacion() {
                return null;
            }
        };

        
        profesor.agregarActividadLearningPath(path, actividad);

        
        assertTrue(path.getActivdades().contains(actividad), "La actividad no se añadió al Learning Path.");
    }

    @Test
    void testEliminarActividadLearningPath() {
        
        LearningPath path = profesor.crearLearningPath("Camino Inicial", "Descripción Inicial", "Objetivo Inicial", new ArrayList<>());
        Actividad actividad = new Actividad("Descripción Actividad", "Objetivo Actividad", "T", 2, 60.0, new ArrayList<>()) {
            @Override
            public Actividad copy() {
                return this;
            }

            @Override
            public void editarContenido(Object cambio) {
            }

            @Override
            public Object obtenerInformacion() {
                return null;
            }
        };

        path.agregarActivdad(actividad);

        
        profesor.eliminarActividadLearningPath(path, actividad);

        
        assertFalse(path.getActivdades().contains(actividad), "La actividad no se eliminó del Learning Path.");
    }
}
