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
			existingCard.classList.add('move');
			draggedCard.classList.add('move');

			const parentOfDraggedCard = draggedCard.parentNode;
			parentOfDraggedCard.appendChild(existingCard);
			targetRow.appendChild(draggedCard);

			setTimeout(() => {
				existingCard.classList.remove('move');
				draggedCard.classList.remove('move');
			}, 200);
		} else {
			draggedCard.classList.add('move');
			targetRow.appendChild(draggedCard);

			setTimeout(() => {
				draggedCard.classList.remove('move');
			}, 200);
		}
	}
}

rows.forEach((row) => {
	row.ondragover = onDragOver;
	row.ondrop = onDrop;
});
