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
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import static proyectofinal.ProyectoFinal.generarListaMaterias;
import static proyectofinal.ProyectoFinal.leerArchivo;

public class GestorAlumnos {

    private static final String NOMBRE_ARCHIVO = "alumnos.csv";
    private final List<String> materiasData;
    
    public GestorAlumnos() throws IOException {
        this.materiasData = leerArchivo("materias.txt");
    }
    

    public void agregarAlumno() throws IOException {
        float promedio=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del alumno:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el numero de cuenta del alumno:");
        int numerocuenta = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea pendiente después de nextInt()
        System.out.println("Ingrese la direccion del alumno:");
        String direccion = scanner.nextLine();
        System.out.println("Ingrese el semestre a cursar del alumno:");
        int semestre = scanner.nextInt();
        System.out.println("Ingrese el edad a cursar del alumno:");
        int edad = scanner.nextInt();
        if(edad < 18 + (semestre - 1)){
            System.out.println("Edad no valida");
            return;
        }
        
        List<Materia> materiasAlumno = new ArrayList<>();
        List<Materia> todasLasMaterias = generarListaMaterias(materiasData);
        Collections.shuffle(todasLasMaterias); // Mezcla la lista de materias
        for (int j = 0; j < (5*semestre); j++) {
            todasLasMaterias.get(j).setCalificacion((int)(Math.random() * 6) + 5);
            promedio += todasLasMaterias.get(j).getCalificacion();
            materiasAlumno.add(todasLasMaterias.get(j));
        }
        
        promedio /= (5*semestre);
        
        Alumnos nuevoAlumno = new Alumnos(nombre, numerocuenta, promedio, direccion, semestre, edad, materiasAlumno,0);

        // Escribir en el archivo
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw)) {
            pw.print(nombre + "," + numerocuenta + "," + promedio + "," + direccion + "," + semestre + "," + edad+ "," );
            for (Materia materia : materiasAlumno) {
                pw.print(materia.getNombreMateria() +"," );
            }
            pw.println();
            System.out.println("Alumno agregado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al agregar alumno: " + e.getMessage());
        }
    }
    
    public void modificarDatoAlumno(Alumnos alumno) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Qué dato desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Número de cuenta");
        System.out.println("3. Dirección");
        System.out.println("4. Semestre");
        System.out.println("5. Edad");
        System.out.println("6. Cancelar");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                scanner.nextLine();
                System.out.println("Ingrese el nuevo nombre:");
                String nuevoNombre = scanner.nextLine();
                alumno.setNombre(nuevoNombre);
                System.out.println("Nombre modificado: " + alumno.getNombre()); // Salida para verificar la dirección modificada
                break;
            case 2:
                System.out.println("Ingrese el nuevo número de cuenta:");
                int nuevoNumeroCuenta = scanner.nextInt();
                alumno.setNumeroCuenta(nuevoNumeroCuenta);
                break;
            case 3:
                scanner.nextLine();
                System.out.println("Ingrese la nueva dirección:");
                String nuevaDireccion = scanner.nextLine();
                alumno.setDireccion(nuevaDireccion);
                 System.out.println("Dirección modificada: " + alumno.getDireccion()); // Salida para verificar la dirección modificada
                
                break;
            case 4:
                System.out.println("Ingrese el nuevo semestre:");
                int nuevoSemestre = scanner.nextInt();
                alumno.setSemestre(nuevoSemestre);
                break;
            case 5:
                System.out.println("Ingrese la nueva edad:");
                int nuevaEdad = scanner.nextInt();
                alumno.setEdad(nuevaEdad);
                break;
            case 6:
                System.out.println("Modificación cancelada.");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            List<String> lineasArchivo = new ArrayList<>();
            String linea;
            br.readLine(); // Omitir la primera línea (encabezado)
            while ((linea = br.readLine()) != null) {
                // Separar los datos por algún delimitador, como una coma si estás usando formato CSV
                String[] datosAlumno = linea.split(","); //los datos están separados por comas
                    
                // Comprobar si el número de cuenta coincide
                if (datosAlumno.length > 1 && Long.parseLong(datosAlumno[1].trim()) == alumno.getNumeroCuenta()) {
                    // Modificar la línea correspondiente al alumno
                    StringBuilder nuevasMaterias = new StringBuilder();
                    for (Materia materia : alumno.getMaterias()) {
                        // Supongamos que el formato de la materia en el archivo es "nombreMateria,calificacion"
                        nuevasMaterias.append(materia.getNombreMateria()).append(",");
                    }
                    
                    String materiasString = nuevasMaterias.toString();

                    // Escribir datos del alumno y sus materias en el archivo CSV
                    String datos = alumno.getNombre() + "," +alumno.getNumeroCuenta() + "," +
                            alumno.getPromedio() +","+
                            alumno.getDireccion() + "," +
                            alumno.getSemestre() + "," +
                            alumno.getEdad() + "," +
                            materiasString;
                    
                    lineasArchivo.add(datos);
                } else {
                    lineasArchivo.add(linea);
                }
            }

            // Escribir de nuevo al archivo con los datos actualizados
            try (PrintWriter pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
                for (String lineaActualizada : lineasArchivo) {
                    pw.println(lineaActualizada);
                }
                System.out.println("Datos actualizados en el archivo.");
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

    }


    public void borrarAlumno(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del alumno a borrar:");
        String nombreABorrar = scanner.nextLine();

        // Leer el archivo y guardar en una lista todos los alumnos excepto el que se desea borrar
        List<String> lineasArchivo = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.contains(nombreABorrar)) {
                    lineasArchivo.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al borrar alumno: " + e.getMessage());
        }

        // Escribir de nuevo al archivo excluyendo al alumno a borrar
        try (PrintWriter pw = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (String linea : lineasArchivo) {
                pw.println(linea);
            }
            System.out.println("Alumno borrado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al borrar alumno: " + e.getMessage());
        }
    }
    
    public void buscarAlumnoPorNombre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del alumno a buscar:");
        String nombreBuscado = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            boolean encontrado = false;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(nombreBuscado)) {
                    encontrado = true;
                    System.out.println("Información del alumno:");
                    System.out.println(linea);
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Alumno no encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Error al buscar alumno: " + e.getMessage());
        }
    }
    public Alumnos buscarAlumno(String nombreBuscar, List<Alumnos> listaAlumnos) {
        for (Alumnos alumno : listaAlumnos) {
            if (alumno.getNombre().equalsIgnoreCase(nombreBuscar)) {
                return alumno; // Retorna el alumno si se encuentra
            }
        }
        return null; // Retorna null si no se encuentra ningún alumno con ese nombre
    }
    
    
    
}
