'strict'

const noteModal = document.getElementById('noteModal')
if (noteModal) {
  noteModal.addEventListener('show.bs.modal', event => {
    const relatedTarget = event.relatedTarget;
    let noteDescription = '';
    let noteTitle = '';
    let noteId = '';

    if(relatedTarget.classList.contains('btn-update')){
        noteId = relatedTarget.getAttribute('data-noteid');
        noteDescription = relatedTarget.closest('tr').querySelector('.note-description').textContent;
        noteTitle = relatedTarget.closest('tr').querySelector('.note-title').textContent;
    }

    document.getElementById('title-note').value = noteTitle;
    document.getElementById('description-note').value = noteDescription;
    document.getElementById('inputNoteid').value = noteId;
  });
}

const credentialModal = document.getElementById('credentialModal')
if (credentialModal) {
  credentialModal.addEventListener('show.bs.modal', event => {
    const relatedTarget = event.relatedTarget;
    let url = '', username = '', password = '', credentialId = '';

    if(relatedTarget.classList.contains('btn-update')){
        credentialId = relatedTarget.getAttribute('data-credentialid');
        url = relatedTarget.closest('tr').querySelector('.url-td').textContent;
        username = relatedTarget.closest('tr').querySelector('.username-td').textContent;
        password = relatedTarget.closest('tr').querySelector('.password-td').textContent;
    }

    document.getElementById('url-credential').value = url;
    document.getElementById('username-credential').value = username;
    document.getElementById('password-credential').value = password;
    document.getElementById('credential-id').value = credentialId;
  });
}

const hash = window.location.hash.substr(1);

const navTabEL = document.getElementById('nav-tab');
navTabEL.addEventListener('click', (event) => {
    const target = event.target;
    if(target.classList.contains('nav-link')){
        window.location.hash = target.getAttribute('data-hash');
    }
});

const navLinkEls = document.querySelectorAll('.nav-link');
for(let navLink of navLinkEls){
    const currentHash = navLink.getAttribute('data-hash');
    if(currentHash === hash){
        navLink.click();
        break;
    }
}