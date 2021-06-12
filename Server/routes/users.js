const express = require('express');
const multer = require('multer');
const path = require('path');
const router = express.Router();
var bodyParser= require('body-parser');
const User = require('../models').User;
const { raw } = require('body-parser');


router.post('/',(req,res)=>{
      User.create({
            user_id: req.body.id,
            user_name: req.body.name,
            user_password: req.body.password,
            user_major: req.body.major,
            user_email: req.body.email,
            user_phoneNumber : req.body.phonenumber,
            user_schoolId: req.body.schoolid
      })
      console.log("create ID");
})

router.post('/IDcheck',async(req,res)=>{
      var arr= await User.findAll({
            attributes:['ID'],
            where:{
                  user_id:req.body.id,
            },
            raw:true
      })

      if(arr.length==0){
            var result={"res":"OK"};
            res.send(result);
      }
      else{
            var result={"res":"Retry"};
            res.send(result)
      }
})

router.post('/login',async(req,res)=>{

      var approve ={'user_id':'OK','user_password':'OK'};


    var paramId = req.body.id;
    var paramPassword = req.body.password;
    console.log('id : '+paramId+'  pw : '+paramPassword);

    //아이디 일치여부 flag json 데이터입니다.
//     if(paramId == 'lewis1998') approve.approve_id = 'OK';
//     if(paramPassword == 'park6766387') approve.approve_pw = 'OK';

//     res.send(approve);
      

      var arr = await User.findAll({
            // attributes:['user_id', 'user_password','user_email','user_name'],
            where:{
                  user_id : paramId,
                  user_password : paramPassword,
            },
            raw:true,
      
      })
      console.log("1");
      console.log(arr);
      var result={'user_id':arr[0].user_id, 'user_name':arr[0].user_name, 'user_password':arr[0].user_password, 'user_email':arr[0].user_email, 
            'user_major':arr[0].user_major, 'user_phoneNumber':arr[0].user_phoneNumber, 'user_schoolId':arr[0].user_schoolId};
      console.log(result);
       res.send(result);
})

module.exports=router;