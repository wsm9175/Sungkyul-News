const express = require('express');
const multer = require('multer');
const path = require('path');
const router = express.Router();
var bodyParser= require('body-parser');
const User = require('../models').User;


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

module.exports=router;