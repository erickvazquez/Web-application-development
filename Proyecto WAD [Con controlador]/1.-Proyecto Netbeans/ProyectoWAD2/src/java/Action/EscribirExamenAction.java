package Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class EscribirExamenAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
;
        String idexamen = request.getParameter("idexamen");
        
        try {

            File archivo = new File(this.getServletContext().getRealPath("/") + "//XML/Examenes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(archivo));
            Element raiz = doc.getDocumentElement();
           
            Node raizx =doc.getDocumentElement();
            NodeList listaElem = doc.getElementsByTagName("id");
            
            for (int i = 0; i < listaElem.getLength(); i++) {                      
                Element eElement = (Element) listaElem.item(i);
                if(eElement.getAttribute("id").equals(idexamen)){
                    Node node = listaElem.item(i);
                    raizx.removeChild(node);                    
                }                    
            }
            Element item = doc.createElement("id");
            item.setAttribute("id",idexamen);
            String[] preguntas;
            preguntas = request.getParameterValues("NombrePregunta");
             for(int i=0; i<preguntas.length; i++){
                Element pregunta = doc.createElement("pregunta");
                pregunta.setAttribute("id", preguntas[i]);
               
                item.appendChild(pregunta);
            }
            raiz.appendChild(item);
            
            doc.getDocumentElement().normalize();
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(archivo));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo XML");
        }
        try {
            sleep(4500);
            RequestDispatcher d = request.getRequestDispatcher("ControladorPrincipal?accion=TablaExamenesAction");;
            d.forward(request, response);
        } catch (InterruptedException ex) {
            System.out.println("Interrumpido");
        }
    }
}
