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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static java.lang.Thread.sleep;
import javax.servlet.RequestDispatcher;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CrearPreguntaAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Recuperamos la sesión
        HttpSession session = request.getSession();
        
        /* Recuperamos de CrearPregunta.jsp los atributos necesarios 
          para crear una nueva pregunta y guardarle en el archivo XML */        
        String idpregunta = request.getParameter("idpregunta");
        String texto = request.getParameter("texto");
        String tipo = request.getParameter("tipo");
        String link = request.getParameter("link");
	
        //Variable auxiliar para las iteraciones
        int i;
        try {
            //Recuperamos el archivo XML de las preguntas y buscamos su nodo raíz
            File archivo = new File(this.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(archivo));
            Element raiz = doc.getDocumentElement();
            Node raizx =doc.getDocumentElement();
            NodeList listaElem = doc.getElementsByTagName("id"); //Establecemos un ID para particularizar cada pregunta
            
            /* Si el ID de una pregunta ya está disponible y otra
               pregunta a crear tiene el mismo ID, la sobreescribimos */
            for (i = 0; i < listaElem.getLength(); i++) {                      
                Element eElement = (Element) listaElem.item(i);
                if(eElement.getAttribute("id").equals(idpregunta)){
                    Node node = listaElem.item(i);
                    raizx.removeChild(node);                      
                }   
            }

            /* Con los parámetros recuperamos creamos la pregunta alojándola
               en el archivo XML*/
            Element item = doc.createElement("id"); 
            item.setAttribute("id",idpregunta); //ID de la pregunta
            Element multimedia = doc.createElement("multimedia");
            multimedia.appendChild(doc.createTextNode(link));
            item.appendChild(multimedia); //Multimedia de la pregunta
            Element pregunta = doc.createElement("pregunta");
            pregunta.setAttribute("tipo", tipo); //Tipo de pregunta
            pregunta.appendChild(doc.createTextNode(texto));
            item.appendChild(pregunta);
            
            //Colocamos las opciones y respuestas de cada pregunta en el archivo XML
            for(i=1; i<=8; i++){
                String respx = request.getParameter("resp"+i);
                String respc =  request.getParameter("resp"+i+"C");
                if(!respx.equals("")){
                    Element resp = doc.createElement("respuesta");
                    resp.setAttribute("id", Integer.toString(i));
                        if(respc!=null){
                            resp.setAttribute("correcta", "true");
                        }else{
                            resp.setAttribute("correcta","false");
                        }
                    resp.appendChild(doc.createTextNode(respx));
                    item.appendChild(resp);
                }
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
            sleep(5000);
            RequestDispatcher d = request.getRequestDispatcher("ControladorPrincipal?accion=TablaPreguntasAction");;
            d.forward(request, response);
        } catch (InterruptedException ex) {
            System.out.println("Interrumpido");
        }
    }
}