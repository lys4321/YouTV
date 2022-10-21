var server = "http://192.168.0.141:8088/janus";

var janus = null;
var screentest = null;
var opaqueId = "screensharingtest-"+Janus.randomString(12);

var myusername = sessionStorage.getItem("userid");
var myid = null;

var capture = null;
var role = null;
var room = null;
var source = null;

var spinner = null;

function checkEnterShare(field, event) {
	var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if(theCode == 13) {
		preShareScreen();
		return false;
	} else {
		return true;
	}
}

function preShareScreen() {
	if(!Janus.isExtensionEnabled()) {
		bootbox.alert("You're using Chrome but don't have the screensharing extension installed: click <b><a href='https://chrome.google.com/webstore/detail/janus-webrtc-screensharin/hapfgfdkleiggjjpfpenajgdnfckjpaj' target='_blank'>here</a></b> to do so", function() {
			window.location.reload();
		});
		return;
	}
	// Create a new room
	$('#desc').attr('disabled', true);/////////////
	$('#create').attr('disabled', true).unbind('click');
	$('#roomid').attr('disabled', true);///////////////////////////
	$('#join').attr('disabled', true).unbind('click');
	if($('#desc').val() === "") {///////////////
		bootbox.alert("Please insert a description for the room");
		$('#desc').removeAttr('disabled', true);
		$('#create').removeAttr('disabled', true).click(preShareScreen);
		$('#roomid').removeAttr('disabled', true);////////////////////////////
		$('#join').removeAttr('disabled', true).click(joinScreen);
		return;
	}
	capture = "screen";
	if(navigator.mozGetUserMedia) {
		// Firefox needs a different constraint for screen and window sharing
		bootbox.dialog({
			title: "Share whole screen or a window?",
			message: "Firefox handles screensharing in a different way: are you going to share the whole screen, or would you rather pick a single window/application to share instead?",
			buttons: {
				screen: {
					label: "Share screen",
					className: "btn-primary",
					callback: function() {
						capture = "screen";
						shareScreen();
					}
				},
				window: {
					label: "Pick a window",
					className: "btn-success",
					callback: function() {
						capture = "window";
						shareScreen();
					}
				}
			},
			onEscape: function() {
				$('#desc').removeAttr('disabled', true);///////////
				$('#create').removeAttr('disabled', true).click(preShareScreen);
				$('#roomid').removeAttr('disabled', true);/////////////////////////////////////
				$('#join').removeAttr('disabled', true).click(joinScreen);
			}
		});
	} else {
		shareScreen();
	}
}

function shareScreen() {
	// Create a new room
	var desc = "1234"//$('#desc').val();///////////////
	role = "publisher";
	var create = {
		request: "create",
		description: desc,
		bitrate: 500000,
		publishers: 1
	};
	screentest.send({ message: create, success: function(result) {
		var event = result["videoroom"];
		Janus.debug("Event: " + event);
		if(event) {
			// Our own screen sharing session has been created, join it
			room = result["room"];
			console.log("1번 : "+room);//////////////////////////
			Janus.log("Screen sharing session created: " + room);
			var register = {
				request: "join",
				room: room,
				ptype: "publisher",
				display: myusername
			};
			console.log("2번 : "+register);//////////////////////////
			screentest.send({ message: register });
		}
	}});
}

function checkEnterJoin(field, event) {
	var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if(theCode == 13) {
		joinScreen();
		return false;
	} else {
		return true;
	}
}

function joinScreen() {
	// Join an existing screen sharing session
	$('#desc').attr('disabled', true);
	//$('#create').attr('disabled', true).unbind('click');
	$('#roomid').attr('disabled', true);///////////////////////////////////
	$('#join').attr('disabled', true).unbind('click');
	var roomid = "1234"//$('#roomid').val();/////////////////////////
	if(isNaN(roomid)) {//????????????????????????????????????????
		bootbox.alert("Session identifiers are numeric only");
		$('#desc').removeAttr('disabled', true);
		$('#create').removeAttr('disabled', true).click(preShareScreen);
		$('#roomid').removeAttr('disabled', true);////////////////////////
		$('#join').removeAttr('disabled', true).click(joinScreen);
		return;
	}
	room = sessionStorage.getItem("video_code")//parseInt(roomid);//여기가 방 들어오는 세션 생성하는 부분(나중에 방송코드로 변경){일단은 병경했음}
	role = "listener";
	var register = {
		request: "join",
		room: room,
		ptype: "publisher",
		display: myusername
	};
	screentest.send({ message: register });
}

