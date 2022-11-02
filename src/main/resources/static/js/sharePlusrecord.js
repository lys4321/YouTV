var janus = null;
var target = null;
var opaqueId = "screensharingtest-" +Janus.randomString(12);

var capture = null;
var role = null;
var room = null;
var myusername = null;

function randomString(len, charSet) {
    charSet = charSet || 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var randomString = '';
    for (var i = 0; i < len; i++) {
    	var randomPoz = Math.floor(Math.random() * charSet.length);
    	randomString += charSet.substring(randomPoz,randomPoz+1);
    }
    return randomString;
}

function videocreate(){
	console.log("동영상 공유를 시작하려합니다.");
	$('#divdivman').append(
		'<video id="vide" width="100%" height="100%" autoplay playsinline muted="muted"/>'
	);
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
						var desc = "123456789"
							role = "publisher";
							var create = {
								request: "create",
								description: desc,
								bitrate: 500000,
								publishers: 1
							};
							target.send({ message: create, success: function(result) {
								var event = result["videoroom"];
								console.log("Event: " + event);
								if(event) {
									// Our own screen sharing session has been created, join it
									room = result["room"];
									console.log("Screen sharing session created: " + room);
									myusername = randomString(12);
									var register = {
										request: "join",
										room: room,
										ptype: "publisher",
										display: myusername
									};
									target.send({ message: register });
								}
							}});
					}
				},
				window: {
					label: "Pick a window",
					className: "btn-success",
					callback: function() {
						capture = "window";
							var desc = "123456789"
							role = "publisher";
							var create = {
								request: "create",
								description: desc,
								bitrate: 500000,
								publishers: 1
							};
							target.send({ message: create, success: function(result) {
								var event = result["videoroom"];
								console.log("Event: " + event);
								if(event) {
									// Our own screen sharing session has been created, join it
									room = result["room"];
									console.log("Screen sharing session created: " + room);
									myusername = randomString(12);
									var register = {
										request: "join",
										room: room,
										ptype: "publisher",
										display: myusername
									};
									target.send({ message: register });
								}
							}});
					}
				}
			},
			onEscape: function() {
			}
		});
	} else {
							var desc = "123456789"
							role = "publisher";
							var create = {
								request: "create",
								description: desc,
								bitrate: 500000,
								publishers: 1
							};
							target.send({ message: create, success: function(result) {
								var event = result["videoroom"];
								console.log("Event: " + event);
								if(event) {
									// Our own screen sharing session has been created, join it
									room = result["room"];
									console.log("Screen sharing session created: " + room);
									myusername = randomString(12);
									var register = {
										request: "join",
										room: room,
										ptype: "publisher",
										display: myusername
									};
									target.send({ message: register });
								}
							}});
	}
}

$(document).ready(function(){
	var server = "http://192.168.0.141:8088/janus";
	Janus.init({debug: "all", callback: function(){
		if(!Janus.isWebrtcSupported()) {
			bootbox.alert("No WebRTC support... ");
			console.log("aa");
			return;
		}
		janus = new Janus({
			server: server,
			success: function(){
				$('#screen').hide();
				console.log("비디오 숨음")
				janus.attach({
					plugin: "janus.plugin.videoroom",
					opaqueId: opaqueId,
					success: function(pluginHandle){ //플러그인 생성 성공 시
						target = pluginHandle;
						console.log("Plugin attached! (" + target.getPlugin() + ", id=" + target.getId() + ")");
						console.log("비디오 나옴")
						$('#divdivman').append(
							'<input type="button" id="cbtn" value="create"><input type="button" id="ebtn" value="exit">'
						);
						$('#cbtn').click(videocreate);
					},
					error: function(error){ // 플러그인 생성 실패 시
						Janus.error("  -- Error attaching plugin...", error);
						bootbox.alert("Error attaching plugin... " + error);
					},
					consentDialog: function(on){ //consentDialog은 UI관리용
						
					},
					iceState: function(state){  // PeerConnection의 ICE 상태가 변경될 시 발동
						Janus.log("ICE state changed to " + state);
					},
					mediaState: function(medium, on){ // Janus가 미디어 수신을 시작하거나 중지할 시 발동
						Janus.log("Janus " + (on ? "started" : "stopped") + " receiving our " + medium);		
					},
					webrtcState: function(on){ // Janus 관점에서 핸들에 연결된 PeerConnection 상태에 따라 다르게 발동
						if(on) {
										bootbox.alert("Your screen sharing session just started: pass the <b>" + room + "</b> session identifier to those who want to attend.");
									} else {
										bootbox.alert("Your screen sharing session just stopped.", function() {
											janus.destroy();
											window.location.reload();
										});
									}
					},
					onmessage: function(msg, jsep){ // 플러그인에서 메시지나 이벤트 수신 될 시 발동
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
												target.createOffer(
													{
														media: { video: capture, audioSend: true, videoRecv: false},	// Screen sharing Publishers are sendonly
														success: function(jsep) {
															Janus.debug("Got publisher SDP!", jsep);
															var publish = { request: "configure", audio: true, video: true };
															target.send({ message: publish, jsep: jsep });
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
													bootbox.alert("The screen sharing session is over, the publisher left", function() {
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
										target.handleRemoteJsep({ jsep: jsep });
									}
					},
					onlocalstream: function(stream){ // 로컬 MediaStream을 사용, 표시할 준비가 됨
						Janus.attachMediaStream($('#vide').get(0), stream);
													
					},
					onremotestream: function(stream){ // 원격 MediaStream을 사용, 표시할 준비가 됨
						//
					},
					oncleanup: function(){ // 플러그인과 Peer가 닫힘
						console.log("플러그인 종료")
					}
				});
			},
			error: function(error){
				console.log("마지막 에러 : "+error);
			},
			destroyed: function(){
				console.log("야누스 끝");
			}
		}); // janus.attach end
	}}); // Janus.init end
}); // ready end