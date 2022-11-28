$(document).ready(function(){
					const modal = document.querySelector('.modal');
        			const modal_create = document.querySelector('.modal_create');
        			const modal_update = document.querySelector('.modal_update');
        			const modal_del = document.querySelector('.modal_del');
        			const modal_listUser = document.querySelector('.modal_listUser');
        			const leftvalue = $('.sidebar').offset().left;
        			var title = null;
        			//sessionStorage.clear();
        			
        			var useid = sessionStorage.getItem("using_id");
        			
        			console.log("유저 상태 : "+ useid);
        			
        			if(useid == null){
        				$('#icons2').append(
        						'<a id="loginIcon" class="mainIcon" href="#">'+
        						'<i class="fa-solid fa-right-to-bracket"></i>로그인</a>'+	
        						'<a id="createAccount" class="mainIcon" href="#">'+
        						'<i class="fa-solid fa-plus"></i>회원가입</a>'
        				);
        				$('.sidebar').css("left", -300);
        				$('#FL').hide();
        				
        			}else{
        				$.ajax({
        					url: '/ajax/main_follows',
        					type: 'GET',
        					data: {
        						"userid": sessionStorage.getItem("using_id")
        					},
        					dataType: 'text',
        					success: function(data){
                        		var obj = JSON.parse(data);
                        		var thum = obj["FollowThumbnail"];
                        		var ulist = obj["follows"];
                        		var len = ulist.length
                        		
                        		for(var i=0;i<len;i++){
                        			if(obj["FollowThumbnail"][i] === null){
                        					$('#sidebar-nav').append(
                    	        				'<li class="nav-item">'+
                    	        				'<a class="nav-link" href="/Guest_Mode?video_code=' + ulist[i]["video_code"] + '">'+
                    	        				'<img src="" width="50px" height="50px" style="border-radius:50%;" alt="Profile" class="rounded-circle">'+
                    	        				ulist[i]["streamer_id"] +'</a></li>' 
                    	        			);
                        			}else{
                        				$('#sidebar-nav').append(
                    	        				'<li class="nav-item">'+
                    	        				'<a class="nav-link" href="/Guest_Mode?video_code=' + ulist[i]["video_code"] + '">'+
                    	        				'<img src="data:image/png;base64,' + thum[i] + '" width="50px" height="50px" style="border-radius:50%;" alt="Profile" class="rounded-circle">'+
                    	        				ulist[i]["streamer_id"] +'</a></li>' 
                    	        		);
                        			}
                        		}
                			},
                			error: function(){
                				console.log("error");
                			}
        				});
        				
        				$.ajax({
                			url: '/ajax/main_followlist',
                			type: 'GET',
                			data: {
                				"userid": sessionStorage.getItem("using_id")
                			},
                			dataType: 'text',
                			success: function(data){
                        		var obj = JSON.parse(data);
                        		var thum = obj["FollowThumbnail"];
                        		var ulist = obj["FollowList"];
                        		var len = ulist.length
                        		console.log(ulist.length);
                        		for(var i=0;i<len;i++){
                        			if(obj["FollowThumbnail"][i] === null){
                        				$('#followList').append(
                                				'<div class="items cardWhite">'+
                                				'<a href="/Guest_Mode?video_code=' + ulist[i]["video_code"] + '">'+
        	                    				'<img src="" width="150px" height="150px" style="border-radius:25px;" alt="Profile" class="rounded-circle">'+
        	                    				'<h2>' + ulist[i]["title"] + '</h2>'+
        	                    				'<h3>' + ulist[i]["streamer_id"] + '</h3>'+
        	                    				'</div>'
                                		); 
                        				console.log("!!!!!!!!!!!!!!")
                        			}else{
                        				$('#followList').append(
                        						'<div class="items cardWhite">'+
                                				'<a href="/Guest_Mode?video_code=' + ulist[i]["video_code"] + '">'+
        	                    				'<img src="data:image/png;base64,' + thum[i] + '" width="150px" height="150px" style="border-radius:25px;" alt="Profile" class="rounded-circle">'+
        	                    				'<h2>' + ulist[i]["title"] + '</h2>'+
        	                    				'<h3>' + ulist[i]["streamer_id"] + '</h3>'+
        	                    				'</div>'
                                		); 
                        			}
                        			/* livelist.setAttribute('class', 'video')
                    				livelist.innerHTML=
                    					'<a href="/Guest_Mode?video_code='+ a[i]["video_code"] +'"><img src=data:image/png;base64,'+ obj[i] +' class="Thumnail">'+''+'</div><div class="STitle">' + a[i]["title"] + '</div><div class="SuserId">' + a[i]["streamer_id"] + '</div></a>';
                            		target.appendChild(livelist); */
                        		}
                			},
                			error: function(){
                				console.log("error");
                			}
                		});
        				
        				$('#streamMenu2').append(
        						'<a id="streamIcon" class="mainIcon" href="/Owner_Mode">'+
        						'<i class="fa-solid fa-tower-broadcast"></i>방송하기</a>'+	
        						'<a id="clipIcon" class="mainIcon" href="/YouTV/AllList?type=Record">'+
        						'<i class="fa-solid fa-paperclip"></i>클립제작</a>'
        				);
        				
        				$('#icons2').append(
        						'<a id="logoutIcon" class="mainIcon" href="/YouTV/MainScreen">'+
        						'<i class="fa-solid fa-right-from-bracket"></i>로그아웃</a>'+	
        						'<a id="updateIcon" class="mainIcon" href="#">'+
        						'<i class="fa-solid fa-wrench"></i>정보수정</a>'
        						//여기는 나중에 팔로우 기능 제작 시 그대로 가져가 사용하기
        				);
        				
        				$('#sidebar-nav').append(
        					'<li class="nav-item">'+
        					'<a class="nav-link" id="follist">'+
        					'<i style="font-size: 30px;" class="fa-solid fa-star"></i>&ensp;즐겨찾기 목록</a></li>'
        				);
        				
        			}
        			
        			$('.streamMenu').append(
        						'<a id="streamIcon" class="mainIcon" href="/Owner_Mode">'+
        						'<i class="fa-solid fa-signal-stream"></i>방송하기</a>'+	
        						'<a id="clipIcon" class="mainIcon" href="#">'+
        						'<i class="fa-solid fa-paperclip"></i>클립제작</a>'
        				);
        				
        			
        			$('#btn-create-login').click(function(){
        				modal.style.display = 'none';
        				modal_create.style.display='block';
      		    	  $('.sidebar').css("left", -300);
        			});

        		      $('#loginIcon').click(function(){
        		        modal.style.display = 'block';
        		        $('.sidebar').css("left", -300);
        		      });
        		      
        		      $('#createAccount').click(function(){
        		    	  modal_create.style.display='block';
        		    	  $('.sidebar').css("left", -300);
        		      });
        		      
        		      $('#updateIcon').click(function(){
        		    	  modal_update.style.display='block';
        		    	  $('.sidebar').css("left", -300);
        		      });
        		      
        		      $('#deleteIcon').click(function(){
							modal_del.style.display = 'block';
        		      	});
        		      
        		      $('#exitmark').click(function(){
        		    	  modal.style.display = 'none';
        		    	  $('#id').val('');
        		    	  $('#password').val('');
        		    	  if(useid != null){
        		    		 $('.sidebar').css("left", leftvalue);
        		    	  }
        		      });
        		      
        		      $('#exitmark_create').click(function(){
        		    	  modal_create.style.display = 'none';
        		    	  $('#createform').each(function(){
        		    		  this.reset();
        		    	  });
        		    	  
        		      });
        		      
        		      $('#exitmark_update').click(function(){
        		    	  modal_update.style.display = 'none';
        		    	  $('#createform').each(function(){
        		    		  this.reset();
        		    	  });
        		    	  if(useid != null){
         		    		 $('.sidebar').css("left", leftvalue);
         		    	  }
        		      });
        		      //
        		      $('#del_exitmark').click(function(){
        		    	  	$('#modal_del').style.display = 'none';
        		    	  	
        		    	  if(useid != null){
         		    		 $('.sidebar').css("left", leftvalue);
         		    	  }
         		      });
        		      
        		      $('#logoutIcon').click(function(){
        		    	 sessionStorage.clear(); 
        		    	 window.location.href = "/YouTV/MainScreen";
        		      });
        		      
        		      $('#deleteIcon').click(function(){
							modal_del.style.display = 'block';
      		      	  });
        		      $('#del_exitmark').click(function(){
        		    	  modal_del.style.display = 'none';
      		          });
        		      
        		      $('#yesbtn').click(function(){
        		    	  $.ajax({
        		    		 url: '/ajax/AccountDelete',
        		    		 type: 'POST',
        		    		 data: {
        		    			 'userid': sessionStorage.getItem("using_id")
        		    		 },
        		    		 success: function(){
        		    			sessionStorage.clear();
                       			window.location.href = "/YouTV/MainScreen";
                       			alert("계정 삭제 완료");
        		    		 }
        		    	  });
        		      });
        		      
					  $('#nobtn').click(function(){
						  modal_del.style.display = 'none';
        		      });
					  
        		      
            		//로그인
            		$('#btn-login').click(function(){
            			var formData = $("#loginform").serialize();
        				console.log(formData);
        				$.ajax({
        					url: '/ajax/AccountExistCheck',
        					type: 'POST',
        					data: formData,
        					dataType: 'json',
        					success: function(data){
        						if(data == true){
        							var id = document.getElementById("id").value;
        							var pw = document.getElementById("password").value;
        							sessionStorage.setItem("using_id", id);
        							sessionStorage.setItem("using_pw", pw);
        							window.location.href="/YouTV/MainScreen"
        							
        						}else{
        							alert("존재하지 않는 계정이거나 입력된 정보가 잘못되었습니다.")
        							
        						}
        					}
        				});
            		});
            		
            		$('#btn-check').click(function(){
						  if(sessionStorage.getItem("using_pw") === $('#checkpassword').val()){
							  $('#check').css('display', 'none');
							  $('#setting').css('display', 'block');
						  }else{
							  alert("인증실패");
						  }
					  });
            		
            		//중복체크
            		var dupcheck_state = 0;
            		document.getElementById("cid").onclick = function(){
        				dupcheck_state = 0;
        			}
            		$('#dupCheck').click(function(){
                		var formData = $("#cid").val();
        				console.log(formData);
        				if(!formData){
        					alert("비어있음 입력을 바람");
        				}else{
        					$.ajax({
            					url: '/ajax/AccountDupCheck',
            					type: 'POST',
            					data: {
            						"inid":formData
            					},
            					dataType: 'json',
            					success: function(data){
            						if(data == true){
            							alert("사용가능한 ID");
            							dupcheck_state = 1;
            						}else{
            							alert("이미 사용중인 ID")
            							dupcheck_state = 0;
            						}
            					}
            				});
        				}
            		});
            		
            		$('#profile-file').on('change', function(){
            			var filename = $('#profile-file').val();
            			console.log(filename);
            			$('#filename').val(filename);
            		});
            		
            		$('#profile-file').on('change', function(){
            			if(this.files && this.files[0]){
            				var reader = new FileReader();
            				reader.onload = function(e){
            					$('#img-profile').attr('src', e.target.result);
            				}
            				reader.readAsDataURL(this.files[0]);
            			}
            		});
            		
            		$('#btn-profile').on('click', function(){
            			var formData = new FormData();
            			$('#profile-file').attr("name", sessionStorage.getItem("using_id"));
            			console.log($('#profile-file')[0].files[0])
            			formData.append(sessionStorage.getItem("using_id"), $('#profile-file')[0].files[0]);
            			
            			
            			$.ajax({
            				url: '/ajax/AccountUpdateImg',
            				processData: false,
            				contentType: false,
            				type: 'POST',
            				data: formData,
            				success: function(data){
            					console.log(data);
            					//sessionStorage.setItem("profile", data);
            					//window.location.href = "/YouTV/MainScreen";
            				},
            			error: function(e){
            				console.log(e)
            			}
            			});
            		});
            		
            		var comcheck = 0;
            		$('#cpw').keyup(function(){
            			var reg = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";
            			var val1 = $('#cpw').val();
            			var val2 = $('#compare').val();
            			
            			console.log(val1.length);
            			if(val1.length < 8){
            				console.log("1");
            				$('#comresult').text("규칙에 맞춰 작성해 주세요");
        					$('#comresult').css("color","orange");
        					comcheck = 0;
            			}else if(val1.length >= 8 || val2 == null || val2.length > 8){
            				$('#comresult').text("비밀번호 확인에 값을 입력해 주세요");
        					$('#comresult').css("color","green");
        					comcheck = 0;
            			}else{
            				comcheck = 0;
            			}
            			
            		});
	
            		$('#compare').keyup(function(){
            			var reg = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";
            			var val1 = $('#cpw').val();
            			var val2 = $('#compare').val();
            			if(val1 != '' && val2 == ''){
            				null;
            			}else if (val1.lengh < 8){
            				console.log(val1.lengh);
            				$('#comresult').text("비밀번호 규칙에 맞춰 작성해 주세요.");
        					$('#comresult').css("color","red");
            			}else if(val1 != '' || val2 != ''){
            				if(val1 == val2){
            					$('#comresult').text("일치");
            					$('#comresult').css("color","green");
            					comcheck = 1;
            				}else{
            					console.log(val1.length);
            					$('#comresult').text("불일치");
            					$('#comresult').css("color","red");
            					comcheck = 0;
            				}
            			}
            		});
            		
            		$('#listUser').click(function(){
						$('.modal_listUser').css('display', 'block');
					    $('.sidebar').css("left", -300);
					    console.log($('#listuserid').val());
					    console.log(video_code);
					    $.ajax({
							url: '/ajax/getUserList',
							type: 'GET',
							data: {
								"code": video_code
							},
							dataType: 'json',
							success: function(data){
								console.log(JSON.stringify(data));
								
								var arr = new Array();
								arr.push(data);
								for(var a of arr){
									for(var b of a){
										$('#listsUserlist').append(
									'<li>' + b + '</li>'			
									);
									}
								}
								
								$('#listsUserlist li').on('click', function(){
									$('.modal_History').css('display', 'block');
									title = $(this).text();
									$('#hititle').text(title);
									console.log(video_code);
									console.log(title);
									
									$.ajax({
										url: '/ajax/queryHistoryList',
										type: 'GET',
										data: {
											"code": video_code,
											"userid": title
										},
										dataType: 'json',
										success: function(data){
											for(var i=0; i<data.length; i++){
												$('#tbody').append(
												'<tr><td>'+data[i]['chat']+'</td><td>'+data[i]['chatDate']+'</td></tr>'
												);
											}
										},
										error: function(error){
											console.log(error);
											alert("유저 정보 불러오기 실패1");
										}
									});
								});
								
							},
							error: function(){
								alert("유저 정보 불러오기 실패");
							}
						});
					});	
					
					$('#btn-user').click(function(){
						var query = $('#listuserid').val();
						console.log(query);
						$.ajax({
							url: '/ajax/queryUserList',
							type: 'GET',
							data: {
								"code": video_code,
								"query": query
							},
							dataType: 'json',
							success: function(data){
								$('#listsUserlist').empty();
								var arr = new Array();
								arr.push(data);
								for(var a of arr){
									for(var b of a){
										$('#listsUserlist').append(
											'<li>' + b + '</li>'		
										);	
									}
								}
								$('#listsUserlist li').on('click', function(){
									$('.modal_History').css('display', 'block');
									var title = $(this).text();
									$('#hititle').text(title);
									
									$.ajax({
										url: '/ajax/queryHistoryList',
										type: 'GET',
										data: {
											"code": video_code,
											"userid": title
										},
										dataType: 'json',
										success: function(data){
											for(var i=0; i<data.length; i++){
												$('#tbody').append(
												'<tr><td>'+data[i]['chat']+'</td><td>'+data[i]['chatDate']+'</td></tr>'
												);
											}
										},
										error: function(error){
											console.log(error);
											alert("유저 정보 불러오기 실패1");
										}
									});
								});
							},
							error: function(){
								alert("유저 정보 불러오기 실패");
							}
						});
					});
            		
            		//가입
            		$('#btn-create').click(function(){
            			if( !($("#cid").val()) || !($("#cpw").val()) || !($("#cnm").val()) || !($("#cpn").val())){
            				alert("모든 항목을 입력해주세요");
            			}else{
            				if(dupcheck_state == 1 && comcheck == 1){
            					$.ajax({
            						url: '/ajax/AccountCreate',
            						type: 'POST',
            						data: {
            							"inid": $("#cid").val(),
            							"inpassword": $("#cpw").val(),
            							"inname": $("#cnm").val(),
            							"inpnum": $("#cpn").val()
            						},
            						dataType: 'json',
            						success: function(data){
            							if(data == true){
            								console.log(data);
            								alert("생성");
            								modal_create.style.display = 'none';
            		        		    	  $('#createform').each(function(){
            		        		    		  this.reset();
            		        		    	  });
            		        		    	  if(useid != null){
            		         		    		 $('.sidebar').css("left", leftvalue);
            		         		    	  }
            							}else{
            								alert("실패");
            							}
            						}
            					});
            				}else{
            					alert("ID 중복체크나 비밀번호의 일치가 필요합니다.");
            				}
            			}
            		});
            		
            		$('#banUserbtn').on('click', function(){
						$('.modal_banUser').css('display', 'block');
						$('#banUserId').text(title);
					});
					
					$('#banConfirm').on('click', function(){
						var str = $('#banReasonSector').val();
						$.ajax({
							url: '/ajax/addBan',
							type: 'POST',
							data: {
								"sid": useid,
								"uid": title,
								"banReason": str
							},
							success: function(data){
								alert(JSON.stringify(data));
							},
							error: function(error){
								alert("?");
							}
						});
					});
            		
            		
					
		/*		$('#listsUserlist li').on('click', function(){
						$('.modal_History').css('display', 'block');
						var title = $(this).text();
						$('#hititle').text(title);
						
						$.ajax({
							url: '/ajax/queryHistoryList',
							type: 'GET',
							data: {
								"code": video_code,
								"userid": title
							},
							dataType: 'json',
							success: function(data){
								console.log(data);
							},
							error: function(){
								alert("유저 정보 불러오기 실패");
							}
						});
					});*/
					
					$('#exitmark_listuser').click(function(){
							$('#listsUserlist').empty();
        		    	  	modal_listUser.style.display = 'none';
        		    	  	
        		    	  if(useid != null){
         		    		 $('.sidebar').css("left", leftvalue);
         		    	  }
         		      });
         		      
         		    $('#exitmark_history').click(function(){
							$('.modal_History').css('display', 'none');
							$('#tbody').empty();
							
							
         		      });
         		       $('#exitmark_ban').click(function(){
							$('.modal_banUser').css('display', 'none');
							$('#banReasonSector').empty();
         		      });
         		      
         		      $.ajax({
						
						});
});


