package usuario;
// Generated 25-abr-2019 17:51:16 by Hibernate Tools 4.3.1



/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private int idUsuario;
     private String nombreUsuario;
     private String clave;

    public Usuarios() {
    }

	
    public Usuarios(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Usuarios(int idUsuario, String nombreUsuario, String clave) {
       this.idUsuario = idUsuario;
       this.nombreUsuario = nombreUsuario;
       this.clave = clave;
    }
   
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombreUsuario() {
        return this.nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getClave() {
        return this.clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }




}


