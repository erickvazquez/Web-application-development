
package Actions;

import java.io.File;
import java.io.FileInputStream;
import static java.lang.Thread.sleep;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BorrarPreguntaAction {
   
     String preguntaEliminada;

    public String getPreguntaEliminada() {
        return preguntaEliminada;
    }

    public void setPreguntaEliminada(String preguntaEliminada) {
        this.preguntaEliminada = preguntaEliminada;
    }
     
    public BorrarPreguntaAction() {
    }
    
    public String execute() throws Exception {
        //Recuperamos la sesión
       
            HttpSession session = ServletActionContext.getRequest().getSession();
            
         
            try {
                //Recuperamos el archivo XML de las preguntas y buscamos su nodo raíz
                File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
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
            sleep(4000);
            }catch (InterruptedException ex) {
                System.out.println("Interrumpido");
            }
            return "OK";
    
    }
    
}
