/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal;

/**
 *
 * @author JMMVF
 */
public class Usuario{
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private int contraseña;
    
    public Usuario(int id, String nombre, String apellido, String correo, int contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña=contraseña;
    }

    public Usuario(String linea) {
        String[] partes = linea.split(",");
        this.id = Integer.parseInt(partes[0]);
        this.nombre = partes[1];
        this.apellido = partes[2];
        this.correo = partes[3];
        this.contraseña = Integer.parseInt(partes[4]);
    }

    // Método para obtener una representación de usuario para guardar en archivo
    public String obtenerFormatoArchivo() {
        return id + "," + nombre + "," + apellido + "," + correo +","+ contraseña;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public int getContraseña() {
        return contraseña;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContraseña(int contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
