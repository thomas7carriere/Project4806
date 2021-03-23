// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

function drawPieChart(elementId, data) {
    // Create the data table.
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn('string', 'Option');
    dataTable.addColumn('number', 'Votes');
    dataTable.addRows(data);

    // Set chart options
    var options = {
        'width':400,
        'height':300};

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById(elementId));
    chart.draw(dataTable, options);
}

function drawHistogram(elementId, data) {
    // Create the data table.
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn('string', 'Number');
    dataTable.addColumn('number', 'Votes');
    dataTable.addRows(data);

    // Set chart options
    var options = {
        legend: { position: 'none' }};

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.Histogram(document.getElementById(elementId));
    chart.draw(dataTable, options);
}