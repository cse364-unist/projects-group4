function nameToId(name) {
    if (name == 'ReviewOne')
        return 'reviewone';
    if (name == 'ReviewTen')
        return 'reviewten';
    if (name == 'BigFiveOne')
        return 'bigfiveone';
    if (name == 'BigFiveTen')
        return 'bigfiveten';
    return null;
}

function updatePage(data) {
    const name = nameToId(data.id.achievement);
    const progress = parseFloat(data.progress);
    const date = new Date(data.timestamp * 1000);
    var msg = `Your current progress is ${progress}%`;
    if (progress == 100)
        msg = `You have finished it on ${date}`;
    console.log(name, msg);
    if (name != null)
        $(`#${name}`).text(msg);
}

function renderMetadata() {
    if (!('userId' in localStorage))
        localStorage['userId'] = 'ddsadsa';
    const userId = localStorage['userId'];
    const urlapi = `/achievements`;
    console.log('Movie Metadata');
    $.ajax({
        url: urlapi,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(response) {
            console.log("Success:", response);
            const filteredData = response.filter((d) => d.id.userId == userId);
            filteredData.forEach(updatePage);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

$(document).ready(function() {
    const userId = $('#info').data('userid');
    if (userId != null)
        localStorage['userId'] = userId;
    renderMetadata();
});
