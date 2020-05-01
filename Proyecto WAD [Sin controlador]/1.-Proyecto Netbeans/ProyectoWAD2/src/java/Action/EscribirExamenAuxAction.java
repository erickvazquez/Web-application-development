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

public class EscribirExamenAuxAction extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();

           try {
                File archivo = new File(this.getServletContext().getRealPath("/")+"//XML/Preguntas.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                
                NodeList listaElem = doc.getElementsByTagName("id");
                JSONArray list = new JSONArray();
                for (int i = 0; i < listaElem.getLength(); i++) {
                       
                    Element eElement = (Element)listaElem.item(i);
                    list.add(eElement.getAttribute("id"));                     
                }
                    JSONObject obj = new JSONObject();
                    obj.put("ids", list);

                    try{
                            FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/")+"/preguntas.json");

                            archivoJson.write(obj.toJSONString());
                            archivoJson.flush();
                            archivoJson.close();
                            
                            response.sendRedirect("CrearExamen.jsp");
                    }catch(Exception ex){
                            System.out.println(ex);
                    }
            }catch (Exception e) {
              e.printStackTrace();
              System.out.println("Error al momento de leer el archivo XML");
            }
    }
}
