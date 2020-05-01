package Actions;

import java.io.FileWriter;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import usuario.HibernateUtil;
import usuario.Usuarios;

public class LoginAction {
     private String email, password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public LoginAction() {
    }
    
    public String execute() throws Exception {
     

  
        String output; //Variable para almacenar la dirección a ser redireccionado
        String query; //Consulta a la base de datos

        Usuarios login = null;
        HttpSession session = ServletActionContext.getRequest().getSession();
        Session hibernatesession;

        hibernatesession = HibernateUtil.getSessionFactory().openSession();
        hibernatesession.beginTransaction();


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
                   de manera que pueda ser leído por el componente Login.js */
                FileWriter archivoJson = new FileWriter(ServletActionContext.getServletContext().getRealPath("/")+"/datos.json");
                
                archivoJson.write(obj.toJSONString());
                archivoJson.flush();
                archivoJson.close();
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
            
            output = "OK";
            session.setAttribute("User", email);
        } else
        {
           output= "errorUsuario";
        }
        if (login == null)
        {
           output= "errorUsuario";
        }
        return output;
    }
    }
    

