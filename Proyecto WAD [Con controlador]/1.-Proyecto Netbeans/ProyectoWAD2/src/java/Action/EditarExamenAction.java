package Action;

import java.io.IOException;
import java.io.File;
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

public class EditarExamenAction extends HttpServlet {

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            HttpSession session = request.getSession();

            String idExamen = request.getParameter("examen");
           try {
                File archivo = new File(this.getServletContext().getRealPath("/")+"/XML/Examenes.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                
                File archivo2 = new File(this.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
                Document doc2 = dBuilder.parse(archivo2);
                doc2.getDocumentElement().normalize();
                
                NodeList listaElem = doc.getElementsByTagName("id");
                NodeList listaPreg = doc2.getElementsByTagName("id");
                JSONArray listCheckBox = new JSONArray();

                int i,j=0;
                Boolean comprobacion;
                for (i = 0; i < listaElem.getLength(); i++) {
                       
                    Element eElement = (Element) listaElem.item(i);
                    
                    if(eElement.getAttribute("id").equals(idExamen)){
                        
                        NodeList listaHijos = eElement.getElementsByTagName("pregunta");
                        
                        for (int x = 0; x < listaPreg.getLength(); x++) {
                            comprobacion = false;
                            Element pregunta = (Element) listaPreg.item(x);
                            for(j=0; j < listaHijos.getLength();j++){
                               Element eElementHijo = (Element) listaHijos.item(j);
                               if(eElementHijo.getAttribute("id").equals(pregunta.getAttribute("id"))){
                                  comprobacion = true;
                               }
                            }
                            if(comprobacion) listCheckBox.add(true);
                            else listCheckBox.add(false);   
                        } 
                    }                       
                }
                JSONObject obj = new JSONObject();
                obj.put("idExamen", idExamen);
                obj.put("listCheckBox", listCheckBox);
                
                try{
                    FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/")+"/edicionExamen.json");

                    archivoJson.write(obj.toJSONString());
                    archivoJson.flush();
                    archivoJson.close();
                      
                    response.sendRedirect("EditarExamen.jsp");
                    }catch(Exception ex){
                        System.out.println(ex);
                    }          
            }catch (Exception e) {
              e.printStackTrace();
              System.out.println("Error al momento de leer el archivo XML");
            }
    }
}
