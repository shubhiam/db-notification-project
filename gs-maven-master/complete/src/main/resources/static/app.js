var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    $("#dbList").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).serviceList);
        });
        stompClient.subscribe('/topic/dbList', function (dbList) {
            showDBList(JSON.parse(dbList.body).dbChangeList);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function getDBChanges() {
	console.log("get db changes");
    stompClient.send("/app/getDBChanges", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
	console.log('show greeting : ' + message.length);
	for(var i = 0; i < message.length; i++) {
		$("#greetings").append("<tr><td>" + message[i] + "</td></tr>");
	}
}

function showDBList(message) {
	console.log('Show db change list : ' + message.length);
	for(var i = 0; i < message.length; i++) {
		$("#dbList").append("<tr><td>" + message[i] + "</td></tr>");
	}
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#dbChanges" ).click(function() { getDBChanges(); });
});

