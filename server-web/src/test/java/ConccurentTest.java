import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConccurentTest {

    @Test
    public void testConcurrenciaCincoPeliculasServicioExterno() throws IOException {
        ArrayList<TestThreadImpl> threads = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);

        threads.add(new TestThreadImpl("soccer"));
        threads.add(new TestThreadImpl("ana"));
        threads.add(new TestThreadImpl("martin"));
        threads.add(new TestThreadImpl("x"));
        threads.add(new TestThreadImpl("messi"));

        threads.forEach(Thread::start);
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                System.out.println("Error en el thread: "+ e.getMessage());
            }
        });

        assertEquals("{\"Title\":\"Shaolin Soccer\",\"Year\":\"2001\",\"Rated\":\"PG\",\"Released\":\"05 Jul 2001\",\"Runtime\":\"87 min\",\"Genre\":\"Action, Comedy, Fantasy\",\"Director\":\"Stephen Chow\",\"Writer\":\"Stephen Chow, Kan-Cheung Tsang, Min-Hun Fung\",\"Actors\":\"Stephen Chow, Wei Zhao, Yat-Fei Wong\",\"Plot\":\"A young Shaolin follower reunites with his discouraged brothers to form a soccer team using their martial art skills to their advantage.\",\"Language\":\"Cantonese, Mandarin\",\"Country\":\"Hong Kong, China\",\"Awards\":\"12 wins & 19 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjhlMWE5NzktNmE4Ny00ZWFlLTk5MzUtZDZhZmQ0YmVlNDI0XkEyXkFqcGdeQXVyNjc5NjEzNA@@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.3/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"89%\"},{\"Source\":\"Metacritic\",\"Value\":\"68/100\"}],\"Metascore\":\"68\",\"imdbRating\":\"7.3\",\"imdbVotes\":\"84,839\",\"imdbID\":\"tt0286112\",\"Type\":\"movie\",\"DVD\":\"23 May 2016\",\"BoxOffice\":\"$489,600\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", threads.get(0).getRespuesta());
        assertEquals("{\"Title\":\"Ana\",\"Year\":\"2020\",\"Rated\":\"N/A\",\"Released\":\"03 Jan 2020\",\"Runtime\":\"105 min\",\"Genre\":\"Comedy, Drama\",\"Director\":\"Charles McDougall\",\"Writer\":\"Cris Cole, Charles McDougall, Luillo Ruiz\",\"Actors\":\"Andy Garcia, Dafne Keen, Jeanne Tripplehorn\",\"Plot\":\"Ana meets Rafa in a chance encounter and they embark on a road trip to try and save him from bankruptcy, or worse.\",\"Language\":\"English, Spanish\",\"Country\":\"United States, Puerto Rico\",\"Awards\":\"N/A\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDdjNzk4ODItN2Q0Ni00YTFjLTkyNTAtNzMyMTc3ODM0NzlhXkEyXkFqcGdeQXVyNTk3MjE4NjI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"5.9/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"5.9\",\"imdbVotes\":\"1,794\",\"imdbID\":\"tt6865630\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"N/A\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", threads.get(1).getRespuesta());
        assertEquals("{\"Title\":\"Martin\",\"Year\":\"1977\",\"Rated\":\"R\",\"Released\":\"05 Jul 1978\",\"Runtime\":\"95 min\",\"Genre\":\"Drama, Horror\",\"Director\":\"George A. Romero\",\"Writer\":\"George A. Romero\",\"Actors\":\"John Amplas, Lincoln Maazel, Christine Forrest\",\"Plot\":\"A young man, who believes himself to be a vampire, goes to live with his elderly and hostile cousin in a small Pennsylvania town where he tries to redeem his blood-craving urges.\",\"Language\":\"English\",\"Country\":\"United States\",\"Awards\":\"1 win\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTg0MWYyOTAtYWNhOS00NWQ1LWEwYTMtN2VhMmU0ZmY3NWZmXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.0/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"90%\"},{\"Source\":\"Metacritic\",\"Value\":\"68/100\"}],\"Metascore\":\"68\",\"imdbRating\":\"7.0\",\"imdbVotes\":\"12,056\",\"imdbID\":\"tt0077914\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"N/A\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", threads.get(2).getRespuesta());
        assertEquals("{\"Title\":\"X\",\"Year\":\"2022\",\"Rated\":\"R\",\"Released\":\"18 Mar 2022\",\"Runtime\":\"105 min\",\"Genre\":\"Horror, Mystery, Thriller\",\"Director\":\"Ti West\",\"Writer\":\"Ti West\",\"Actors\":\"Mia Goth, Jenna Ortega, Brittany Snow\",\"Plot\":\"In 1979, a group of young filmmakers set out to make an adult film in rural Texas, but when their reclusive, elderly hosts catch them in the act, the cast find themselves fighting for their lives.\",\"Language\":\"English\",\"Country\":\"United States, Canada\",\"Awards\":\"3 wins & 38 nominations\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTJiMmE5YWItOWZjYS00YTg0LWE0MTYtMzg2ZTY4YjNkNDEzXkEyXkFqcGdeQXVyMTAzMDg4NzU0._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"6.6/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"93%\"},{\"Source\":\"Metacritic\",\"Value\":\"79/100\"}],\"Metascore\":\"79\",\"imdbRating\":\"6.6\",\"imdbVotes\":\"137,746\",\"imdbID\":\"tt13560574\",\"Type\":\"movie\",\"DVD\":\"14 Apr 2022\",\"BoxOffice\":\"$11,765,309\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", threads.get(3).getRespuesta());
        assertEquals("{\"Title\":\"Messi\",\"Year\":\"2014\",\"Rated\":\"TV-Y\",\"Released\":\"01 Jan 2015\",\"Runtime\":\"93 min\",\"Genre\":\"Documentary, Biography, Sport\",\"Director\":\"\u00c1lex de la Iglesia\",\"Writer\":\"Jorge Valdano S\u00e1enz, Jorge Valdano\",\"Actors\":\"Lionel Messi, Johan Cruijff, Kike Dom\u00ednguez\",\"Plot\":\"Lionel Messi from early life to international stardom.\",\"Language\":\"Spanish\",\"Country\":\"Spain\",\"Awards\":\"1 nomination\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZDA1NDdkM2UtM2M2ZS00NzJjLTk4YmQtNDhlYjM2MWVlOWE5XkEyXkFqcGdeQXVyMTA0MjU0Ng@@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.2/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"7.2\",\"imdbVotes\":\"4,248\",\"imdbID\":\"tt3538766\",\"Type\":\"movie\",\"DVD\":\"24 Feb 2018\",\"BoxOffice\":\"N/A\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}", threads.get(4).getRespuesta());
    }

    @Test
    public void testConcurrenciaNoEncontrarPeliculas() throws InterruptedException {
        ArrayList<TestThreadImpl> threads = new ArrayList<>();
        threads.add(new TestThreadImpl("eqweqew"));
        threads.add(new TestThreadImpl("fgddfs"));
        threads.add(new TestThreadImpl("asdasd"));
        threads.add(new TestThreadImpl("qwe12e"));
        threads.add(new TestThreadImpl("tghtgwf2"));

        threads.forEach(Thread::start);
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                System.out.println("Error en el thread: "+ e.getMessage());
            }
        });


        assertEquals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", threads.get(0).getRespuesta());
        assertEquals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", threads.get(1).getRespuesta());
        assertEquals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", threads.get(2).getRespuesta());
        assertEquals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", threads.get(3).getRespuesta());
        assertEquals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}", threads.get(4).getRespuesta());
    }

}
