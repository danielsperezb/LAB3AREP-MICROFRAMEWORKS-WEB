import cache.CacheInit;

public class TestThreadImpl extends Thread{

    private String nombrePelicula;
    private String respuesta;

    private static final CacheInit cache = CacheInit.getInstance();

    public TestThreadImpl(String nombrePelicula){
        this.nombrePelicula = nombrePelicula;
    }

    @Override
    public void run(){
        respuesta = cache.buscarPeliculaPorNombre(nombrePelicula);
    }

    public String getRespuesta() {
        return respuesta;
    }
}