function newRemoteFeed(id, display) {
	// A new feed has been published, create a new plugin handle and attach to it as a listener
	source = id;
	var remoteFeed = null;
	janus.attach(
		{
			plugin: "janus.plugin.videoroom",
			opaqueId: opaqueId,
			success: function(pluginHandle) {
				remoteFeed = pluginHandle;
				Janus.log("Plugin attached! (" + remoteFeed.getPlugin() + ", id=" + remoteFeed.getId() + ")");
				Janus.log("  -- This is a subscriber");
				// We wait for the plugin to send us an offer
				var listen = {
					request: "join",
					room: room,
					ptype: "listener",
					feed: id
				};
				remoteFeed.send({ message: listen });
			},
			error: function(error) {
				Janus.error("  -- Error attaching plugin...", error);
				bootbox.alert("Error attaching plugin... " + error);
			},
			onmessage: function(msg, jsep) {
				Janus.debug(" ::: Got a message (listener) :::", msg);
				var event = msg["videoroom"];
				Janus.debug("Event: " + event);
				if(event) {
					if(event === "attached") {
						// Subscriber created and attached
						if(!spinner) {
							var target = document.getElementById('#screencapture');
							spinner = new Spinner({top:100}).spin(target);
						} else {
							spinner.spin();
						}
						Janus.log("Successfully attached to feed " + id + " (" + display + ") in room " + msg["room"]);
						$('#screenmenu').hide();
						$('#room').removeClass('hide').show();
					} else {
						// What has just happened?
					}
				}
				if(jsep) {
					Janus.debug("Handling SDP as well...", jsep);
					// Answer and attach
					remoteFeed.createAnswer(
						{
							jsep: jsep,
							media: { audioSend: false, videoSend: false },	// We want recvonly audio/video
							success: function(jsep) {
								Janus.debug("Got SDP!", jsep);
								var body = { request: "start", room: room };
								remoteFeed.send({ message: body, jsep: jsep });
							},
							error: function(error) {
								Janus.error("WebRTC error:", error);
								bootbox.alert("WebRTC error... " + error.message);
							}
						});
				}
			},
			onlocalstream: function(stream) {
				// The subscriber stream is recvonly, we don't expect anything here
			},
			onremotestream: function(stream) {
				if($('#screenvideo').length === 0) {
					// No remote video yet
					$('#screencapture').append('<video class="rounded centered" id="waitingvideo" width="100%" height="100%" />');
					$('#screencapture').append('<video class="rounded centered hide" id="screenvideo" width="100%" height="100%" autoplay playsinline/>');
					// Show the video, hide the spinner and show the resolution when we get a playing event
					console.log('코오오오온솔')
					$("#screenvideo").bind("playing", function () {
						$('#waitingvideo').remove();
						$('#screenvideo').removeClass('hide');
						if(spinner)
							spinner.stop();
						spinner = null;
					});
				}
				Janus.attachMediaStream($('#screenvideo').get(0), stream);
			},
			oncleanup: function() {
				Janus.log(" ::: Got a cleanup notification (remote feed " + id + ") :::");
				$('#waitingvideo').remove();
				if(spinner)
					spinner.stop();
				spinner = null;
			}
		});
}

