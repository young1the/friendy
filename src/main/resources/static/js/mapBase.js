function mapBase(mapElemId, mapOption) {
    const map = new naver.maps.Map(mapElemId, mapOption);
    const mainInfoWindow = new naver.maps.InfoWindow();
    let aroundCircle;
    let markers = [];

    const successTemplate = `
        <div class="infoContainer" onclick="closeInfo()">
            <h2>위치를 찾았어요!</h2>
            <div class="ken ken--success"></div>
            <md-filled-button onclick="addMarkers();">알겠어!</md-filled-button>
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
            <md-filled-button onclick="getAroundSchool();">찾기</md-filled-button>
        </div>
    `

    const schoolTemplate = (school) => {
        const {name, idx, road_address, level_code} = school;
        const tag = +level_code === 2 ? "a" : "h4";
        return `
            <div style="padding: 16px;">
                <${tag} style="font-weight: bold; font-size: 20px" href="/school/user/${idx}">${name}</${tag}>
                <p>${road_address}</p>
            </div>
        `;
    }

    function moveToCoordinate(latitude, longitude) {
        const location = new naver.maps.LatLng(latitude, longitude);
        map.setZoom(15);
        map.setCenter(location);
    }

    function showMarker(marker) {
        if (marker) {
            marker.setMap(map);
        }
    }

    function hideMarker(marker) {
        if (marker) {
            marker.setMap(null);
        }
    }

    function getMarkerClickHandler(marker, infoWindow) {
        return function (e) {
            if (infoWindow.getMap()) {
                infoWindow.close();
            } else {
                infoWindow.open(map, marker);
            }
        }
    }

    function updateMarkers() {
        const mapBounds = map.getBounds();
        for (let i = 0; i < markers.length; i++) {
            const {marker} = markers[i]
            const position = marker.getPosition();
            if (mapBounds.hasLatLng(position)) {
                showMarker(marker);
            } else {
                hideMarker(marker);
            }
        }
    }


    async function getAroundSchool () {
        const center = map.getCenter();
        const body = {
            latitude: center.lat(),
            longitude: center.lng(),
        }
        const queryString = new URLSearchParams(body).toString();
        const response = await fetch(`/aroundSchool?${queryString}`, {
            headers: {
                'Content-Type': 'application/json'
            },
        });
        const schoolList = await response.json();
        addMarkers(schoolList);
    }

    function addMarkers(schoolList) {
        if (markers.length > 0) {
            clearMarkers();
        }
        if (!schoolList || schoolList.length === 0) {
            return;
        }
        for (const school of schoolList) {
            const {name, idx, latitude, longitude, road_address, road_zip_code, level_code} = school;
            const position = new naver.maps.LatLng(latitude, longitude);
            const marker = new naver.maps.Marker({
                map: map,
                position: position,
                zIndex: 100
            });
            const infoWindow = new naver.maps.InfoWindow({
                content: schoolTemplate(school),
            });
            naver.maps.Event.addListener(marker, 'click', getMarkerClickHandler(marker, infoWindow));
            markers.push({marker, infoWindow});
        }
        updateMarkers();
    }

    function clearMarkers() {
        for (let i = 0, ii = markers.length; i < ii; ++i) {
            let {marker, infoWindow} = markers[i];
            naver.maps.Event.clearInstanceListeners(marker);
            marker.setMap(null);
            infoWindow.setMap(null);
        }
        markers = [];
    }

    function closeAroundCircle () {
        if (aroundCircle) {
            aroundCircle.setMap(null);
            aroundCircle = null;
            clearMarkers();
        }
    }

    function showAroundSchoolBoundary() {
        closeInfo();
        const center = map.getCenter();
        closeAroundCircle();
        aroundCircle = new naver.maps.Circle({
            map: map,
            center: center,
            radius: 3000,
            fillColor: 'var(--md-ref-palette-primary90)',
            fillOpacity: 0.8,
        });
        mainInfoWindow.setContent(searchTemplate);
        mainInfoWindow.open(map, center);
    }

    function hideAroundSchoolBoundary() {
        closeInfo();
        if (aroundCircle) {
            aroundCircle.setMap(null);
            aroundCircle = null;
        }
    }

    function closeInfo() {
        if (mainInfoWindow) mainInfoWindow.close();
    }

    function onSuccessGeolocation(position) {
        hideAroundSchoolBoundary();
        const location = new naver.maps.LatLng(position.coords.latitude, position.coords.longitude);
        map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
        map.setZoom(15); // 지도의 줌 레벨을 변경합니다.
        mainInfoWindow.setContent(successTemplate);
        mainInfoWindow.open(map, location);
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
            updateMarkers();
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
        getAroundSchool,
        closeAroundCircle,
    };
}