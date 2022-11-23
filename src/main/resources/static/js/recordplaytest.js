var server = "http://192.168.0.141:8088/janus";
var janus = null;
var recordplay = null;
var opaqueId = "recordplaytest-"+Janus.randomString(12);

var spinner = null;
var bandwidth = 1024 * 1024;

var myname = null;
var recording = false;
var playing = false;
var recordingId = null;
var selectedRecording = null;
var selectedRecordingInfo = null;

var acodec = (getQueryStringValue("acodec") !== "" ? getQueryStringValue("acodec") : null);
var vcodec = (getQueryStringValue("vcodec") !== "" ? getQueryStringValue("vcodec") : null);
var vprofile = (getQueryStringValue("vprofile") !== "" ? getQueryStringValue("vprofile") : null);
var doSimulcast = (getQueryStringValue("simulcast") === "yes" || getQueryStringValue("simulcast") === "true");
var doSimulcast2 = (getQueryStringValue("simulcast2") === "yes" || getQueryStringValue("simulcast2") === "true");

var date = new Date();
var dateall = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();

var startCheck = 0;
var stopCheck = 0;

var urlParams = new URL(location.href).searchParams;
var video_code = urlParams.get('video_code');

$(document).ready(function() {
	var msg = "${test}";
	console.log(msg);
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
						// Attach to Record&Play plugin
						
						////////////////////////////
						janus.attach(
							{
								plugin: "janus.plugin.recordplay",
								opaqueId: opaqueId,
								success: function(pluginHandle) {
									console.log("플러그인 핸들 : "+JSON.stringify(pluginHandle));
									$('#details').remove();
									recordplay = pluginHandle;
									Janus.log("Plugin attached! (" + recordplay.getPlugin() + ", id=" + recordplay.getId() + ")");
									// Prepare the name prompt
									$('#recordplay').removeClass('hide').show();
									$('#start').removeAttr('disabled').html("Stop")
										.click(function() {
											$(this).attr('disabled', true);
											janus.destroy();
										});
									updateRecsList();
								},
								error: function(error) {
									Janus.error("  -- Error attaching plugin...", error);
									bootbox.alert("  -- Error attaching plugin... " + error);
								},
								consentDialog: function(on) {
									Janus.debug("Consent dialog should be " + (on ? "on" : "off") + " now");
									if(on) {
										// Darken screen and show hint
										$.blockUI({
											message: '<div><img src="up_arrow.png"/></div>',
											css: {
												border: 'none',
												padding: '15px',
												backgroundColor: 'transparent',
												color: '#aaa',
												top: '10px',
												left: (navigator.mozGetUserMedia ? '-100px' : '300px')
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
									$("#videobox").parent().unblock();
								},
								onmessage: function(msg, jsep) {
									Janus.debug(" ::: Got a message :::", msg);
									var result = msg["result"];
									if(result) {
										if(result["status"]) {
											var event = result["status"];
											if(event === 'preparing' || event === 'refreshing') {
												Janus.log("Preparing the recording playout");
												recordplay.createAnswer(
													{
														jsep: jsep,
														media: { audioSend: false, videoSend: false },	// We want recvonly audio/video
														success: function(jsep) {
															Janus.debug("Got SDP!", jsep);
															var body = { request: "start" };
															recordplay.send({ message: body, jsep: jsep });
														},
														error: function(error) {
															Janus.error("WebRTC error:", error);
															bootbox.alert("WebRTC error... " + error.message);
														}
													});
												if(result["warning"])
													bootbox.alert(result["warning"]);
											} else if(event === 'recording') {
												// Got an ANSWER to our recording OFFER
												if(jsep)
													recordplay.handleRemoteJsep({ jsep: jsep });
												var id = result["id"];
												if(id) {
													Janus.log("The ID of the current recording is " + id);
													recordingId = id;
												}
											} else if(event === 'slow_link') {
												var uplink = result["uplink"];
												if(uplink !== 0) {
													// Janus detected issues when receiving our media, let's slow down
													bandwidth = parseInt(bandwidth / 1.5);
													recordplay.send({
														message: {
															request: 'configure',
															'video-bitrate-max': bandwidth,		// Reduce the bitrate
															'video-keyframe-interval': 15000	// Keep the 15 seconds key frame interval
														}
													});
												}
											} else if(event === 'playing') {
												Janus.log("Playout has started!");
											} else if(event === 'stopped') {
												Janus.log("Session has stopped!");
												var id = result["id"];
												if(recordingId) {
													if(recordingId !== id) {
														Janus.warn("Not a stop to our recording?");
														return;
													}
													bootbox.alert("녹화 완료!");
												}
												if(selectedRecording) {
													if(selectedRecording !== id) {
														Janus.warn("Not a stop to our playout?");
														return;
													}
												}
												// FIXME Reset status
												$('#videobox').empty();
												$('#video').hide();
												recordingId = null;
												recording = false;
												playing = false;
												recordplay.hangup();
												$('#record').removeAttr('disabled').click(startRecording);
												$('#play').removeAttr('disabled').click(startPlayout);
												$('#list').removeAttr('disabled').click(updateRecsList);
												$('#recset').removeAttr('disabled');
												$('#recslist').removeAttr('disabled');
												updateRecsList();
												$('#play').click();
												
											}
										}
										console.log("00000000000000000");
									} else {
										// FIXME Error?
										var error = msg["error"];
										bootbox.alert(error);
										// FIXME Reset status
										$('#videobox').empty();
										$('#video').hide();
										recording = false;
										playing = false;
										recordplay.hangup();
										$('#record').removeAttr('disabled').click(startRecording);
										$('#play').removeAttr('disabled').click(startPlayout);
										$('#list').removeAttr('disabled').click(updateRecsList);
										$('#recset').removeAttr('disabled');
										$('#recslist').removeAttr('disabled');
										updateRecsList();
									}
								},
								onlocalstream: function(stream) {
									
									if(playing === true)
										return;
									console.log(stream)
									Janus.debug(" ::: Got a local stream :::", stream);
									$('#videotitle').html("Recording...");
									$('#stop').unbind('click').click(stop);
									$('#video').removeClass('hide').show();
									if($('#thevideo').length === 0)
										$('#videobox').append('<video class="rounded centered" id="thevideo" width="100%" height="100%" autoplay playsinline muted="muted"/>');
									
									Janus.attachMediaStream($('#thevideo').get(0), stream);
									
									
									$("#thevideo").get(0).muted = "muted";
									if(recordplay.webrtcStuff.pc.iceConnectionState !== "completed" &&
											recordplay.webrtcStuff.pc.iceConnectionState !== "connected") {
										$("#videobox").parent().block({
											message: '<b>Publishing...</b>',
											css: {
												border: 'none',
												backgroundColor: 'transparent',
												color: 'white'
											}
										});
									}
									var videoTracks = stream.getVideoTracks();
									if(!videoTracks || videoTracks.length === 0) {
										// No remote video
										$('#thevideo').hide();
										if($('#videobox .no-video-container').length === 0) {
											$('#videobox').append(
												'<div class="no-video-container">' +
													'<i class="fa fa-video-camera fa-5 no-video-icon"></i>' +
													'<span class="no-video-text">No remote video available</span>' +
												'</div>');
										}
									} else {
										$('#videobox .no-video-container').remove();
										$('#thevideo').removeClass('hide').show();
									}
									
								},
								onremotestream: function(stream) {
									
									if(playing === false)
										return;
									Janus.debug(" ::: Got a remote stream :::", stream);
									if($('#thevideo').length === 0) {
										var query = window.location.search;
										var param = new URLSearchParams(query);
										var title = param.get('title'); 
									
										console.log(title);   
										$('#videotitle').html(title);
										$('#stop').unbind('click').click(stop);
										$('#video').removeClass('hide').show();
										$('#videobox').append('<video class="rounded centered hide" id="thevideo" width="100%" height="100%" autoplay playsinline/>');
										// No remote video yet
										$('#videobox').append('<video class="rounded centered" id="waitingvideo" width="100%" height="100%" />');
										if(spinner == null) {
											var target = document.getElementById('videobox');
											spinner = new Spinner({top:100}).spin(target);
										} else {
											spinner.spin();
										}
										// Show the video, hide the spinner and show the resolution when we get a playing event
										$("#thevideo").bind("playing", function () {
											$('#waitingvideo').remove();
											$('#thevideo').removeClass('hide');
											if(spinner)
												spinner.stop();
											spinner = null;
										});
									}
									Janus.attachMediaStream($('#thevideo').get(0), stream);
									
									
									var mediaStream = document.getElementById("thevideo").captureStream();
									console.log(stream);
									var mediaRecorder = new MediaRecorder(mediaStream, {
									      mimeType: "video/webm; codecs=vp9"
									    });
									var arrVideoData = [];
									var icheck=0;
									$('#capstartbtn').on('click', function(){
										capstartbtn.disabled = true
    									capstopbtn.disabled = false
    									
    									
    									mediaRecorder.ondataavailable = (event)=>{
										 console.log("들어감");
										 arrVideoData.push(event.data);
										}
										mediaRecorder.start();
										startCheck++;
									});
									
									
									$('#capstopbtn').on('click', function(){
										capstartbtn.disabled = true
    									capstopbtn.disabled = true
    									mediaRecorder.onstop = (event)=>{
											console.log("이벤트1");
								            // 들어온 스트림 데이터들(Blob)을 통합한 Blob객체를 생성
								            var blob = new Blob(arrVideoData, {type:"video/webm"});
								
								            // BlobURL 생성: 통합한 스트림 데이터를 가르키는 임시 주소를 생성
								            var blobURL = window.URL.createObjectURL(blob);
								
											console.log("이벤트2");
								            // 다운로드 구현
								            
											console.log(video_code);
											console.log(dateall);
											
								            var makerId = sessionStorage.getItem("using_id");
								            console.log(makerId+dateall+video_code);
								            var $anchor = document.createElement("a"); // 앵커 태그 생성
								            document.body.appendChild($anchor);
								            $anchor.style.display = "none";
								            $anchor.href = blobURL; // 다운로드 경로 설정
								            $anchor.download = makerId + dateall + video_code + ".webm"; // 파일명 설정
								            $anchor.click(); // 앵커 클릭
								            console.log("이벤트3");
								            // 배열 초기화
								            arrVideoData.splice(0);
								        }
								        mediaRecorder.stop();
								        stopCheck++;
									});
									
									/*
									var mediaStream = document.getElementById("thevideo").captureStream();
									var mediaRecorder = new MediaRecorder(mediaStream);
									var arrVideoData = [];
									mediaRecorder.ondataavailable = (event)=>{
										 arrVideoData.push(event.data);
									}
									mediaRecorder.onstop = (event)=>{
											console.log("이벤트1");
								            // 들어온 스트림 데이터들(Blob)을 통합한 Blob객체를 생성
								            var blob = new Blob(arrVideoData);
								
								            // BlobURL 생성: 통합한 스트림 데이터를 가르키는 임시 주소를 생성
								            var blobURL = window.URL.createObjectURL(blob);
								
											console.log("이벤트2");
								            // 다운로드 구현
								            var $anchor = document.createElement("a"); // 앵커 태그 생성
								            document.body.appendChild($anchor);
								            $anchor.style.display = "none";
								            $anchor.href = blobURL; // 다운로드 경로 설정
								            $anchor.download = "test1.webm"; // 파일명 설정
								            $anchor.click(); // 앵커 클릭
								            console.log("이벤트3");
								            // 배열 초기화
								            arrVideoData.splice(0);
								        }
								
								        // 녹화 시작
								        mediaRecorder.start(); 
								        $('#downbtn').on('click', function(){
											mediaRecorder.stop(); 
										});*/
									
									
									var videoTracks = stream.getVideoTracks();
									if(!videoTracks || videoTracks.length === 0) {
										// No remote video
										$('#thevideo').hide();
										if($('#videobox .no-video-container').length === 0) {
											$('#videobox').append(
												'<div class="no-video-container">' +
													'<i class="fa fa-video-camera fa-5 no-video-icon"></i>' +
													'<span class="no-video-text">No remote video available</span>' +
												'</div>');
										}
									} else {
										$('#videobox .no-video-container').remove();
										$('#thevideo').removeClass('hide').show();
									}
									
								},
								oncleanup: function() {
									Janus.log(" ::: Got a cleanup notification :::");
									// FIXME Reset status
									$('#waitingvideo').remove();
									if(spinner)
										spinner.stop();
									spinner = null;
									$('#videobox').empty();
									$("#videobox").parent().unblock();
									$('#video').hide();
									recording = false;
									playing = false;
									$('#record').removeAttr('disabled').click(startRecording);
									$('#play').removeAttr('disabled').click(startPlayout);
									$('#list').removeAttr('disabled').click(updateRecsList);
									$('#recset').removeAttr('disabled');
									$('#recslist').removeAttr('disabled');
									updateRecsList();
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
	
	
	
	$('#btn-Clip').on('click', function(){
		if((startCheck>0) && (stopCheck>0)){
			var formData = new FormData();
			var filetitle = $('#filetitle').val();
			formData.append("makerid", sessionStorage.getItem("using_id"));
			formData.append("video_code", video_code);
			formData.append("filetitle", filetitle);
			formData.append("clipCode", sessionStorage.getItem("using_id")+""+dateall+""+video_code);
			formData.append(sessionStorage.getItem("using_id")+""+dateall+""+video_code, $('#clipFile')[0].files[0]);
			console.log(formData);
			
			$.ajax({
				url: '/ajax/uploadClip',
				processData: false,
	            contentType: false,
	            type: 'POST',
	            data: formData,
	            success: function(data){
	            	if(data === true){
						window.location.href = "/YouTV/MainScreen";
					}else{
						alert("클립 업로드 실패");
					}
	            	//sessionStorage.setItem("profile", data);
	            	//window.location.href = "/YouTV/MainScreen";
	            },
	            error: function(e){
	            	console.log(e)
	            }
			});
		}else{
			alert("클립을 녹화하고 저장해야 합니다.")
		}
	});
});

function checkEnter(field, event) {
	var theCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if(theCode == 13) {
		if(field.id == 'name')
			insertName();
		return false;
	} else {
		return true;
	}
}

function updateRecsList() {
	$('#list').unbind('click');
	$('#update-list').addClass('fa-spin');
	var body = { request: "list" };
	Janus.debug("Sending message:", body);
	recordplay.send({ message: body, success: function(result) {
		setTimeout(function() {
			$('#list').click(updateRecsList);
			$('#update-list').removeClass('fa-spin');
		}, 500);
		if(!result) {
			bootbox.alert("Got no response to our query for available recordings");
			return;
		}
		if(result["list"]) {
			$('#recslist').empty();
			$('#record').removeAttr('disabled').click(startRecording);
			$('#list').removeAttr('disabled').click(updateRecsList);
			var list = result["list"];
			list.sort(function(a, b) {return (a["date"] < b["date"]) ? 1 : ((b["date"] < a["date"]) ? -1 : 0);} );
			Janus.debug("Got a list of available recordings:", list);
			var query = window.location.search;
			var param = new URLSearchParams(query);
			var title = param.get('title'); 
			$('#recslist').append("<li><a href='#' id='" + list[0]["id"] + "'>" + title + " [" + list[0]["date"] + "]" + "</a></li>");
			
			$('#recset').click();
			$('#recslist a').bind('click').click(function() {
				selectedRecording = $(this).attr("id");
				selectedRecordingInfo = $(this).text();
				$('#recset').html($(this).html()).parent().removeClass('open');
				$('#play').removeAttr('disabled').click(startPlayout);
				$('#play').click();
				$.ajax({
				url: '/ajax/recordChat',
				type: 'GET',
				data:{
					"video_code": video_code
				},
				success: function(data){
					var i = 0;
					setInterval(function(){
						str = "<div class='col-6'>";
                        str += "<div class='alert alert-warning'>";
                        str += "<b>" + data[i]["user_id"] + " : " + data[i]["chat"] + "</b>";
                        str += "</div></div>";
                        $('#msgSector').css('visibility', 'visible');
						$("#msgArea").append(str);
						i++;
					}, 1000);
				},
				error: function(error){
					
				}
			});
				
				return false;
			});
			
		}
	}});
}

function startRecording() {
	if(recording)
		return;
	// Start a recording
	recording = true;
	playing = false;
	myname = "코드 동영상";////////////sessionStorage.getItem("코드")
		$('#record').unbind('click').attr('disabled', true);
		$('#play').unbind('click').attr('disabled', true);
		$('#list').unbind('click').attr('disabled', true);
		$('#recset').attr('disabled', true);
		$('#recslist').attr('disabled', true);

		// bitrate and keyframe interval can be set at any time:
		// before, after, during recording
		recordplay.send({
			message: {
				request: 'configure',
				'video-bitrate-max': bandwidth,		// a quarter megabit
				'video-keyframe-interval': 15000	// 15 seconds
			}
		});

		recordplay.createOffer(
			{
				// By default, it's sendrecv for audio and video... no datachannels
				// If you want to test simulcasting (Chrome and Firefox only), then
				// pass a ?simulcast=true when opening this demo page: it will turn
				// the following 'simulcast' property to pass to janus.js to true
				simulcast: doSimulcast,
				success: function(jsep) {
					Janus.debug("Got SDP!", jsep);
					var body = { request: "record", name: myname };
					// We can try and force a specific codec, by telling the plugin what we'd prefer
					// For simplicity, you can set it via a query string (e.g., ?vcodec=vp9)
					if(acodec)
						body["audiocodec"] = acodec;
					if(vcodec)
						body["videocodec"] = vcodec;
					// For the codecs that support them (VP9 and H.264) you can specify a codec
					// profile as well (e.g., ?vprofile=2 for VP9, or ?vprofile=42e01f for H.264)
					if(vprofile)
						body["videoprofile"] = vprofile;
					recordplay.send({ message: body, jsep: jsep });
				},
				error: function(error) {
					Janus.error("WebRTC error...", error);
					bootbox.alert("WebRTC error... " + error.message);
					recordplay.hangup();
				}
			});
}

function startPlayout() {
	if(playing)
		return;
	// Start a playout
	recording = false;
	playing = true;
	if(!selectedRecording) {
		playing = false;
		return;
	}
	$('#record').unbind('click').attr('disabled', true);
	$('#play').unbind('click').attr('disabled', true);
	$('#list').unbind('click').attr('disabled', true);
	$('#recset').attr('disabled', true);
	$('#recslist').attr('disabled', true);
	
	var query = window.location.search;
	var param = new URLSearchParams(query);
	var id = param.get('sessionId'); 

	console.log(id);   
	
	var play = { request: "play", id: parseInt(id) };
	recordplay.send({ message: play });
	
	
}


function stop() {
	// Stop a recording/playout
	$('#stop').unbind('click');
	var stop = { request: "stop" };
	recordplay.send({ message: stop });
	recordplay.hangup();
}

// Helper to parse query string
function getQueryStringValue(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
