const contenedorPelicula = document.getElementById("info-pelicula");
const imgServer = document.getElementById("image-fromserver");

async function obtenerInfoPelicula(){
    const nombrePelicula = document.getElementById("nombrePelicula");

    if( nombrePelicula.value != '' && nombrePelicula.value != undefined ){
        const url = "http://localhost:35000/?resource="+nombrePelicula.value;
        const respuesta = await fetch(url, {
            method: "GET"
        });
        const infoPeliculas = await respuesta.json();

        let contenedorInfo = "";
        if( infoPeliculas.resource === 'png' || infoPeliculas.resource === 'jpg' ){
            contenedorInfo += `
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <p class="card-text">1</p>
                                <p class="card-text">2</p>
                                <span class="badge bg-secondary">3</span></h6>
                            </div>
                        </div>`
            imgServer.setAttribute('src', "data:image/jpg;base64," + infoPeliculas.body);
        } else if( infoPeliculas.resource === 'html' ){
            contenedorPelicula.innerHTML = infoPeliculas.body;
        } else if( infoPeliculas.resource === 'css' || infoPeliculas.resource === 'js' ){
            contenedorInfo += `
                        <div class="container" style="background: white; color: black;">
                            <div class="container">
                                <p class="card-text">${infoPeliculas.body}</p>
                            </div>
                        </div>`
            contenedorPelicula.innerHTML = contenedorInfo;
        }

    } else {
        alert('Para buscar la informacíón de la pelicula, escriba en el input')
    }
}

const button = document.getElementById("btnBuscarPelicula");

button.addEventListener("click", obtenerInfoPelicula)