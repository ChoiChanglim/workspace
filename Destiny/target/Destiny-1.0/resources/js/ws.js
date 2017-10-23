var ws;

function connect() {
	ws = new WebSocket('ws://10.77.32.185/websocket/open');
	ws.onopen = function() {
		//console.log('websocket opened');
	};
	ws.onmessage = function(message) {
		//console.log(message);
		//console.log('receive message : ' + message.data);
		var recieve = JSON.parse(message.data);
		//console.log(recieve.title+", "+recieve.body);
		notifyMe(recieve.title, recieve.body);
		// alert("메시지가 왔다.");
		$('#messages').val($('#messages').val() + recieve.body + '\n');
		/*document.getElementById('messages').scrollTop = document
				.getElementById('messages').scrollHeight;*/
	};
	ws.onclose = function(event) {
		// console.log(event);
		// console.log('websocket closed');
	};
}

function disconnect() {
	if (ws) {
		ws.close();
		ws = null;
	}
}
$(function() {
	connect();
	$('#connect').attr('disabled', true);
	/*
	 * $('#connect').click(function() { connect(); $(this).attr('disabled',
	 * true); $('#disconnect').removeAttr('disabled'); });
	 */

	$('#disconnect').click(function() {
		disconnect();
		$(this).attr('disabled', true);
		$('#connect').removeAttr('disabled');
	});

	$('#message').keydown(event, function() {
		if (event.keyCode === 13) {
			var msg = {
				"message" : $(this).val()
			};
			// console.log(JSON.stringify(msg));
			ws.send(JSON.stringify(msg));
			$(this).val('');
		}
	});
});

function notifyMe(title, body) {
	// Let's check if the browser support notifications
	if (!"Notification" in window) {
		alert("This browser does not support desktop notification");
	}

	// Let's check if the user is okay to get some notification
	else if (Notification.permission === "granted") {
		// If it's okay let's create a notification
		var title = title;
		var options = {
			body : body,
			icon : "http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"
		}
		var notification = new Notification(title, options);
		setTimeout(notification.close.bind(notification), 5000);
	}

	// Otherwise, we need to ask the user for it's permission
	// Note, Chrome does not implement the permission static property
	// So we have to check for NOT 'denied' instead of 'default'
	else if (Notification.permission !== 'denied') {
		Notification.requestPermission(function(permission) {

			// Whatever the user answers, we make sure Chrome store the
			// information
			if (!('permission' in Notification)) {
				Notification.permission = permission;
			}

			// If the user is okay, let's create a notification
			if (permission === "granted") {
				var notification = new Notification("Hi there!");
			}
		});
	}

	// At last, if the user already denied any notification, and you
	// want to be respectful there is no need to bother him any more.
}
