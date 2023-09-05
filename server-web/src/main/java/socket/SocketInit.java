package socket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Clase utilizada para crear el socket principal de la aplicación
 * Es la puerta de entrada para las peticiones de la aplicación.
 */
public class SocketInit {
    private final static int PUERTO_SERVIDOR = 35000;
    static Map<String, ManejadorPeticion> recursos = new HashMap<>();
    static String direccionArchivoEstatico = "";

    /**
     * Metodo que implementa la clase ServerSocket y que abre un puerto de escucha para
     * recibir peticiones externas.
     */
    public static void socketInitialize() {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO_SERVIDOR);
            System.out.println("Servidor escuchando en el puerto :"+ PUERTO_SERVIDOR);
            boolean running = true;

            while(running) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    System.out.println("Recibiendo... ");

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine, outputLine;

                    
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.startsWith("GET /")){
                            inputLine = inputLine.split("/")[1].split(" ")[0];
                            outputLine = "HTTP/1.1 200 \r\n" +
                                    "Content-Type: application/json \r\n" +
                                    "Access-Control-Allow-Origin: * \r\n" +
                                    "\r\n" +
                                    get(inputLine);

                            out.println(outputLine);
                            //System.out.println(recursos);
                        } else if( inputLine.startsWith("POST /") ){
                            String message = "";
                            if( inputLine.contains("direccionArchivo") ){
                                inputLine = inputLine.split(" ")[1].split("[?]")[1].split("=")[1];
                                direccionArchivoEstatico = inputLine.equals("default") ? "" : inputLine;
                                message = "Direccion del path actualizada correctamente";
                            } else {
                                inputLine = inputLine.split("/")[1].split(" ")[0];

                                registrarRecurso(inputLine, type -> getResourceByType(type));
                                message = "El recurso ha sido creado correctamente";
                                System.out.println(recursos);
                            }
                            outputLine = "HTTP/1.1 200 \r\n" +
                                    "Content-Type: application/json \r\n" +
                                    "Access-Control-Allow-Origin: * \r\n" +
                                    "\r\n" +
                                    "\n { \n \"status\": \"ok\", \n \"message\": \""+message+"\" \n }";

                            out.println(outputLine);
                        }

                        if (!in.ready()) {
                            break;
                        }
                    }

                    out.close();
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error al recibir información: "+ e.getMessage());
                    System.exit(1);
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("No se puede abrir la conexión en el puerto "+ PUERTO_SERVIDOR + " : "+ e.getMessage());
            System.exit(1);
        }
    }

    private static String get(String tipoRecurso){
        if( recursos.containsKey(tipoRecurso) ){
            return recursos.get(tipoRecurso).manejarPeticion(tipoRecurso);
        }

        return "\n { \n \"status\": \"bad_request\", \n \"message\": \"no se ha encontrado el recurso\"\n }";
    }

    private static void registrarRecurso(String url, ManejadorPeticion manejadorPeticion){
        recursos.put(url, manejadorPeticion);
    }

    private static String getResourceByType(String response) {
        String finalResponse = "";

        try {
            if( response.equalsIgnoreCase("html") ){
                String htmlResponse = getFinalResponse(response, "index");
                finalResponse = htmlResponse.replace("\"", "'");
            } else if ( response.equalsIgnoreCase("js") ) {
                String jasResponse = getFinalResponse(response, "index");
                finalResponse =  jasResponse.replace("\"", "'");
            } else if ( response.equalsIgnoreCase("jpg") ){
                finalResponse =  getResponseFromImage("piolin", response);
            } else if ( response.equalsIgnoreCase("png") ){
                finalResponse =  getResponseFromImage("logo", response);
            } else {
                finalResponse = getFinalResponse(response, "style");
            }
        }catch (IOException error){
            System.out.println("Error obteniendo el recurso -> "+ error.getMessage());
        }
        return "\n { \n \"status\": \"ok\", \n \"resource\": \""+ response +"\", \n \"body\": \"" + finalResponse + " \" \n }";
    }

    private static String getResponseFromImage(String name, String resource){
        String response = "";
        byte[] imageData = null;
        String ruta = (direccionArchivoEstatico.equals("") ? direccionArchivoEstatico : direccionArchivoEstatico + "/")  + name+"."+resource;
        try (InputStream inputStream = SocketInit.class.getClassLoader().getResourceAsStream(ruta)) {
            if( inputStream != null ){
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, resource, baos);
                imageData = baos.toByteArray();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
                response = Base64.getEncoder().encodeToString(byteArrayInputStream.readAllBytes());
            } else {
                response = "No se encontró el archivo en la ruta: "+ ruta;
            }
        } catch (IOException e) {
            System.out.println("Error encodeando la imagen a base64: "+ e.getMessage());
        }
        return response;
    }

    private static String getFinalResponse(String resource, String name) throws IOException {
        StringBuilder finalResponse = new StringBuilder();
        String ruta = (direccionArchivoEstatico.equals("") ? direccionArchivoEstatico : direccionArchivoEstatico + "/") + name + "." + resource;

        try(InputStream inputStream = SocketInit.class.getClassLoader().getResourceAsStream(ruta) ){

            if( inputStream != null ){
                InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream));

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String lineaArchivo;
                while ( (lineaArchivo = bufferedReader.readLine())  != null){
                    finalResponse.append(lineaArchivo);
                }
            } else {
                finalResponse.append("No se encontró el archivo en la ruta: "+ ruta);
            }
        }
        return finalResponse.toString();
    }
}
