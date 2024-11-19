package persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modelo.LearningPath;
import modelo.actividad.Actividad;

public class PersistenciaLearningPaths {
	private String[] titulos = {"ID", "titulo", "descripcion", "objetivo", "duracion", "dificultad", "rating", "raters", "actividades", "fechaCreacion", "fechaModificacion", "version"};
	
	public void cargarArchivo(String ruta, List<LearningPath> caminos, List<Actividad> actividadesCompleta) throws JSONException, IOException {
		JSONArray jCaminos = new JSONArray(new String(Files.readAllBytes(new File(ruta).toPath())));
		
		for (int i = 0; i < jCaminos.length(); i++) {
			JSONObject jCamino = jCaminos.getJSONObject(i);
			JSONArray jActividades = jCamino.getJSONArray("actividades");
			List<Actividad> actividades = obtenerActividades(jActividades, actividadesCompleta);
			LearningPath camino = new LearningPath(jCamino.getString(titulos[0]), jCamino.getString(titulos[1]), jCamino.getString(titulos[2]), jCamino.getString(titulos[3]), actividades);
			cargarDatos(camino, jCamino.getInt(titulos[7]), jCamino.getDouble(titulos[6]), jCamino.getInt(titulos[11]), jCamino.getString(titulos[9]), jCamino.getString(titulos[10]));
			caminos.add(camino);
		}
	}
	
	private List<Actividad> obtenerActividades(JSONArray jActividades, List<Actividad> actividadesCompleta) {
		System.out.println(jActividades);
		List<Actividad> actividades = new ArrayList<Actividad>();
		for (int i = 0; i < jActividades.length(); i++) {
			for (int j = 0; j < actividadesCompleta.size(); j++) {
				System.out.println(String.valueOf(actividadesCompleta.get(j).getID()));
				System.out.println(jActividades.getString(i));
				if (String.valueOf(actividadesCompleta.get(j).getID()).equals(jActividades.getString(i))) {
					actividades.add(actividadesCompleta.get(j));
					break;
				}
			}
		}
		return actividades;
	}
	
	private void cargarDatos(LearningPath camino, int raters, double rating, int version, String fechaCreacion, String fechaModificacion) {
		camino.cambiarDuracionEsperada();
		camino.cambiarNivelDificultad();
		for (int i = 0; i < raters; i++) {
			camino.ratePath(rating);
		}
		camino.setVersion(version);
		camino.setFechaCreacion(obtenerFecha(fechaCreacion));
		camino.setFechaModificacion(obtenerFecha(fechaModificacion));
	}
	
	private Date obtenerFecha(String fecha) {
		String[] arrayFecha = fecha.split(" ");
		int mes = 0;
		String[] meses = {"Jan", "Feb", "Mar", "Apr", "May",
	            "Jun", "Jul", "Agu", "Sep", "Oct", "Noc", "Dec"};
		for (int i = 0; i < meses.length; i++) {
			if (arrayFecha[1].equals(meses[i])) {
				mes = i;
			}
		}
		String[] hora = arrayFecha[3].split(":");
		return new Date(Integer.parseInt(arrayFecha[5]) - 1900, mes, Integer.parseInt(arrayFecha[2]), Integer.parseInt(hora[0]), Integer.parseInt(hora[1]), Integer.parseInt(hora[2]));
	}
	
	public void guardarArchivo(String ruta, List<LearningPath> caminos) {
		JSONArray jCaminos = new JSONArray();
		
		for (LearningPath camino: caminos) {
			JSONObject jCamino = new JSONObject();
			String[] caminoArray = camino.toString().split("/");
			
			for (int i = 0; i < titulos.length; i++) {
				if (i != 8) {
					jCamino.put(titulos[i], caminoArray[i]);
				}
			}
			
			JSONArray jActividades = new JSONArray();
			
			if (!caminoArray[8].equals("NA")) {
				String[] actividades = caminoArray[8].split("%");
				
				for (String actividad: actividades) {
					jActividades.put(actividad);
				}
			}
			
			jCamino.put(titulos[8], jActividades);
			jCaminos.put(jCamino);
		}
		try {
			PrintWriter pw = new PrintWriter(ruta);
			jCaminos.write(pw, 2, 0);
	        pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}