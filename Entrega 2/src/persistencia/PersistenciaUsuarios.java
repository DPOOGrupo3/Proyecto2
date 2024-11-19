package persistencia;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.LearningPath;
import modelo.usuario.Estudiante;
import modelo.usuario.Profesor;
import modelo.usuario.Usuario;

public class PersistenciaUsuarios {
	private String[] titulos = {"nombre", "correo", "contrasena", "caminosCreados", "caminosCopiados"};
	
	public void cargarArchivo(String ruta, List<Profesor> profesores, List<Estudiante> estudiantes, List<LearningPath> caminosCompletos) throws IOException {
		JSONObject usuarios = new JSONObject(new String(Files.readAllBytes(new File(ruta).toPath())));
		
		cargarProfesores(profesores, caminosCompletos, usuarios.getJSONArray("profesores"));
		cargarEstudiantes(estudiantes, usuarios.getJSONArray("estudiantes"));
	}
	
	private void cargarProfesores(List<Profesor> profesores, List<LearningPath> caminosCompletos, JSONArray jProfesores) {
		for (int i = 0; i < jProfesores.length(); i++) {
			JSONObject jProfesor = jProfesores.getJSONObject(i);
			Profesor profesor = new Profesor(jProfesor.getString(titulos[0]), jProfesor.getString(titulos[1]), jProfesor.getString(titulos[2]));
			JSONArray jCaminos = jProfesor.getJSONArray(titulos[3]);
			List<String> caminos = new ArrayList<String>();
			for (int j = 0; j < jCaminos.length(); j++) {
				caminos.add(jCaminos.getString(j));
			}
			profesor.cargarLearninPathsCreados(caminos, caminosCompletos);
			profesores.add(profesor);
		}
	}
	
	private void cargarEstudiantes(List<Estudiante> estudiantes, JSONArray jEstudiantes) {
		for (int i = 0; i < jEstudiantes.length(); i++) {
			JSONObject estudiante = jEstudiantes.getJSONObject(i);
			estudiantes.add(new Estudiante(estudiante.getString(titulos[0]), estudiante.getString(titulos[1]), estudiante.getString(titulos[2])));
		}
	}
	
	public void guardarArchivo(String ruta, List<Profesor> profesores, List<Estudiante> estudiantes) {
		JSONObject usuarios = new JSONObject();
		JSONArray jProfesores = new JSONArray();
		JSONArray jEstudiantes = new JSONArray();
		
		for (Profesor profesor: profesores) {
			JSONObject profe = new JSONObject();
			String[] atributosProfesor = profesor.toString().split("/");
			
			for (int i = 0; i < titulos.length-2; i++) {
				profe.put(titulos[i], atributosProfesor[i]);
			}
			
			JSONArray jCaminosCreados = new JSONArray();
			JSONArray jCaminosCopiados = new JSONArray();
			
			if (!atributosProfesor[3].equals("NA")) {
				String[] caminos = atributosProfesor[3].split("%");
				for (String camino: caminos) {
					jCaminosCreados.put(camino);
				}
			}
			if (!atributosProfesor[4].equals("NA")) {
				String[] caminos = atributosProfesor[4].split("%");
				for (String camino: caminos) {
					jCaminosCopiados.put(camino);
				}
			}
			profe.put(titulos[3], jCaminosCreados);
			profe.put(titulos[4], jCaminosCopiados);
			jProfesores.put(profe);
		}
		
		for (Usuario estudiante: estudiantes) {
			JSONObject student = new JSONObject();
			String[] atributosEstudiante = estudiante.toString().split("/");
			
			for (int i = 0; i < titulos.length-2; i++) {
				student.put(titulos[i], atributosEstudiante[i]);
			}
			jEstudiantes.put(student);
		}
		
		usuarios.put("profesores", jProfesores);
		usuarios.put("estudiantes", jEstudiantes);
		
		try {
			PrintWriter pw = new PrintWriter(ruta);
			usuarios.write(pw, 2, 0);
	        pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}