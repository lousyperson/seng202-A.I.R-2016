/**
 * Created by jar156 on 11/09/16.
 */

var map;
// var markerCluster;
var markers = [];
var flightPath = [];
var bounds = new google.maps.LatLngBounds();

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 20, lng: 0},
        zoom: 1,
        draggable: false
    });
}

//function panToMap(middle) {
//    map = new google.maps.Map(document.getElementById('map'), {
//        center: middle,
//        zoom: 3
//    });
//}


function off() {
    // map.panTo(lastValidCenter)
    map.setOptions({
        draggable: false
    });
}

function on() {
    // map.panTo(lastValidCenter);
    map.setOptions({
        draggable: true
    });
}

function hideFlights() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

function deleteFlights() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
    flightPath = [];
}

function addFlight(late, lone) {
    var marker = new google.maps.Marker({
        position: {lat: late, lng: lone},
        map: map,
    });
    coord = new google.maps.LatLng({lat: late, lng: lone});
    flightPath.push(coord);
    markers.push(marker);
}

function makePath() {
    path = new google.maps.Polyline({
        path: flightPath,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    initMap();
    path.setMap(map);
    markers[0].setMap(map);
    markers[markers.length - 1].setMap(map);
    repositionMap(flightPath);
}

function repositionMap(flightPath) {
    bounds = new google.maps.LatLngBounds(null);
    for (var i = 0; i < flightPath.length; i++) {
        bounds.extend(flightPath[i]);
    }
    map.fitBounds(bounds);
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
    markerCluster = new MarkerClusterer(map, markers);

}


