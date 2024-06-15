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

function updatePage(data) {
    document.title = data.Title;
    $('#title').text(data.Title);
    $('#release').text(data.Released);
    $('#country').text(data.Country);
    $('#director').text(data.Director);
    $('#genre').text(data.Genre);
    $('#language').text(data.Language);
    $('#runtime').text(data.Runtime);
    $('#plot').text(data.Plot);
    $('#poster').attr('src', data.Poster);
}

function askOMDB(title, year) {
    const key = 'a34f625';
    
    const omdbAPI = new XMLHttpRequest();
    const omdbURL = `http://www.omdbapi.com/?apikey=${key}&t=${title}&y=${year}`;
    omdbAPI.open("get", omdbURL, true);
    omdbAPI.onload = function(event) {
      event.preventDefault();
      if (this.status == 200) {
        var result = JSON.parse(this.responseText);
        updatePage(result);
      } else {
        alert(`Could not find a movie with ${title} (${year})`);
      }
    }
  
    omdbAPI.send();
}

function renderMetadata() {
    const movieId = $('#movie').data('movieid');
    const urlapi = `http://localhost:8080/movies/${movieId}`;
    console.log('Movie Metadata');
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            const data = parseTitle(response.title);
            const title = data.name;
            const year = data.year;
            console.log(data, title, year);

            askOMDB(title, year);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

function renderSmartPlot() {
    const movieId = $('#movie').data('movieid');
    const urlapi = `http://localhost:8080/ratings/summary/${movieId}`;
    console.log('Smart Plot');
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            bar.initData(response);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
    $('#c1').on('change', bar.changeGender);
    $('#c2').on('change', bar.changeGender);
    $('#c3').on('change', bar.changeAge);
    $('#c4').on('change', bar.changeAge);
    $('#c5').on('change', bar.changeAge);
}

const bar = new barChart();

$(document).ready(function() {
    renderMetadata();
    renderSmartPlot();
    // Rendering BarChart
});
