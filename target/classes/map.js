/**
 * Created by jar156 on 11/09/16.
 */

var map;
var markerCluster;
var markers = [];

function initMap() {

    map = new google.maps.Map(document.getElementById('map'), {

        center: {lat: 20, lng: 0},

        zoom: 5

    });

}

function deleteFlights() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// function hiderer() {
//     for (var i = 0; i < markers.length; i++) {
//         markers[i].setMap(null);
//     }
// }

function addFlight(late, lone) {
    var marker = new google.maps.Marker({
        position: {lat: late, lng: lone},
        map: map,
    });
    markers.push(marker);
}
//
// function addAirport2() {
//     var marker = new google.maps.Marker({
//         position: {lat: 20, lng: 0},
//         map: map,
//     });
//     markers.push(marker);
// }

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
};

