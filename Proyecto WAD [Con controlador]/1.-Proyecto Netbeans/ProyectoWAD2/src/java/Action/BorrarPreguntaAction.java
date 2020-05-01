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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import static java.lang.Thread.sleep;
import javax.servlet.RequestDispatcher;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class BorrarPreguntaAction extends HttpServlet {
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");

            //Recuperamos la sesión
            HttpSession session = request.getSession();

            //Recuperamos el ID de la pregunta a eliminar
            String preguntaEliminada = request.getParameter("preguntaEliminada");  
            try {
                //Recuperamos el archivo XML de las preguntas y buscamos su nodo raíz
                File archivo = new File(this.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new FileInputStream(archivo));
                Element raiz = doc.getDocumentElement();
                Node raizx =doc.getDocumentElement();
                NodeList listaElem = doc.getElementsByTagName("id");
                
                //Iteramos la lista de IDs para encontrar el ID de la pregunta a borrar
                int i;
                for (i = 0; i < listaElem.getLength(); i++) {                    
                    Element eElement = (Element) listaElem.item(i);
                    if(eElement.getAttribute("id").equals(preguntaEliminada)){
                        System.out.println("se elimino");
                
                        Node node = listaElem.item(i);
                        
                        //Removemos la pregunta del archivo XML
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
            RequestDispatcher d = request.getRequestDispatcher("ControladorPrincipal?accion=TablaPreguntasAction");;
            d.forward(request, response);
            }catch (InterruptedException ex) {
                System.out.println("Interrumpido");
            }
            
    }
}