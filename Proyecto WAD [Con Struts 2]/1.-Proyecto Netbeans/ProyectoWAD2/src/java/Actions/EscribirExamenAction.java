/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.io.File;
import java.io.FileInputStream;

import static java.lang.Thread.sleep;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.struts2.ServletActionContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Itzel A
 */
public class EscribirExamenAction {
    
    
    String idexamen;

    public String getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(String idexamen) {
        this.idexamen = idexamen;
    }
    
    
    
    public EscribirExamenAction() {
    }
    
    public String execute() throws Exception {
      HttpSession session = ServletActionContext.getRequest().getSession();

        try {

            File archivo = new File(ServletActionContext.getServletContext().getRealPath("/") + "/XML/Examenes.xml");
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
            preguntas = ServletActionContext.getRequest().getParameterValues("NombrePregunta");
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
            
        } catch (InterruptedException ex) {
            System.out.println("Interrumpido");
        } 
        return "OK";
    }  
}
