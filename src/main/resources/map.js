/**
 * Created by jar156 on 11/09/16.
 */

var map;
var markers = [];
var polylines = [];

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 20, lng: 0},
        zoom: 2
    });
}

function hideAllAirports() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

function hideAllRoutes() {
    for (var i = 0; i < polylines.length; i++) {
        polylines[i].setMap(null);
    }
}

function addAirport(late, lone) {
    var marker = new google.maps.Marker({
        position: {lat: late, lng: lone},
        map: map,
        title: 'Hello World!'
    });
    markers.push(marker);
}

// creates and displays markers for all airports
function showAllAirports()
{
    var marker = new google.maps.Marker({
        position: {lat: -20, lng: 31.044},
        map: map,
        title: 'Hello World!'
    });
    markers.push(marker);
};

// creates and displays lines for all routes
function showAllRoutes() {
    var flightPlanCoordinates = [
        {lat: 37.772, lng: -122.214},
        {lat: -27.467, lng: 153.027}
    ];
    var flightPath = new google.maps.Polyline({
        path: flightPlanCoordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 1
    });

    flightPath.setMap(map);
    polylines.push(flightPath);
    //flightPath.setMap(null); to make a route invisible
}