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

public class EvaluarExamenAction extends HttpServlet{

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
                    
                    float promedio=((float)listaPreg.getLength()-(float)errores)*10/(float)listaPreg.getLength();
                    
                    JSONObject obj = new JSONObject();
                    obj.put("examen", examen);
                    obj.put("resultado", promedio);

                    try {
                        FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/") + "/examenEvaluado.json");
                        archivoJson.write(obj.toJSONString());
                        archivoJson.flush();
                        archivoJson.close();

                        response.sendRedirect("ResultadoExamen.jsp");
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
    }
}
