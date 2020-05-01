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

public class BorrarExamenAction {
     
    String examenEliminado;

    public String getExamenEliminado() {
        return examenEliminado;
    }

    public void setExamenEliminado(String examenEliminado) {
        this.examenEliminado = examenEliminado;
    }

    public BorrarExamenAction() {
    }
    
    public String execute() throws Exception {
         HttpSession session = ServletActionContext.getRequest().getSession();

            try {

                File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Examenes.xml");
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
                        System.out.println("se elimino");
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
            sleep(4000);
            }catch (InterruptedException ex) {
                System.out.println("Interrumpido");
            }
            return "OK";
    }
}
