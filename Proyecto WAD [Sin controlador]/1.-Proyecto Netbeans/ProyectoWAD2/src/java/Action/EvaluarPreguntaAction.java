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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class EvaluarPreguntaAction extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        String examen = request.getParameter("examen");
        String pregunta = request.getParameter("pregunta");
        int errores = Integer.parseInt(request.getParameter("err"));
        String texto = "", imagen = "", tipo = "", anterior = "", siguiente = "";

        try {
            File archivo = new File(this.getServletContext().getRealPath("/") + "/XML/Examenes.xml");
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
                        File archivop = new File(this.getServletContext().getRealPath("/") + "/XML/Preguntas.xml");
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
                                }else{
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
                                    FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/") + "/preguntamc.json");
                                    archivoJson.write(obj.toJSONString());
                                    archivoJson.flush();
                                    archivoJson.close();
                                    
				    response.sendRedirect("EvaluarPregunta.jsp");
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                    System.out.println("Error al momento de crear JSON");
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error al momento de leer el archivo Preguntas.xml. Se lee: id=" + pregunta + " pregunta=" + texto + " imagen=" + imagen + " tipo=" + tipo);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al momento de leer el archivo Examenes.xml");
        }
    }
}
