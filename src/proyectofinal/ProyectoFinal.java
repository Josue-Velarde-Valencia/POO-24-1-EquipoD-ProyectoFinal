/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal;

/**
 *
 * @author kevin
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoFinal {

    // Método para leer archivos y devolver una lista de strings (líneas)
    public static List<String> leerArchivo(String nombreArchivo) throws IOException {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }
    
    public static String generarNombre(List<String> nombres, List<String> apellidos, Random rand) {
        String nombreAlumno;
        if (rand.nextBoolean()) {
            // Generar un solo nombre
            nombreAlumno = nombres.get(rand.nextInt(nombres.size())) + " " +
                    apellidos.get(rand.nextInt(apellidos.size())) + " " +
                    apellidos.get(rand.nextInt(apellidos.size()));
        } else {
            // Generar dos nombres
            nombreAlumno = nombres.get(rand.nextInt(nombres.size())) + " " +
                    nombres.get(rand.nextInt(nombres.size())) + " " +
                    apellidos.get(rand.nextInt(apellidos.size())) + " " +
                    apellidos.get(rand.nextInt(apellidos.size()));
        }
        return nombreAlumno;
    }
    
    public static void generarArchivoCSVAlumnos(List<Alumnos> alumnos, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir encabezados
            writer.write("Nombre,Numero de Cuenta,Promedio,Direccion,Colonia,Delegación,Semestre,Edad,Materias\n");

            // Escribir datos de cada alumno
            for (Alumnos alumno : alumnos) {
                // Crear una cadena con los nombres de las materias separadas por comas
                StringBuilder materiasConcatenadas = new StringBuilder();
                List<Materia> materias = alumno.getMaterias();
                for (Materia materia : materias) {
                    materiasConcatenadas.append(materia.getNombreMateria()).append(", ");
                }
                
                String materiasString = materiasConcatenadas.toString();
                // Escribir datos del alumno y sus materias en el archivo CSV
                String datosAlumno =
                        alumno.getNombre() + "," +
                        alumno.getNumeroCuenta() + "," +
                        alumno.getPromedio() +","+
                        alumno.getDireccion() + "," +
                        alumno.getSemestre() + "," +
                        alumno.getEdad() + "," +
                        materiasString + "\n";

                writer.write(datosAlumno);
            }

            System.out.println("Archivo CSV '" + nombreArchivo + "' creado exitosamente.");
        } catch (IOException ex) {
            Logger.getLogger(ProyectoFinal.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    // Método para mostrar la lista de alumnos
    public static void mostrarListaAlumnos(List<Alumnos> alumnos) {
        for (Alumnos alumno : alumnos) {
            System.out.println("Nombre: " + alumno.getNombre());
            System.out.println("Número de cuenta: " + alumno.getNumeroCuenta());
            System.out.println("Promedio: "+alumno.getPromedio());
            System.out.println("Dirección: " + alumno.getDireccion());
            System.out.println("Semestre: " + alumno.getSemestre());
            System.out.println("Edad: " + alumno.getEdad());
            System.out.println("Materias:");
            for (Materia materia : alumno.getMaterias()) {
                System.out.println("- " + materia.getNombreMateria() + " (Código: " + materia.getCodigoMateria() +
                        ", Créditos: " + materia.getCreditos() + ", Calificación: "+ materia.getCalificacion() +')');
            }
            System.out.println("------------------------------------");
        }
    }
    
    // Método para generar la lista completa de materias
    public static List<Materia> generarListaMaterias(List<String> materiasData) {
        List<Materia> todasLasMaterias = new ArrayList<>();
        for (String materiaData : materiasData) {
            String[] partes = materiaData.split(",");
            if (partes.length == 4) {
                int semestreMateria = Integer.parseInt(partes[0]);
                String nombreMateria = partes[1];
                int codigo = Integer.parseInt(partes[2]);
                int creditos = Integer.parseInt(partes[3]);
                int calificacion = 0;

                Materia nuevaMateria = new Materia(semestreMateria,nombreMateria, codigo, creditos, calificacion);
                todasLasMaterias.add(nuevaMateria);
            }
        }
        return todasLasMaterias;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int bandera = 0,opc=0, opc2=0;
        Random rand = new Random();
        List<Alumnos> alumnos = new ArrayList<>();
        String archivoUsuarios = "usuarios.txt";
        GestorUsuarios gestorUsuarios = new GestorUsuarios(archivoUsuarios);
        GestorAlumnos gestorAlumno = new GestorAlumnos();
        
        System.out.println("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
        System.out.println("\t\t -- SISTEMA DE INSCRIPCION --");
        System.out.println("<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>");
        System.out.println("|\t\t - Usuario: \t\t\t\t\t|");
        String usuario  = sc.nextLine();
        
        Optional<Usuario> usuarioEncontrado = gestorUsuarios.buscarUsuariosPorNombre(usuario);
        
        if (usuarioEncontrado.isPresent()) {
            System.out.println("|\t\t >>>> Usuario encontrado <<<< \t\t\t|");
            System.out.println("|\t\t - Introduce tu contraseña: \t\t\t|");
            int contraseña = sc.nextInt();
            if(contraseña == usuarioEncontrado.get().getContraseña()){
                System.out.println("|\t\t     Contraseña correcta     \t\t\t|");
                System.out.println("|\t\t\t -- Bienvenidx --  \t\t\t|");
                bandera=1;
            }else{
                System.out.println("|\t Contraseña incorrecta \t|");
                return;
            }
            
        } else {
            System.out.println("No se encontró un usuario con ese nombre.");
            return;
        }
        
        
        if(bandera==1){
            try {
                // Lee los archivos de nombres, apellidos, direcciones y materias
                List<String> nombres = leerArchivo("nombres.txt");
                List<String> apellidos = leerArchivo("apellidos.txt");
                List<String> direcciones = leerArchivo("direcciones.txt");
                List<String> materiasData = leerArchivo("materias.txt");
                List<Materia> todasLasMaterias = generarListaMaterias(materiasData);
                // Trabajar con las líneas leídas del archivo
                do{
                    System.out.println("|\t 1. Generar base de datos. \t|");
                    System.out.println("|\t 2. Mostrar lista de alumnos.\t|");
                    System.out.println("|\t 3. Modificar alumno. \t|");
                    System.out.println("|\t 4. Generar numeros de inscripcion. \t|");
                    System.out.println("|\t 5. Salir del programa. \t|");
                    System.out.println("\t - Introduce una opcion: \t");
                    opc = sc.nextInt();
                    switch(opc){
                        case 1:
                            for (int i = 0; i < 1000; i++) {
                                float promedio = 0;
                                String nombreAlumno = generarNombre(nombres, apellidos, rand);
                                long numeroCuenta = (long) (Math.random() * 900_000_000L) + 100_000_000L; // Número de cuenta de máximo 9 dígitos
                                String direccion = direcciones.get(rand.nextInt(direcciones.size()));
                                int semestre = rand.nextInt(10) + 1;
                                int edad = 18 + (semestre - 1); // Edad dependiendo del semestre

                                List<Materia> materiasAlumno = new ArrayList<>();
                                // Seleccionar cinco materias aleatorias de todasLasMaterias
                                Collections.shuffle(todasLasMaterias); // Mezcla la lista de materias
                                for (int j = 0; j < (5*semestre); j++) {
                                    todasLasMaterias.get(j).setCalificacion((int)(Math.random() * 6) + 5);
                                    promedio += todasLasMaterias.get(j).getCalificacion();
                                    materiasAlumno.add(todasLasMaterias.get(j));
                                }

                                promedio /= (5*semestre);
                                Alumnos nuevoAlumno = new Alumnos(nombreAlumno, numeroCuenta, promedio, direccion, semestre, edad, materiasAlumno,0);
                                double num = nuevoAlumno.calcularNumeroInscripcion();
                                nuevoAlumno.setNumInscripcion(num);
                                alumnos.add(nuevoAlumno);
                            }
                            generarArchivoCSVAlumnos(alumnos, "alumnos.csv");
                        break;
                        case 2:
                            // Mostrar la lista de alumn
                            mostrarListaAlumnos(alumnos);
                        break;
                        case 3:
                            do{
                                System.out.println("\t\t ++++ Opciones de modificacion ++++ ");
                                System.out.println("|\t 1. Crear. \t\t|");
                                System.out.println("|\t 2. Actualizar. \t\t|");
                                System.out.println("|\t 3. Borrar. \t\t|");
                                System.out.println("|\t 4. Buscar. \t\t|");
                                System.out.println("|\t 5. Regresar a menu principal. \t\t|");
                                System.out.println("\t - Ingresa el numero de opcion deseada: \t");
                                opc2 = sc.nextInt();
                                sc.nextLine();
                                
                                switch(opc2){
                                    case 1:
                                        gestorAlumno.agregarAlumno();
                                    break;
                                    case 2:
                                        System.out.println("\t -Introduce el nombre del alumno: ");
                                        String nombreen=sc.nextLine();
                                        System.out.println(nombreen);
                                        Alumnos alumnoEncontrado = gestorAlumno.buscarAlumno(nombreen, alumnos);
                                        if (alumnoEncontrado != null) {
                                            // Haz algo con el alumno encontrado, por ejemplo, mostrar sus datos
                                            System.out.println("Alumno encontrado: " + alumnoEncontrado.getNombre());
                                            gestorAlumno.modificarDatoAlumno(alumnoEncontrado);
                                        } else {
                                            System.out.println("Alumno no encontrado.");
                                        }
                                    break;
                                    case 3:
                                        gestorAlumno.borrarAlumno();
                                    break;
                                    case 4:
                                        gestorAlumno.buscarAlumnoPorNombre();
                                    break;
                                    case 5:
                                        System.out.println(">>>>> Regresando a menu principal <<<<<");
                                    break;
                                    default: System.out.println("Esta opcion no existe");
                                }
                                
                                        
                            }while(opc2<=4 && opc2>=1);
                            
                        break;    
                        case 4:
                            // Crear una copia de la lista original
                            List<Alumnos> copiaLista = new ArrayList<>(alumnos);

                            // Crear un objeto de la clase Alumnos para ordenar la copia
                            Alumnos alumno = new Alumnos();
                            alumno.ordenarPorNumeroInscripcion(copiaLista);

                            // Mostrar la lista ordenada por número de inscripción
                            // Mostrar la lista ordenada por posición en la lista
                            int posicion = 1;
                            for (Alumnos a : copiaLista) {
                                System.out.println("Posición " + posicion + " - Nombre: " + a.getNombre());
                                posicion++;
                            }
                        break;
                        case 5:
                            System.out.println(">>>>> Saliendo del programa <<<<<");
                            return;
                        default: System.out.println("Esta opción no existe");
                    }
                }while(opc<=5 && opc>=1);
            }catch (IOException e) {
                // Manejo de la excepción IOException
                e.printStackTrace(); // Otra forma de manejar la excepción, puedes imprimir el stack trace para depuración
            }
        }
        
    }
}

