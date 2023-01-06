'use strict';

angular.module('myevent', [
    'angular-input-stars'
])
    .factory('EventService', EventService)
    .controller('EventsController', EventsController);

function EventService($http) {
    return {
        deleteEvent: deleteEvent,
        getEvents: getEvents,
        updateStars: updateStars,
        addEvent: addEvent,
    };

    function deleteEvent(id) {
        return $http.delete('/api/events/' + id);
    }

    function addEvent(data) {
        return $http.post('/api/events', data);
    }

    function getEvents() {
        return $http.get('/api/events/')
            .then(getEventsComplete);

        function getEventsComplete(response) {
            return response.data;
        }
    }

    function updateStars(event) {
        return $http.put('/api/events/' + event.id, event);
    }
}

function EventsController(EventService) {
    var vm = this;
    vm.deleteEvent = deleteEvent;
    vm.updateStars = updateStars;

    activate();

    function activate() {
        return EventService.getEvents()
            .then(function (events) {
                vm.events = events;
                return vm.events;
            });
    }

    function deleteEvent(event) {
        EventService.deleteEvent(event.id)
            .then(function (res) {
                var index = vm.events.indexOf(event);
                vm.events.splice(index, 1);
            });

    }

    function addEvent(event) {
        EventService.addEvent(event)
            .then(function (res) {
                activate();
            });

    }

    function updateStars(event) {
        return EventService.updateStars(event);
    }

    var isVisible = true;

    function showHide() {
        isVisible = !isVisible;
        console.log(isVisible);
    }
}
