var animation = bodymovin.loadAnimation({
    container: document.getElementById('pink-cat'), // Required
    path: '/main/res/assets/pink-cat.json', // Required
    renderer: 'svg', // Required
    loop: true, // Optional
    autoplay: true, // Optional
    name: "Welcome to My Michi!", // Name for future reference. Optional.
  }) 