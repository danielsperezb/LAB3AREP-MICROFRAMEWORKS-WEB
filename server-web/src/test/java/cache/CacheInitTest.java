package cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import webclient.WebClientInit;

import static org.junit.jupiter.api.Assertions.*;

class CacheInitTest {

    @Mock
    private WebClientInit webClientInit;
    @InjectMocks
    private CacheInit cacheInit;


    @BeforeEach
    void setUp() {
        webClientInit = Mockito.mock(WebClientInit.class);
        cacheInit = new CacheInit(webClientInit);
    }

    @Test
    public void debeCrearUnaInstanciaNueva(){
        cacheInit = null;
        CacheInit.getInstance();
    }

    @Test
    public void debeDevolverPeliculaDeLaCache(){
        //given
        String nombrePelicula = "Messi";
        cacheInit.getPeliculas().put("Messi", "pelicula info");

        //when
        cacheInit.buscarPeliculaPorNombre(nombrePelicula);

        //then
        assertEquals(1, cacheInit.getContador());
    }

    @Test
    public void debeDevolverPeliculaDeLaApiExterna(){
        //given
        String nombrePelicula = "Messi";

        Mockito.when(webClientInit.buscarPeliculaApiExterna("Messi")).thenReturn("messi-external-api");

        //when
        String respuesta = cacheInit.buscarPeliculaPorNombre(nombrePelicula);

        //then
        assertEquals(0, cacheInit.getContador());
        assertEquals("messi-external-api", respuesta);
    }

}