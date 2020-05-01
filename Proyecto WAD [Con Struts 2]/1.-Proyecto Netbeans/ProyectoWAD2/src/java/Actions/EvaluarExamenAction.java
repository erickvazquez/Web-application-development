package Actions;


import java.io.File;
import java.io.FileWriter;
import java.io.*;

import org.apache.struts2.ServletActionContext;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.json.simple.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class EvaluarExamenAction {
     String examen;
     String pregunta;
     String tipo;
     String texto;
     String imagen;
     String anterior;
     String siguiente;
     String output;
 

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(String siguiente) {
        this.siguiente = siguiente;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
    public EvaluarExamenAction() {
    }

    
    public String execute() throws Exception {
     
                  try {
           
            File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Examenes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new FileInputStream(archivo));
            Element raiz = doc.getDocumentElement();
            Node raizx = doc.getDocumentElement();

            NodeList listaElem = doc.getElementsByTagName("id");
            int i;
            for (i = 0; i < listaElem.getLength(); i++) {

                Element eElement = (Element) listaElem.item(i);
                if (eElement.getAttribute("id").equals(examen)) {
                    NodeList listaPreg = eElement.getElementsByTagName("pregunta");
                    
                 int errores = Integer.parseInt(ServletActionContext.getRequest().getParameter("err"));
                    
                    float promedio=((float)listaPreg.getLength()-(float)errores)*10/(float)listaPreg.getLength();
                    
                    JSONObject obj = new JSONObject();
                    obj.put("examen", examen);
                    obj.put("resultado", promedio);

                    try {


                       FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/")+"/evaluaExamen.json");

                         
                        archivoJson.write(obj.toJSONString());
                        archivoJson.flush();
                        archivoJson.close();

                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.out.println("Error al momento de crear JSON");
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo Examenes.xml");
        }
                  
         output = "OK";
         
          return output;

    }   
}
    

