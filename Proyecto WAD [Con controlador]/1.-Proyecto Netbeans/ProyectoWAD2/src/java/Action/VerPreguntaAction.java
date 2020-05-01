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
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class VerPreguntaAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Recuperamos la sesión
        HttpSession session = request.getSession();

        //Recuperamos el ID de la pregunta a ver
        String pregunta = request.getParameter("pregunta");
        String texto = "", imagen="", tipo= "";
        
        try {
            
            //Recuperamos el archivo XML de las preguntas y el nodo raíz
            File archivo = new File(this.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(archivo));
            Element raiz = doc.getDocumentElement();
            Node raizx = doc.getDocumentElement();
            NodeList listaElem = doc.getElementsByTagName("id");
			
            //Variable auxiliar para la iteración 
            int i;
            JSONArray list = new JSONArray();
			
            //Encontramos y recuperamos los datos de la pregunta a desplegar
            for (i = 0; i < listaElem.getLength(); i++) {
                Element eElement = (Element) listaElem.item(i);
                if (eElement.getAttribute("id").equals(pregunta)) {
                    texto = eElement.getElementsByTagName("pregunta").item(0).getTextContent();
                    imagen = eElement.getElementsByTagName("multimedia").item(0).getTextContent();
                    Element x = (Element) eElement.getElementsByTagName("pregunta").item(0);
                    tipo = x.getAttribute("tipo");
                    NodeList resp = eElement.getElementsByTagName("respuesta");
                    for (int j = 0; j < resp.getLength(); j++) {
                        Element e = (Element) resp.item(j);
                        list.add(e.getTextContent());
                    }
                    break;
                }
            }
			
            //Colocamos los datos recuperados en un objeto JSON
            JSONObject obj = new JSONObject();
            obj.put("id", pregunta);
            obj.put("tipo", tipo);
            obj.put("pregunta", texto);
            obj.put("resp", list);
            obj.put("multimedia", imagen);

            //Colocamos el objeto JSON creado en un archivo JSON
            try {
                FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/") + "/preguntamc.json");
                archivoJson.write(obj.toJSONString());
                archivoJson.flush();
                archivoJson.close();
				
	        response.sendRedirect("VerPregunta.jsp");
            } catch (Exception ex) {
                System.out.println(ex);
                System.out.println("Error al momento de crear el archivo JSON");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo XML. Se lee: id="+pregunta+" pregunta="+texto+" imagen="+imagen+" tipo="+tipo);
        }
    }
}