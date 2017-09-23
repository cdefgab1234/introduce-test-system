$(function() {
		//所有登录，注册，忘记密码的前端验证代码
	    $('#defaultForm').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	           loginname: {
	                message: '请输入用户名',
	                validators: {
	                    notEmpty: {},
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_\.]{4,}$/,
	                        message: '输入字母数字下划线长度4位以上'
	                    },
	                /* remote: {
                            url: 'ValidatorLoginName',
                            type: "Post",
                            message: '用户名不存在'
                        },*/
	                    different: {
	                        field: 'password',
	                        message: '用户名不能与密码相同'
	                    }
	                }
	            },
	            username: {
	                message: '请输入用户名',
	                validators: {
	                    notEmpty: {},
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_\.]{5,}$/,
	                        message: '输入字母数字下划线长度5位以上'
	                    },
	                  /*   remote: {
	                        url: 'selectuser.action',
	                        type: "Post",
	                        delay :4000,
	                        message: '用户名已存在'
	                    },*/
	                    different: {
	                        field: 'password',
	                        message: '用户名不能与密码相同'
	                    }
	                }
	            },
	            yzm: {
	                message: '请输入验证码',
	                validators: {
	                    notEmpty: {},
	                    identical: {
	                        field: 'yzms',
	                         message: '验证码输入错误'
	                    },
	                }
	            },
	            yzms: {
	                validators: {
	                	  notEmpty: {},
	                }
	            },
	            email: {
	                validators: {
	                	 notEmpty: {
	                		 message: '邮箱不能为空'
	                	 },
	                	  regexp: {
		                        regexp: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
		                        message: '邮箱格式不正确'
		                    }
	                }
	            },
	            phone: {
	                validators: {
	                	 notEmpty: {
	                		 message: '手机号不能为空'
	                	 },
	                	  regexp: {
		                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
		                        message: '手机格式输入不正确'
		                    }
	                }
	            },
	            sfz: {
	                validators: {
	                	 notEmpty: {
	                		 message: '身份证号不能为空'
	                	 },
	                	  regexp: {
		                        regexp: /^[0-9]{18}$/,
		                        message: '身份证格式输入不正确'
		                    }
	                }
	            },
	            password: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入密码'
	                    },
	                 /*   stringLength: {
	                        min: 6,
	                        max: 15
	                    },*/
	                     regexp: {
	                        regexp: /^[a-zA-Z0-9]{6,}$/,
	                        message: '输入字母数字下划线长度6位以上'
	                    },
	                  /*  identical: {
	                        field: 'confirmPassword',
	                         message: '应该与密码相同'
	                    },*/
	                 /*  remote: {
                            url: 'loginPass',
                            type: "Post",
                            message: '密码与用户名不匹配'
                        },*/
	                    different: {
	                        field: 'username',
	                        message: '密码不能与用户名相同'
	                    }
	                }
	            },
	            confirmPassword: {
	                validators: {
	                    notEmpty: {
	                     message: '不能为空'
	                    },
	                    stringLength: {
	                        min: 6,
	                        max: 15
	                    },
	                    identical: {
	                        field: 'password',
	                         message: '两次输入密码不一致'
	                    },
	                    different: {
	                        field: 'username',
	                        message: '密码不能与用户名相同'
	                    }
	                }
	            },
	            eName: {
	                validators: {
	                	   notEmpty: {
	  	                  	 message: 'eName不能为空'
	  	                  },
		  	                stringLength: {
		                        min: 6,
		                        max: 8
		                    }
	                }
	            },
	            oldpassword: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入密码'
	                    },
	                 /*   stringLength: {
	                        min: 6,
	                        max: 15
	                    },*/
	                     regexp: {
	                        regexp: /^[a-zA-Z0-9]{6,}$/,
	                        message: '输入字母数字下划线长度6位以上'
	                    },
	                 /*   identical: {
	                        field: 'newpassword',
	                         message: '新密码不能与原密码相同'
	                    },*/
	                    different: {
	                        field: 'newpassword',
	                        message: '新密码不能与原密码相同'
	                    }
	                }
	            },
	            newpassword: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入密码'
	                    },
	                 /*   stringLength: {
	                        min: 6,
	                        max: 15
	                    },*/
	                     regexp: {
	                        regexp: /^[a-zA-Z0-9]{6,}$/,
	                        message: '输入字母数字下划线长度6位以上'
	                    },
	                  /*  identical: {
	                        field: 'Oldpassword',
	                         message: '新密码不能与原密码相同'
	                    },*/
	                    different: {
	                        field: 'oldpassword',
	                        message: '新密码不能与原密码相同'
	                    }
	                }
	            },
	            replyAnswer: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入内容'
	                    },
	                   stringLength: {
	                        min: 30,
	                        max: 200,
	                        message: '请输入30到200的字数'
	                    }
	                }
	            },
	            replySend: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入内容'
	                    },
	                   stringLength: {
	                        min: 30,
	                        max: 300,
	                        message: '请输入30到200的字数'
	                    }
	                }
	            },
	            title: {
	                validators: {
	                    notEmpty: {
	                      message: '请输入标题内容'
	                    },
	                   stringLength: {
	                        min: 5,
	                        max: 50,
	                        message: '请输入5到50的字数'
	                    }
	                }
	            }
	            
	           
          }
	    })
	})
