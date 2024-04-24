const searchInput = document.getElementById('searchInput');
const searchResults = document.getElementById('searchResults');


searchInput.addEventListener('click', () => {
    searchResults.style.display = 'flex';
  });
  
  document.addEventListener('click', (event) => {
    if (!searchResults.contains(event.target) && event.target !== searchInput) {
      searchResults.style.display = 'none';
    }
  });