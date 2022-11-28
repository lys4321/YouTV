var userid = "lys4321"//sessionStorage.getItem("userid");
var date = null;
var time = null;
var video_code = null;
var title = null;
var live_state = true;
var live_session = null

function createCode(){
	//코드, 아이디, 제목, 날짜, 상태 , --, --
	date = today.toLocaleDateString();
	time = today.toLocaleTimeString();
	video_code = userid + date + time; 
}

$(document).ready(function(){
	$('#insert_btn').onclick = function(){
		createCode();
		title = $('#title').value();
		var sendData = {
			"video_code": video_code,
			"streamer_id": userid,
			"title": title,
			"video_date": date,
			"live_state": live_state,
			"save_url": null,
			"thumbnail_url": null
			"live_session": live_session
		}
		$.ajax({
			url: "/Ajax/",
			type: "POST",
			data: JSON.stringify(sendData),
			dataType: "JSON",
			success: function(data){
				 //화면 변화시킨다
			},
			error: function(e){
				alert("방송시작 실패함");
			}
		});
	}
});
