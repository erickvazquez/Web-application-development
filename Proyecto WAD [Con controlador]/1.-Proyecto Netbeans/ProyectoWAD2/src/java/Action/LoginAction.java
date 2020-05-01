package Action;

import usuario.HibernateUtil;
import usuario.Usuarios;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.hibernate.Session;

public class LoginAction extends HttpServlet implements Serializable
{
    private String email, password;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String output; //Variable para almacenar la dirección a ser redireccionado
        String query; //Consulta a la base de datos

        Usuarios login = null;
        HttpSession session = request.getSession();
        Session hibernatesession;

        hibernatesession = HibernateUtil.getSessionFactory().openSession();
        hibernatesession.beginTransaction();

        email = request.getParameter("email");
        password = request.getParameter("password");

        if (email != null && password != null && !email.equals("") && !password.equals(""))
        {
            query = "from Usuarios where nombre_usuario='" + email + "'AND clave='" + password + "'";
            login = (Usuarios) hibernatesession.createQuery(query).uniqueResult();
            
            /* Colocamos el atributo 'email' en un objeto JSON para 
               para ser escrito en el archivo 'datos.json' */
            JSONObject obj = new JSONObject();
            obj.put("email", email);
            
            try
            {
                /* Creamos un archivo JSON para guardar el objeto previamente creado
                   de manera que pueda ser leído por el componente Bienvenida.js */
                FileWriter archivoJson = new FileWriter(this.getServletContext().getRealPath("/")+"/datos.json");
                
                archivoJson.write(obj.toJSONString());
                archivoJson.flush();
                archivoJson.close();
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
            
            output = "Bienvenida.jsp";
            session.setAttribute("User", email);
        } else
        {
            output = "Inicio.jsp";
        }
        if (login == null)
        {
            output = "Inicio.jsp";
        }
        response.sendRedirect(output);
    }
}