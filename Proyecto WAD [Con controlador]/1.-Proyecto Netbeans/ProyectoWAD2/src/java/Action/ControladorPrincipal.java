/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elite
 */
public class ControladorPrincipal extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = (String)request.getParameter("accion");
        
        if(accion.equals("LoginAction")){
            RequestDispatcher d = request.getRequestDispatcher("LoginAction");;
            d.forward(request, response);
        }else if(accion.equals("TablaPreguntasAction")){
            RequestDispatcher d = request.getRequestDispatcher("TablaPreguntasAction");;
            d.forward(request, response);
        }else if(accion.equals("EscribirExamenAction")){
            RequestDispatcher d = request.getRequestDispatcher("EscribirExamenAction");;
            d.forward(request, response);
        }else if(accion.equals("CrearPreguntaAction")){
            RequestDispatcher d = request.getRequestDispatcher("CrearPreguntaAction");;
            d.forward(request, response);
        }else if(accion.equals("EvaluarExamenAction")){
            RequestDispatcher d = request.getRequestDispatcher("EvaluarExamenAction");;
            d.forward(request, response);
        }else if(accion.equals("SubirArchivoAction")){
            RequestDispatcher d = request.getRequestDispatcher("SubirArchivoAction");;
            d.forward(request, response);
        }else if(accion.equals("EscribirExamenAuxAction")){
            RequestDispatcher d = request.getRequestDispatcher("EscribirExamenAuxAction");;
            d.forward(request, response);
        }else if(accion.equals("EvaluarPreguntaAction")){
            RequestDispatcher d = request.getRequestDispatcher("EvaluarPreguntaAction");;
            d.forward(request, response);
        }else if(accion.equals("EditarExamenAction")){
            RequestDispatcher d = request.getRequestDispatcher("EditarExamenAction");;
            d.forward(request, response);
        }else if(accion.equals("BorrarExamenAction")){
            RequestDispatcher d = request.getRequestDispatcher("BorrarExamenAction");;
            d.forward(request, response);
        }else if(accion.equals("VerPreguntaAction")){
            RequestDispatcher d = request.getRequestDispatcher("VerPreguntaAction");;
            d.forward(request, response);
        }else if(accion.equals("EditarPreguntaAction")){
            RequestDispatcher d = request.getRequestDispatcher("EditarPreguntaAction");;
            d.forward(request, response);
        }else if(accion.equals("BorrarPreguntaAction")){
            RequestDispatcher d = request.getRequestDispatcher("BorrarPreguntaAction");;
            d.forward(request, response);
        }else if(accion.equals("TablaExamenesAction")){
            RequestDispatcher d = request.getRequestDispatcher("TablaExamenesAction");;
            d.forward(request, response);
        }
        
    }

}
