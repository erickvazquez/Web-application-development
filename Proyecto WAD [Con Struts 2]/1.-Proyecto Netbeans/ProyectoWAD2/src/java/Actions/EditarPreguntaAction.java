package Actions;

import java.io.File;
import java.io.FileWriter;

import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EditarPreguntaAction {
    
    String pregunta;
    String multimedia;
    String tipo;
    String textoPregunta;
    String idRespuesta;
    String textoRespuesta;
    Boolean valorBooleano;
    String output;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public String getTextoRespuesta() {
        return textoRespuesta;
    }

    public void setTextoRespuesta(String textoRespuesta) {
        this.textoRespuesta = textoRespuesta;
    }

    public Boolean getValorBooleano() {
        return valorBooleano;
    }

    public void setValorBooleano(Boolean valorBooleano) {
        this.valorBooleano = valorBooleano;
    }
         
    public EditarPreguntaAction() {
    }
    
    public String execute() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();

           try {
                //Recuperamos los datos de la pregunta del archivo XML
                File archivo = new File(ServletActionContext.getServletContext().getRealPath("/")+"/XML/Preguntas.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(archivo);
                doc.getDocumentElement().normalize();
                
				//Creamos una lista para un archivo JSON
                NodeList listaElem = doc.getElementsByTagName("id");
                JSONArray listTextoRespuesta = new JSONArray();
                JSONArray listValorBooleano = new JSONArray();
				
				//Variables auxiliares para iteración
                int i,j=0;
				
				/* Iteramos el archivo XML para encontrar los atributos de la pregunta a editar */
                for (i = 0; i < listaElem.getLength(); i++) {
                       
                    Element eElement = (Element) listaElem.item(i);
                    
					//Buscamos los atributos de la pregunta a editar en el archivo XML
                    if(eElement.getAttribute("id").equals(pregunta)){
                        Element elementoPregunta = (Element) eElement.getElementsByTagName("pregunta").item(0);
                        multimedia = eElement.getElementsByTagName("multimedia").item(0).getTextContent();
                        textoPregunta = eElement.getElementsByTagName("pregunta").item(0).getTextContent();
                        tipo = elementoPregunta.getAttribute("tipo");
                        NodeList listaHijos = eElement.getElementsByTagName("respuesta");
                        for(j=0; j < listaHijos.getLength();j++){
                            Element eElementHijo = (Element) listaHijos.item(j);
                            idRespuesta = eElementHijo.getAttribute("id");
                            textoRespuesta = eElementHijo.getTextContent();
                            valorBooleano =  Boolean.parseBoolean( eElementHijo.getAttribute("correcta"));
                            listTextoRespuesta.add(eElementHijo.getTextContent());
                            listValorBooleano.add(valorBooleano);
                        }
						
						//Colocamos las respuestas y sus valores en un archivo JSON
                        for(j=j; j < 8;j++){
                            listTextoRespuesta.add("");
                            listValorBooleano.add(false);
                        }
                    }                       
                }
				
				/* Colocamos en un archivo JSON los datos para que puedan 
                   ser leídos por el componente EditarPregunta.js */
                String cantidadRespuestas = Integer.toString(j);
                JSONObject obj = new JSONObject();
                obj.put("idPregunta", pregunta);
                obj.put("textoPregunta", textoPregunta);
                obj.put("textoRespuesta", listTextoRespuesta);
                obj.put("valorBooleano", listValorBooleano);
                obj.put("cantidadRespuestas",cantidadRespuestas);
                
                    try{
                        FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/")+"/edicionPregunta.json");
                        
						//Guardamos el archivo JSON con los datos de la pregunta
                        archivoJson.write(obj.toJSONString());
                        archivoJson.flush();
                        archivoJson.close();

                    }catch(Exception ex){
                        System.out.println(ex);
                    }          
            }catch (Exception e) {
              e.printStackTrace();
              System.out.println("Error al momento de leer el archivo XML");
            }

            output= "OK";

            return output;
    }
        
}
    

