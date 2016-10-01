var map;
var markers = [];
var flightPath = [];
var bounds = new google.maps.LatLngBounds();
var routes = [];
var markerCluster;

function initMap() {  // Used to initialise everything to default
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 20, lng: 0},
        zoom: 2,
        <!--draggable: false,-->
        streetViewControl: false
    });
    map.setOptions({ minZoom: 1, maxZoom: 15 });
    markerCluster = new MarkerClusterer(map);
    deleteFlights();
}

function refreshMap(lat, lon) {  // Used to just refresh map
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: lat, lng: lon},
        zoom: 2,
        draggable: false,
        streetViewControl: false
    });
    map.setOptions({ minZoom: 1, maxZoom: 15 });
    markerCluster = new MarkerClusterer(map);
}

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
    markerCluster.clearMarkers();
}

function addFlight(lat, lon) {
    var marker = new google.maps.Marker({
        position: {lat: lat, lng: lon},
        map: map,
    });
    coord = new google.maps.LatLng({lat: lat, lng: lon});
    flightPath.push(coord);
    markers.push(marker);
    console.log(markers);
}

function makePath() {
    path = new google.maps.Polyline({
        path: flightPath,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    refreshMap(20, 0);
    path.setMap(map);
    markers[0].setMap(map);
    markers[markers.length - 1].setMap(map);
    repositionMap();
}

function repositionMap() {
    bounds = new google.maps.LatLngBounds(null);
    for (var i = 0; i < flightPath.length; i++) {
        bounds.extend(flightPath[i]);
    }
    map.fitBounds(bounds);
}

function addRoute(srcLat, srcLon, destLat, destLon) {
    var route = new google.maps.Polyline({
        path: [{lat: srcLat, lng: srcLon}, {lat: destLat, lng: destLon}],
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 0.5,
        strokeWeight: 0.5
    });
    routes = [];
    routes.push(route);
    route.setMap(map);
}

function makeOnePath(srcLat, srcLon, destLat, destLon) {
    path = new google.maps.Polyline({
        path: [{lat: srcLat, lng: srcLon}, {lat: destLat, lng: destLon}],
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    path.setMap(map);
    coord = new google.maps.LatLng({lat: srcLat, lng: srcLon});
    if (flightPath.indexOf(coord) == -1) {
        flightPath.push(coord);
    }
    coord = new google.maps.LatLng({lat: destLat, lng: destLon});
    if (flightPath.indexOf(coord) == -1) {
        flightPath.push(coord);
    }
}

function mapClusterer() {
    var options = {
            imagePath: 'images/m'
    };
    markerCluster = new MarkerClusterer(map, markers, options);
}