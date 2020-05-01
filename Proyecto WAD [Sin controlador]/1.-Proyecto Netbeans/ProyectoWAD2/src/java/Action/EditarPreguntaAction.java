package Action;

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

public class EditarPreguntaAction extends HttpServlet {
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();

            // Recibimos los parámetros de la pregunta a editar
            String idPregunta = request.getParameter("pregunta");
            String multimedia = "";
            String tipo = "";
            String textoPregunta = "";
            String idRespuesta = "";
            String textoRespuesta = "";
            Boolean valorBooleano = false;
			
            try {
                //Recuperamos los datos de la pregunta del archivo XML
                File archivo = new File(this.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                
                //Creamos una lista para un archivo JSON
                NodeList listaElem = doc.getElementsByTagName("id");
                JSONArray listTextoRespuesta = new JSONArray();
                JSONArray listValorBooleano = new JSONArray();
                
                //Variables auxiliares para iteración
                int i,j=0;
                
                /* Iteramos el archivo XML para encontrar los atributos de la pregunta a editar */
                for (i = 0; i < listaElem.getLength(); i++) {
                       
                    Element eElement = (Element) listaElem.item(i);
                    
                    //Buscamos los atributos de la pregunta a editar en el archivo XML
                    if(eElement.getAttribute("id").equals(idPregunta)){
                        Element elementoPregunta = (Element) eElement.getElementsByTagName("pregunta").item(0);
                        multimedia = eElement.getElementsByTagName("multimedia").item(0).getTextContent();
                        textoPregunta = eElement.getElementsByTagName("pregunta").item(0).getTextContent();
                        tipo = elementoPregunta.getAttribute("tipo");
                        NodeList listaHijos = eElement.getElementsByTagName("respuesta");
                        for(j=0; j < listaHijos.getLength();j++){
                            Element eElementHijo = (Element) listaHijos.item(j);
                            idRespuesta = eElementHijo.getAttribute("id");
                            textoRespuesta = eElementHijo.getTextContent();
                            valorBooleano =  Boolean.parseBoolean( eElementHijo.getAttribute("correcta"));
                            listTextoRespuesta.add(eElementHijo.getTextContent());
                            listValorBooleano.add(valorBooleano);
                        }
                        
                        //Colocamos las respuestas y sus valores en un archivo JSON
                        for(j=j; j < 8;j++){
                            listTextoRespuesta.add("");
                            listValorBooleano.add(false);
                        }
                    }                       
                }
                
                /* Colocamos en un archivo JSON los datos para que puedan 
                   ser leídos por el componente EditarPregunta.js */
                String cantidadRespuestas = Integer.toString(j);
                JSONObject obj = new JSONObject();
                obj.put("idPregunta", idPregunta);
                obj.put("textoPregunta", textoPregunta);
                obj.put("textoRespuesta", listTextoRespuesta);
                obj.put("valorBooleano", listValorBooleano);
                obj.put("cantidadRespuestas",cantidadRespuestas);
                
                    try{
                        FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/")+"/edicionPregunta.json");

                        //Guardamos el archivo JSON con los datos de la pregunta
                        archivoJson.write(obj.toJSONString());
                        archivoJson.flush();
                        archivoJson.close();
					
                        //Redireccionamos a EditarPreguntas.jsp
			response.sendRedirect("EditarPregunta.jsp");
                    }catch(Exception ex){
                        System.out.println(ex);
                    }          
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al momento de leer el archivo XML");
            }
    }
}