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
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static java.lang.Thread.sleep;
import javax.servlet.RequestDispatcher;

public class BorrarExamenAction extends HttpServlet{
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            
            HttpSession session = request.getSession();

            String examenEliminado = request.getParameter("examenEliminado");  
            try {

                File archivo = new File(this.getServletContext().getRealPath("/")+"//XML/Examenes.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new FileInputStream(archivo));
                Element raiz = doc.getDocumentElement();
                Node raizx =doc.getDocumentElement();

                NodeList listaElem = doc.getElementsByTagName("id");
                int i;
                for (i = 0; i < listaElem.getLength(); i++) {                      
                    Element eElement = (Element) listaElem.item(i);
                    if(eElement.getAttribute("id").equals(examenEliminado)){
                        System.out.println("Examen eliminado");
                        Node node = listaElem.item(i);
                        raizx.removeChild(node);
                        break;                   
                    }    
                }
				
                doc.getDocumentElement().normalize();
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.transform(new DOMSource(doc), new StreamResult(archivo)); 
				
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al momento de leer el archivo XML");
            }
            try{
            sleep(5000);
            RequestDispatcher d = request.getRequestDispatcher("ControladorPrincipal?accion=TablaExamenesAction");;
            d.forward(request, response);
            }catch (InterruptedException ex) {
                System.out.println("Interrumpido");
            }
    }
}
