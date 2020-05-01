package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Itzel A
 */
public class VerPreguntaAction {
    String pregunta;
    String texto, tipo, output, imagen ;
    
    
    
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
    
    public VerPreguntaAction() {
    }
    
    public String execute() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
             
        try {
			
            //Recuperamos el archivo XML de las preguntas y el nodo raíz
            File archivo = new File(ServletActionContext.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
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
                FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/") + "/preguntamc.json");
                archivoJson.write(obj.toJSONString());
                archivoJson.flush();
                archivoJson.close();
               
            } catch (Exception ex) {
                System.out.println(ex);
                System.out.println("Error al momento de crear el archivo JSON");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo XML. Se lee: id="+pregunta+" pregunta="+texto+" imagen="+imagen+" tipo="+tipo);
        }
        
        output = "OK";
        
        return output;
    }
}
