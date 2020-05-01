/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.io.File;
import java.io.FileWriter;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Itzel A
 */
public class TablaPreguntasAction {
    
    public TablaPreguntasAction() {
    }
    
    public String execute() throws Exception {
     
            HttpSession session = ServletActionContext.getRequest().getSession();
           
            try {
                //Recuperamos el archivo XML de las preguntas
                File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                
                //Hacemos una lista de las preguntas por su ID
                NodeList listaElem = doc.getElementsByTagName("id");
                
                /* Crearemos un array de archivo JSON 'preguntas.js' para guardar los IDs de las 
                preguntas de manera que puedan ser leídos por el componente TablaPreguntas.js */
                JSONArray list = new JSONArray();
                for (int i = 0; i < listaElem.getLength(); i++) {  
                    Element eElement = (Element) listaElem.item(i);
                    list.add(eElement.getAttribute("id"));                   
                }
                    JSONObject obj = new JSONObject();
                    obj.put("ids", list);
                    try{
                            FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/")+"/preguntas.json");
                            archivoJson.write(obj.toJSONString());
                            archivoJson.flush();
                            archivoJson.close();
                            
                        
                    }catch(Exception ex){
                            System.out.println(ex);
                    }
                       
            }catch (Exception e) {
              e.printStackTrace();
              System.out.println("Error al momento de leer el archivo XML");
            }
            return "OK";
            
    }
}
