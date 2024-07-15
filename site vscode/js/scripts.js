

window.addEventListener('DOMContentLoaded', event => {

    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            rootMargin: '0px 0px -40%',
        });
    };

    

    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});




document.getElementById('randomBtn').addEventListener('click', function() {
    fetch('ficheiro.json')
        .then(response => response.json())

        //o then data recebe os dados do json resolvidos 
        .then(data => {

            //extrai a lista de utilizadores do objeto json
            const utilizadores = data.utilizadores;

            //gera um numero aleatorio entre 0 e 1
            const randomIndex = Math.floor(Math.random() * utilizadores.length);

            //seleciona um utilizador da lista
            const randomUser = utilizadores[randomIndex];

            //atualiza o elemento para exibir o nome do utilizador e o comentario
            document.getElementById('randomNome').innerHTML = randomUser.nome;
            document.getElementById('randomComentario').innerHTML = randomUser.comentarios;
        })

        //vê se acontece algum erro durante
        .catch(error => console.error('Erro ao carregar o JSON:', error));
});



