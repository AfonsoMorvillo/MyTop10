const buscarMusicaUrl = '/MyTop10/musica/buscar';

async function adicionarMusica() {
    const input = document.getElementById('musica');
    const textoMusica = input.value.trim();

    if (textoMusica === '') {
        alert('Por favor, insira o nome da música.');
        return;
    }

    const cards = document.querySelectorAll('.card');
    const nextId = cards.length + 1; // Incrementa o número de cards existentes

    // Configura os parâmetros da requisição
    const params = new URLSearchParams();
    params.set('musicaBusca', textoMusica);

    try {
        // Faz a requisição GET para o servlet
        const response = await fetch(`${buscarMusicaUrl}?${params.toString()}`, {
            method: 'GET'
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error('Erro ao buscar música');
        }

        // Obtém a resposta em formato JSON
        const data = await response.json();

        // Cria uma nova div para a música encontrada
        const container = document.getElementsByClassName('musicas')[0];
        const novaDiv = document.createElement('div');
        
        novaDiv.className = 'card';
        novaDiv.id = `musica-${nextId}`;
        novaDiv.draggable = true; // Torna a div arrastável
        novaDiv.dataset.musicId = data.id;

        novaDiv.ondragstart = onDragStart;
        novaDiv.ondragend = onDragEnd;

        const cardImg = document.createElement('img');
        cardImg.src = data.capa;
        cardImg.alt = 'Capa da música';
        cardImg.className = 'card-img';
        cardImg.draggable = false

        const cardInfo = document.createElement('div');
        cardInfo.className = 'card-info';

        const cardTitle = document.createElement('div');
        cardTitle.textContent = data.nome;
        cardTitle.className = 'card-title';

		const album = document.createElement('div');
		album.textContent = `${data.album} • ${data.artista}`;
		album.className = 'card-album';


        cardInfo.appendChild(cardTitle);
        cardInfo.appendChild(album);

        novaDiv.appendChild(cardImg);
        novaDiv.appendChild(cardInfo);

        container.appendChild(novaDiv);

        // Limpa o campo de input
        input.value = '';
        
    } catch (error) {
        console.error('Erro:', error);
    }
}
