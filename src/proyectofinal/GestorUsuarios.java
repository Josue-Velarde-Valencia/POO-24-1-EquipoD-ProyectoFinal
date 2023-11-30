/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal;

/**
 *
 * @author JMMVF
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorUsuarios {
    private List<Usuario> usuarios;
    private String archivoUsuarios;

    public GestorUsuarios(List<Usuario> usuarios, String archivoUsuarios) {
        this.usuarios = usuarios;
        this.archivoUsuarios = archivoUsuarios;
    }

    public GestorUsuarios(String archivoUsuarios) {
        this.archivoUsuarios = archivoUsuarios;
        this.usuarios = new ArrayList<>();
        cargarUsuariosDesdeArchivo();
    }

    private void cargarUsuariosDesdeArchivo() {
        try (Scanner scanner = new Scanner(new File(archivoUsuarios))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                Usuario usuario = new Usuario(linea);
                usuarios.add(usuario);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de usuarios no encontrado. Se creará uno nuevo.");
        }
    }

    public void agregarUsuarioDesdeConsola() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el apellido:");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese el correo:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        int contraseña = scanner.nextInt();

        int nuevoId = usuarios.size() + 1; // Asignar un ID único
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre, apellido, correo,contraseña);
        usuarios.add(nuevoUsuario);
        guardarUsuariosEnArchivo();
    }

    public void eliminarUsuarioDesdeConsola() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del usuario a eliminar:");
        int idEliminar = scanner.nextInt();

        usuarios.removeIf(usuario -> usuario.getId() == idEliminar);
        guardarUsuariosEnArchivo();
    }

    public Optional<Usuario> buscarUsuariosPorNombre(String nombreBuscado) {
        return usuarios.stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombreBuscado))
                .findFirst();
    }
    
    private void guardarUsuariosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.obtenerFormatoArchivo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

