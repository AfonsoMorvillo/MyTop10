const buscarMusicaUrl = '/MyTop10/musica/buscar'

async function adicionarMusica() {
	const input = document.getElementById('musica');
	const textoMusica = input.value.trim();

	if (textoMusica === '') {
		alert('Por favor, insira o nome da música.');
		return;
	}

	const cards = document.querySelectorAll('.card');
	const nextId = cards.length + 1; // Incrementa o número de cards existentes

	const buscarMusicaUrl = '/MyTop10/musica/buscar'; // URL para o servlet

	// Configura os parâmetros da requisição
	const params = new URLSearchParams();
	params.set("musicaBusca", textoMusica);

	try {
		// Faz a requisição GET para o servlet
		const response = await fetch(`${buscarMusicaUrl}?${params.toString()}`, {
			method: 'GET'
		});

		// Verifica se a resposta foi bem sucedida
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

		novaDiv.ondragstart = onDragStart;
		novaDiv.ondragend = onDragEnd;


		const cardImg = document.createElement('img');
		cardImg.src = data.capa
		cardImg.alt = 'teste'
		cardImg.className = 'card-img'

		const cardTitle = document.createElement('div');
		cardTitle.textContent = data.nome;
		cardTitle.className = 'card-title'

		novaDiv.appendChild(cardImg)
		novaDiv.appendChild(cardTitle)

		container.appendChild(novaDiv);
		// Limpa o campo de input
		input.value = '';

	} catch (error) {
		console.error('Erro:', error);
	}
}

