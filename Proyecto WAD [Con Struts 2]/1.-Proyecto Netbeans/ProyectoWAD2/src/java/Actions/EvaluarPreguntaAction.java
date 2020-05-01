package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EvaluarPreguntaAction {
    
    String examen;
    String pregunta;
    String texto, imagen , tipo , anterior , siguiente ;
    String output;
  

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
        
    
    public EvaluarPreguntaAction() {
    }
    
    public String execute() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();

        try {

            File archivo = new File(ServletActionContext.getServletContext().getRealPath("/") + "/XML/Examenes.xml");
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
                    try {
                        File archivop = new File(ServletActionContext.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
                        DocumentBuilderFactory dbFactoryp = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilderp = dbFactoryp.newDocumentBuilder();
                        Document docp = dBuilderp.parse(new FileInputStream(archivop));
                        Element raizp = docp.getDocumentElement();
                        Node raizxp = docp.getDocumentElement();
                       
                        NodeList listaElemp = docp.getElementsByTagName("id");
                        int j;
                        JSONArray list = new JSONArray();
                        JSONArray listcorr = new JSONArray();
                        for (int l = 0; l < listaPreg.getLength(); l++) {
                            Element preg = (Element) listaPreg.item(l);
                            if (pregunta == null) {
                                pregunta = preg.getAttribute("id");
                            }
                            
                            anterior = pregunta;
                            siguiente = pregunta;
                            
                            if (pregunta.equals(preg.getAttribute("id"))) {
                                Element p = (Element)listaPreg.item(l);
                                if(l>0){
                                    p = (Element) listaPreg.item(l-1);
                                    anterior= p.getAttribute("id");
                                }
                                if(l<listaPreg.getLength()-1){
                                    p = (Element) listaPreg.item(l+1);
                                    siguiente= p.getAttribute("id");
                                }
								else{
                                    siguiente= "Evaluacion del Examen";
                                }
                                for (j = 0; j < listaElemp.getLength(); j++) {
                                    Element eElementp = (Element) listaElemp.item(j);
                                    if (eElementp.getAttribute("id").equals(preg.getAttribute("id"))) {
                                        texto = eElementp.getElementsByTagName("pregunta").item(0).getTextContent();
                                        imagen = eElementp.getElementsByTagName("multimedia").item(0).getTextContent();
                                        Element x = (Element) eElementp.getElementsByTagName("pregunta").item(0);
                                        tipo = x.getAttribute("tipo");
                                        NodeList resp = eElementp.getElementsByTagName("respuesta");
                                        for (int k = 0; k < resp.getLength(); k++) {
                                            Element e = (Element) resp.item(k);
                                            list.add(e.getTextContent());
                                            listcorr.add(e.getAttribute("correcta"));
                                        }
                                        break;
                                    }
                                }
                                
                                 int errores = Integer.parseInt(ServletActionContext.getRequest().getParameter("err"));
                                JSONObject obj = new JSONObject();
                                obj.put("id", pregunta);
                                obj.put("tipo", tipo);
                                obj.put("pregunta", texto);
                                obj.put("resp", list);
                                obj.put("corr", listcorr);
                                obj.put("multimedia", imagen);
                                obj.put("anterior", anterior);
                                obj.put("siguiente", siguiente);
                                obj.put("examen", examen);
	             obj.put("errores", errores);

                                try {
                                    FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/") + "/preguntamc.json");
                                    archivoJson.write(obj.toJSONString());
                                    archivoJson.flush();
                                    archivoJson.close();
              
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                    System.out.println("ERROR AL MOMENTO DE CREAR JSON");
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("ERROR AL MOMENTO DE LEER EL ARCHIVO PreguntasXML. Se lee: id=" + pregunta + " pregunta=" + texto + " imagen=" + imagen + " tipo=" + tipo);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL MOMENTO DE LEER EL ARCHIVO ExamenesXML");
        }

        output= "OK";

         return output;
}
    
}
