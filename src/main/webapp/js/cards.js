const cards = document.querySelectorAll('.card');


const onDragStart = (event) => {
  event.dataTransfer.setData('id', event.target.id)
  setTimeout(()=>{
    event.target.style.visibility = 'hidden'
  })
}

const onDragEnd = (event) => {
     event.target.style.visibility = 'visible'
}

cards.forEach((card)=>{
    card.ondragstart = onDragStart;
    card.ondragend = onDragEnd;
})