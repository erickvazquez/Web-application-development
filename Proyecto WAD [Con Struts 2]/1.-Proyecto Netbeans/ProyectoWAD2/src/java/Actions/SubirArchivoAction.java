/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class SubirArchivoAction extends ActionSupport {
    private File archivo;  
    private String archivoContentType;  
    private String archivoFileName;  
  
    public String execute() {  
        try{
        String filePath = ServletActionContext.getServletContext().getRealPath("/")+ "/multimedia";     
        System.out.println("Image Location:" + filePath);//see the server console for actual location  
        File fileToCreate = new File(filePath,archivoFileName);  
        FileUtils.copyFile(archivo, fileToCreate);//copying source file to new file  
        }
        catch (IOException ex){
            System.out.println(ex);
        }
        return "OK"; 
    }  
    public File getArchivo() {  
        return archivo;  
    }  
    public void setArchivo(File archivo) {  
        this.archivo = archivo;  
    }  
    public String getArchivoContentType() {  
        return archivoContentType;  
    }  
  
    public void setArchivoContentType(String archivoContentType) {  
        this.archivoContentType = archivoContentType;  
    }  
    public String getArchivoFileName() {  
        return archivoFileName;  
    }  
    public void setArchivoFileName(String archivoFileName) {  
        this.archivoFileName = archivoFileName;  
    }  
}  