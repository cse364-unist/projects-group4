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
    if (data.totalRatings == 1) 
        $(`#recap1`).text("1 movie was rated for the period given!");
    else 
        $(`#recap1`).text(data.totalRatings + " movies was rated for the period given!");

    $(`#recap2`).text(data.top3genre + " were your top genres!");
    
    const urlapi = `/movies/`+data.mostPopularMovieId;
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            console.log(typeof(response.title));
            $(`#recap3`).text('\"' + parseTitle(response.title).name + "\" was favourite of many!");
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
            $(`#recap3`).text("Some error occurred...");
        }
    });
}

function genRecap(deltaTime) {
    const userId = '1'; // $('#info').data('userid');
    const currentTimeInSeconds = Math.floor(Date.now()/1000);
    const dTime = Number(deltaTime) * 24 * 60 * 60;
    const urlapi = `/recap/`+userId+`/?currentTime=`+currentTimeInSeconds+`&deltaTime=`+dTime;
    
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            updatePage(response);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

const button = document.getElementById('recapBtn');

button.addEventListener('click', function() {
    const deltaTime = document.getElementById('deltaTimeInput').value;
    
    genRecap(deltaTime);   
});