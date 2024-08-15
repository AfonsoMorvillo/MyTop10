async function salvarTop10() {
    const buscarMusicaUrl = '/MyTop10/salvar'; // URL para o servlet
    const boardTop10 = document.querySelector('.board');
    
    const cards = boardTop10.querySelectorAll('.card');
    
    const cardData = [];
    
    cards.forEach(card => {
        const cardInfo = {
            id: card.id,
        };
        cardData.push(cardInfo);
    });
    
    const data = JSON.stringify(cardData);
    
    try {
        const response = await fetch(buscarMusicaUrl, { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Definindo o tipo de conteúdo como JSON
            },
            body: data 
        });
        
        if (response.ok) {
            console.log('Top 10 adicionado com sucesso');
        } else {
            console.error('Erro ao adicionar o Top 10:', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao enviar os dados:', error);
    }
}


