document.addEventListener("DOMContentLoaded", function() {

    document.getElementById('openCreatePublishContent').addEventListener('click', function() {
        document.getElementById('createPublishContent').style.display = 'block';
    });

    document.getElementById('closeCreatePublishContent').addEventListener('click', function() {
        document.getElementById('createPublishContent').style.display = 'none';
    });
});