$(document).ready(function() {
	
	
	// Initialize the library (all console debuggers enabled)
	Janus.init({debug: "all", callback: function() {
		// Use a button to start the demo
		$('#start').one('click', function() {
			$(this).attr('disabled', true).unbind('click');
			// Make sure the browser supports WebRTC
			if(!Janus.isWebrtcSupported()) {
				bootbox.alert("No WebRTC support... ");
				return;
			}
			// Create session
			janus = new Janus(
				{
					server: server,
					success: function() {
						// Attach to VideoRoom plugin
						janus.attach(
							{
								plugin: "janus.plugin.videoroom",
								opaqueId: opaqueId,
								success: function(pluginHandle) {
									$('#details').remove();
									screentest = pluginHandle;
									Janus.log("Plugin attached! (" + screentest.getPlugin() + ", id=" + screentest.getId() + ")");
									// Prepare the username registration
									$('#screenmenu').removeClass('hide').show();
									$('#createnow').removeClass('hide').show();
									$('#create').click(preShareScreen);
									$('#joinnow').removeClass('hide').show();
									$('#join').click(joinScreen);
									$('#desc').focus();
									$('#start').removeAttr('disabled').html("방송 나가기")
										.click(function() {
											$(this).attr('disabled', true);
											janus.destroy();
										});
								},
								error: function(error) {
									Janus.error("  -- Error attaching plugin...", error);
									bootbox.alert("Error attaching plugin... " + error);
								},
								consentDialog: function(on) {
									Janus.debug("Consent dialog should be " + (on ? "on" : "off") + " now");
									if(on) {
										// Darken screen
										$.blockUI({
											message: '',
											css: {
												border: 'none',
												padding: '15px',
												backgroundColor: 'transparent',
												color: '#aaa'
											} });
									} else {
										// Restore screen
										$.unblockUI();
									}
								},
								iceState: function(state) {
									Janus.log("ICE state changed to " + state);
								},
								mediaState: function(medium, on) {
									Janus.log("Janus " + (on ? "started" : "stopped") + " receiving our " + medium);
								},
								webrtcState: function(on) {
									Janus.log("Janus says our WebRTC PeerConnection is " + (on ? "up" : "down") + " now");
									$("#screencapture").parent().unblock();
									if(on) {
										bootbox.alert("Your screen sharing session just started: pass the <b>" + room + "</b> session identifier to those who want to attend.");
									} else {
										bootbox.alert("Your screen sharing session just stopped.", function() {
											janus.destroy();
											window.location.reload();
										});
									}
								},
								onmessage: function(msg, jsep) {
									Janus.debug(" ::: Got a message (publisher) :::", msg);
									var event = msg["videoroom"];
									Janus.debug("Event: " + event);
									if(event) {
										if(event === "joined") {
											myid = msg["id"];
											$('#session').html(room);
											$('#title').html(msg["description"]);
											Janus.log("Successfully joined room " + msg["room"] + " with ID " + myid);
											if(role === "publisher") {
												// This is our session, publish our stream
												Janus.debug("Negotiating WebRTC stream for our screen (capture " + capture + ")");
												screentest.createOffer(
													{
														media: { video: capture, audioSend: true, videoRecv: false},	// Screen sharing Publishers are sendonly
														success: function(jsep) {
															Janus.debug("Got publisher SDP!", jsep);
															var publish = { request: "configure", audio: true, video: true };
															screentest.send({ message: publish, jsep: jsep });
														},
														error: function(error) {
															Janus.error("WebRTC error:", error);
															bootbox.alert("WebRTC error... " + error.message);
														}
													});
											} else {
												// We're just watching a session, any feed to attach to?
												if(msg["publishers"]) {
													var list = msg["publishers"];
													Janus.debug("Got a list of available publishers/feeds:", list);
													for(var f in list) {
														var id = list[f]["id"];
														var display = list[f]["display"];
														Janus.debug("  >> [" + id + "] " + display);
														newRemoteFeed(id, display)
													}
												}
											}
										} else if(event === "event") {
											// Any feed to attach to?
											if(role === "listener" && msg["publishers"]) {
												var list = msg["publishers"];
												Janus.debug("Got a list of available publishers/feeds:", list);
												for(var f in list) {
													var id = list[f]["id"];
													var display = list[f]["display"];
													Janus.debug("  >> [" + id + "] " + display);
													newRemoteFeed(id, display)
												}
											} else if(msg["leaving"]) {
												// One of the publishers has gone away?
												var leaving = msg["leaving"];
												Janus.log("Publisher left: " + leaving);
												if(role === "listener" && msg["leaving"] === source) {
													bootbox.alert("스트리머가 방송을 종료 하였습니다.", function() {
														window.location.reload();
													});
												}
											} else if(msg["error"]) {
												bootbox.alert(msg["error"]);
											}
										}
									}
									if(jsep) {
										Janus.debug("Handling SDP as well...", jsep);
										screentest.handleRemoteJsep({ jsep: jsep });
									}
								},
								onlocalstream: function(stream) {
									Janus.debug(" ::: Got a local stream :::", stream);
									$('#screenmenu').hide();
									$('#room').removeClass('hide').show();
									if($('#screenvideo').length === 0) {
										$('#screencapture').append('<video class="rounded centered" id="screenvideo" width="100%" height="100%" autoplay playsinline muted="muted"/>');
									}
									Janus.attachMediaStream($('#screenvideo').get(0), stream);
									if(screentest.webrtcStuff.pc.iceConnectionState !== "completed" &&
											screentest.webrtcStuff.pc.iceConnectionState !== "connected") {
										$("#screencapture").parent().block({
											message: '<b>Publishing...</b>',
											css: {
												border: 'none',
												backgroundColor: 'transparent',
												color: 'white'
											}
										});
									}
								},
								onremotestream: function(stream) {
									// The publisher stream is sendonly, we don't expect anything here
								},
								oncleanup: function() {
									Janus.log(" ::: Got a cleanup notification :::");
									$('#screencapture').empty();
									$("#screencapture").parent().unblock();
									$('#room').hide();
								}
							});
					},
					error: function(error) {
						Janus.error(error);
						bootbox.alert(error, function() {
							window.location.reload();
						});
					},
					destroyed: function() {
						window.location.reload();
					}
				});
		});
	}});
});