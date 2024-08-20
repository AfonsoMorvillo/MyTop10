const rows = document.querySelectorAll('.row');

const onDragOver = (event) => {
    event.preventDefault();
}

const onDrop = (event) => {
    event.preventDefault();
    const draggedCardId = event.dataTransfer.getData('id');
    const draggedCard = document.getElementById(draggedCardId);

    const targetRow = event.target.closest('.row');
    const existingCard = targetRow.querySelector('.card');

    if (targetRow) {
        if (existingCard) {
            // Aplica a animação aos cards
            existingCard.classList.add('move');
            draggedCard.classList.add('move');

            // Troca os cards imediatamente
            const parentOfDraggedCard = draggedCard.parentNode;
            parentOfDraggedCard.appendChild(existingCard);
            targetRow.appendChild(draggedCard);

            // Remove a classe de animação após a transição
            setTimeout(() => {
                existingCard.classList.remove('move');
                draggedCard.classList.remove('move');
            }, 200); // Tempo da animação em ms (ajustado para 0.2s)
        } else {
            // Se não houver um card existente, apenas adiciona o card dragado
            draggedCard.classList.add('move');
            targetRow.appendChild(draggedCard);

            // Remove a classe de animação após a transição
            setTimeout(() => {
                draggedCard.classList.remove('move');
            }, 200); // Tempo da animação em ms (ajustado para 0.2s)
        }
    }
}

rows.forEach((row) => {
    row.ondragover = onDragOver;
    row.ondrop = onDrop;
});
