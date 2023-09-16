function mapBase(mapElemId, mapOption) {
    const map = new naver.maps.Map(mapElemId, mapOption);
    const mainInfoWindow = new naver.maps.InfoWindow();
    let aroundCircle;
    let markers = [];

    const successTemplate = `
        <div class="infoContainer" onclick="closeInfo()">
            <h2>위치를 찾았어요!</h2>
            <div class="ken ken--success"></div>
        </div>
    `

    const failedTemplate = `
        <div class="infoContainer" onclick="closeInfo()">
            <h2>위치를 알 수 없어요</h2>
            <div class="ken ken--failed"></div>
        </div>
    `
    const searchTemplate = `
        <div class="infoContainer" onclick="closeInfo()">
            <h2>이 주변 학교 찾기</h2>
            <div class="ken ken--search"></div>
            <md-filled-button onclick="addMarkers();">찾기</md-filled-button>
        </div>
    `

    function moveToCoordinate(coordinate) {
        const location = new naver.maps.LatLng(coordinate.latitude, coordinate.longitude);
        map.setCenter(location);
    }

    function showMarker(marker) {
        if (marker.setMap()) return;
        marker.setMap(map);
    }

    function hideMarker(marker) {
        if (!marker.setMap()) return;
        marker.setMap(null);
    }

    function getMarkerClickHandler(index) {
        return function (e) {
            const { marker, infoWindow } = markers[index];
            if (infoWindow.getMap()) {
                infoWindow.close();
            } else {
                infoWindow.open(map, marker);
            }
        }
    }

    function updateMarkers(markers) {
        const mapBounds = map.getBounds();
        for (let i = 0; i < markers.length; i++) {
            const {marker} = markers[i]
            const position = marker.getPosition();
            if (mapBounds.hasLatLng(position)) {
                showMarker(map, marker);
            } else {
                hideMarker(map, marker);
            }
        }
    }

    function addMarkers() {
        if (markers.length > 0) clearMarkers();

        // TODO: location 받기
        const bounds = map.getBounds(),
            southWest = bounds.getSW(),
            northEast = bounds.getNE(),
            lngSpan = northEast.lng() - southWest.lng(),
            latSpan = northEast.lat() - southWest.lat();

        for (let i = 0; i < 10; ++i) {
            // TODO : location 받기
            const position = new naver.maps.LatLng(
                southWest.lat() + latSpan * Math.random(),
                southWest.lng() + lngSpan * Math.random());

            const marker = new naver.maps.Marker({
                map: map,
                position: position,
                zIndex: 100
            });
            const infoWindow = new naver.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:10px;">b>"' + i + '"</b>.</div>'
            });
            naver.maps.Event.addListener(marker, 'click', getMarkerClickHandler(i));
            markers.push({marker, infoWindow});
        }
    }

    function clearMarkers () {
        for (let i=0, ii=markers.length; i<ii; ++i) {
            let {marker, infoWindow} = markers[i];
            naver.maps.Event.clearInstanceListeners(marker);
            marker.setMap(null);
            infoWindow.setMap(null);
        }
        markers = [];
    }

    function showAroundSchoolBoundary() {
        closeInfo();
        const center = map.getCenter();
        if (aroundCircle) {
            aroundCircle.setMap(null);
            aroundCircle = null;
            clearMarkers();
        }
        aroundCircle = new naver.maps.Circle({
            map: map,
            center: center,
            radius: 20000,
            fillColor: 'var(--md-ref-palette-primary90)',
            fillOpacity: 0.8,
        });
        mainInfoWindow.setContent(searchTemplate);
        mainInfoWindow.open(map, center);
    }

    function closeInfo() {
        mainInfoWindow.close();
    }

    function onSuccessGeolocation(position) {
        const location = new naver.maps.LatLng(position.coords.latitude, position.coords.longitude);
        map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
        map.setZoom(15); // 지도의 줌 레벨을 변경합니다.
        mainInfoWindow.setContent(successTemplate);
        mainInfoWindow.open(map, location);
        console.log('Coordinates: ' + location.toString());
    }

    function onErrorGeolocation() {
        const location = map.getCenter();
        mainInfoWindow.setContent(failedTemplate);
        mainInfoWindow.open(map, location);
    }

    function getGeolocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
        } else {
            onErrorGeolocation();
        }
    }

    function windowOnLoadHandler() {
        getGeolocation(map, mainInfoWindow);
        naver.maps.Event.addListener(map, 'idle', function () {
            updateMarkers(map, markers);
        });
    }

    window.onload = windowOnLoadHandler;

    return {
        getGeolocation,
        closeInfo,
        showAroundSchoolBoundary,
        addMarkers,
        clearMarkers,
        moveToCoordinate,
    };
}