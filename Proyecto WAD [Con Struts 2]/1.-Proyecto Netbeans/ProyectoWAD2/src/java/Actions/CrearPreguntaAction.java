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

public class CrearPreguntaAction {
    
    String idpregunta;
    String texto;
    String tipo ;
    String link ;

    public String getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(String idpregunta) {
        this.idpregunta = idpregunta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CrearPreguntaAction() {
    }
    
    public String execute() throws Exception {

        HttpSession session = ServletActionContext.getRequest().getSession();     
       
        int i;

        try {

            File arch = new File(ServletActionContext.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(arch));
            Element raiz = doc.getDocumentElement();
            Node raizx = doc.getDocumentElement();
            NodeList listaElem = doc.getElementsByTagName("id");

            for (i = 0; i < listaElem.getLength(); i++) {
                Element eElement = (Element) listaElem.item(i);
                if (eElement.getAttribute("id").equals(idpregunta)) {
                    Node node = listaElem.item(i);
                    raizx.removeChild(node);                      
                }
            }

            Element item = doc.createElement("id");
            item.setAttribute("id", idpregunta);
            Element multimedia = doc.createElement("multimedia");
            multimedia.appendChild(doc.createTextNode(link));
            item.appendChild(multimedia);
            Element pregunta = doc.createElement("pregunta");
            pregunta.setAttribute("tipo", tipo);
            pregunta.appendChild(doc.createTextNode(texto));
            item.appendChild(pregunta);

                for (i = 1; i <= 8; i++) {
                    String respx = ServletActionContext.getRequest().getParameter("resp" + i);
                    String respc = ServletActionContext.getRequest().getParameter("resp" + i + "C");
                    if (!respx.equals("")) {
                        Element resp = doc.createElement("respuesta");
                        resp.setAttribute("id", Integer.toString(i));
                        if (respc != null) {
                            resp.setAttribute("correcta", "true");
                        } else {
                            resp.setAttribute("correcta", "false");
                        }
                        resp.appendChild(doc.createTextNode(respx));
                        item.appendChild(resp);
                    }
                }
            
            raiz.appendChild(item);

            doc.getDocumentElement().normalize();
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(arch));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo XML");
        }
        try {
            sleep(4000);
            
        } catch (InterruptedException ex) {
            System.out.println("Interrumpido");
        }
           return "OK";
    }

}

    
