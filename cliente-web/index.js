const contenedorCodigo = document.getElementById("info-recurso");
const contenedorImagen = document.getElementById("info-imagen");
const imgServer = document.getElementById("image-fromserver");

async function obtenerInfoRecurso(){
    const extensionRecurso = document.getElementById("extensionRecurso");
    

    if( extensionRecurso.value != '' && extensionRecurso.value != undefined ){

        const extensionRecurso4 = document.getElementById("extensionRecurso4");

        if(extensionRecurso4.value == 'Si'){
            // Redireccionar a una página web
            window.location.href = "http://localhost:35000/" + extensionRecurso.value;
        }

        const url = "http://localhost:35000/"+extensionRecurso.value;
        const respuesta = await fetch(url, {
            method: "GET"
        });
        const infoRecurso = await respuesta.json();

        let contenedorInfo = ""; 
        if( infoRecurso.resource === 'png' || infoRecurso.resource === 'jpg' ){
            contenedorInfo += `
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <p class="card-text">1</p>
                                <p class="card-text">2</p>
                                <span class="badge bg-secondary">3</span></h6>
                            </div>
                        </div>`         
            imgServer.setAttribute('src', "data:image/jpg;base64," + infoRecurso.body);
            contenedorCodigo.innerHTML = '';
        } else if( infoRecurso.resource === 'html' ){
            contenedorCodigo.innerHTML = infoRecurso.body;
            imgServer.removeAttribute('src');
        } else if( infoRecurso.resource === 'css' || infoRecurso.resource === 'js' ){
            contenedorInfo += `
                        <div class="container" style="background: white; color: black;">
                            <div class="container">
                                <p class="card-text">${infoRecurso.body}</p>
                            </div>
                        </div>`
            contenedorCodigo.innerHTML = contenedorInfo;
            imgServer.removeAttribute('src');
        }else{
            contenedorInfo += `
            <div class="container" style="background: white; color: black;">
                <div class="container">
                    <p class="card-text">NO SE ENCONTRO EL RECURSO.ESTO ES SOLO UN MENSAJE DE AVISO</p>
                </div>
            </div>`
    contenedorCodigo.innerHTML = contenedorInfo;
    imgServer.removeAttribute('src');
        }
        
    } else {
        alert('Para buscar la informacíón de la pelicula, escriba en el input')
    }
}


async function agregarRecurso() {
    const extensionRecurso2 = document.getElementById("extensionRecurso2");
    const extensionRecurso3 = document.getElementById("extensionRecurso3");

    console.log(extensionRecurso3.value);

    if (extensionRecurso2.value !== '' && extensionRecurso2.value !== undefined) {
        let url = "http://localhost:35000/" + extensionRecurso2.value.trim();

        if (extensionRecurso3.value !== '') {
            const direccionArchivo = extensionRecurso3.value.trim();
            url += "?direccionArchivo=" + direccionArchivo;
            console.log("HOLA ENGRE");
        }

        console.log(url);
        
        try {
            const respuesta = await fetch(url, {
                method: "POST"
            });
            
            if (respuesta.ok) {
                // Manejar la respuesta exitosa aquí
                const data = await respuesta.json();
                console.log("Respuesta exitosa:", data);
            } else {
                // Manejar errores de respuesta aquí
                console.error("Error en la respuesta:", respuesta.status, respuesta.statusText);
            }
        } catch (error) {
            // Manejar errores de red o excepciones aquí
            console.error("Error en la solicitud:", error);
        }
    } else {
        // Manejar caso en que extensionRecurso.value está vacío o no definido
        console.error("La extensión del recurso no es válida");
    }
}


const button = document.getElementById("btnBuscarRecurso");

button.addEventListener("click", obtenerInfoRecurso);

const button2 = document.getElementById("agregarRecurso");

button2.addEventListener("click", agregarRecurso);