async function salvarTop10() {
	const buscarMusicaUrl = 'salvar';

	const urlParts = window.location.pathname.split('/');
	const id = urlParts[urlParts.length - 1];

	const cardData = {
		top10Id: id,
		items: []
	};

	rows.forEach((row, index) => {
		const card = row.querySelector('.card');
		if (card) {
			const musicId = card.dataset.musicId;

			const cardInfo = {
				id: musicId,
				ordem: index + 1
			};
			cardData.items.push(cardInfo);
		}
	});

	const data = JSON.stringify(cardData);

	try {
		const response = await fetch(buscarMusicaUrl, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: data
		});


		if (response.ok) {
			console.log('Top 10 adicionado com sucesso');
		} else {
			console.error('Erro ao adicionar o Top 10:', response.statusText);
		}
	} catch (error) {
	}
}
async function salvarSessao() {
	const buscarMusicaUrl = 'salvarSessao';

	try {
		const response = await fetch(buscarMusicaUrl, {
			method: 'POST',
		});


		if (response.ok) {
			console.log('Sessao salva');
		} else {
			console.error('Erro salvar sessao');
		}
	} catch (error) {
	}
}
