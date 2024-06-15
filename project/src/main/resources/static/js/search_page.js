function parseTitle(title) {
    const regex1 = /^([\w\s,.'-]+?), The *\((\d{4})\)$/;
    const regex2 = /^([\w\s,.'-]+?)\s*\((\d{4})\)$/;

    var match = title.match(regex1);
    if (match) {
        const name = "The" + match[1].trim();
        const year = parseInt(match[2]);
        return {name, year};
    }
    match = title.match(regex2);
    if (match) {
        const name = match[1].trim();
        const year = parseInt(match[2]);
        return {name, year};
    }
    return null;
}

function updatePage(data, i) {
    $(`#poster${i}`).attr('src', data.Poster);
}

function askOMDB(title, year, i) {
    const key = 'a34f625';
    
    const omdbAPI = new XMLHttpRequest();
    const omdbURL = `http://www.omdbapi.com/?apikey=${key}&t=${title}&y=${year}`;
    omdbAPI.open("get", omdbURL, true);
    omdbAPI.onload = function(event) {
      event.preventDefault();
      if (this.status == 200) {
        var result = JSON.parse(this.responseText);
        updatePage(result, i);
      } else {
        alert(`Could not find a movie with ${title} (${year})`);
      }
    }
  
    omdbAPI.send();
}

function renderMetadata() {
    const movieTitle = $('#search').data('title');
    const urlapi = `http://localhost:8080/movies`;
    console.log('Movie Metadata');
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            const movies = response.filter((d) => d.title.includes(movieTitle));
            for (var i = 0; i < 3 && i < movies.length; i += 1) {
                const dd = parseTitle(movies[i].title);
                askOMDB(dd.name, dd.year, i + 1);
                $(`#a${i+1}`).attr('href', `/moviepage/?movieId=${movies[i].id}`);
            }
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

$(document).ready(function() {
    renderMetadata();
});
