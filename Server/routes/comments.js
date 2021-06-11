const express = require('express');
const multer = require('multer');
const router = express.Router();
var bodyParser= require('body-parser');
const Comment = require('../models').Comment;
const { raw } = require('body-parser');



router.post('/',async(req,res)=>{

      var parampostnum = req.body.post_number;

      var arr = await Comment.findAll({
            attributes:['real_comment','user_name'],
            where:{
                  post_number:parampostnum,
            },
            raw:true
      })
      res.send(arr);
})

router.post('/comment', async(req,res)=>{
      var paramName = req.body.user_name;
      var paramuserID=req.body.user_id;
      var paramComment = req.body.comment;
      var parampostNum = req.body.post_number;

      var approve ={'response':'OK'};

      var arr = await Comment.create({
            post_number:parampostNum,
            user_id:paramuserID,
            real_comment:paramComment,
            user_name:paramName,
      })

      res.send(approve);

})

module.exports =router;