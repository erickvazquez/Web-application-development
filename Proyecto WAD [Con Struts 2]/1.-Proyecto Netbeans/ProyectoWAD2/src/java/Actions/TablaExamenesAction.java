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

public class TablaExamenesAction {
    
    public TablaExamenesAction() {
    }
    
    public String execute() throws Exception {
         HttpSession session = ServletActionContext.getRequest().getSession();

           try {
                File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Examenes.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                NodeList listaElem = doc.getElementsByTagName("id");
                JSONArray list = new JSONArray();
				
                for (int i = 0; i < listaElem.getLength(); i++) {    
                    Element eElement = (Element) listaElem.item(i);
                    list.add(eElement.getAttribute("id"));                     
                }           
				
                    JSONObject obj = new JSONObject();
                    obj.put("ids", list);
					
                    try{
                        FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/")+"/examenes.json");                           
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
