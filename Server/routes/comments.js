const express = require('express');
const multer = require('multer');
const router = express.Router();
var bodyParser= require('body-parser');
const Comment = require('../models').Comment;
const { raw } = require('body-parser');



router.post('/',async(req,res)=>{

      var parampostnum = req.body.post_number;

      var arr = await Comment.findAll({
            attributes:['real_comment','user_name','post_number','user_id'],
            where:{
                  post_number:parampostnum,
            },
            raw:true
      })
      var result ={'comments':arr}
      console.log(result);
      res.send(result);
})

router.post('/comment', async(req,res)=>{
      var paramName = req.body.user_name;
      var paramuserID=req.body.user_id;
      var paramComment = req.body.comment;
      var parampostNum = req.body.post_number;


      var arr = await Comment.create({
            post_number:parampostNum,
            user_id:paramuserID,
            real_comment:paramComment,
            user_name:paramName,
      })

      var arr = await Comment.findAll({
            attributes:['real_comment','user_name','post_number','user_id'],
            where:{
                  post_number:parampostNum,
            },
            raw:true
      })
      var result ={'comments':arr}
      res.send(result);


})

module.exports =router;