const navbar = document.getElementsByClassName("form-inline my-2 my-lg-0 navbar-left")[0];
navbar.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission

    const titleInput = document.getElementsByClassName('form-control mr-sm-2')[0].value;
    const searchUrl = `/search?title=${encodeURIComponent(titleInput)}`;

    window.location.href = searchUrl; // Redirect to the search URL
});