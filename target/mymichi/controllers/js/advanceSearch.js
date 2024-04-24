const openButton = document.getElementById('openAdvancePublishContent');
const popup = document.getElementById('advancePublishContent');
const closeButton = document.getElementById('closeAdvancePublishContent');

function showAdvancePublishContent() {
    var advancePublishContent = document.getElementById('advancePublishContent');
    advancePublishContent.classList.add('showAdvancePublishContent');
}

function closeAdvancePublishContent() {
    var advancePublishContent = document.getElementById('advancePublishContent');
    advancePublishContent.classList.remove('showAdvancePublishContent');
}


document.getElementById('openAdvancePublishContent').addEventListener('click', function() {
    showAdvancePublishContent();
});

document.getElementById('closeAdvancePublishContent').addEventListener('click', function() {
    closeAdvancePublishContent();
});