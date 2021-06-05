const express = require('express');
const multer = require('multer');
const router = express.Router();
var bodyParser= require('body-parser');
const Board = require('../models').Board;


router.post('/',(req,res)=>{
      Board.create({
            title: req.body.title,
            contents: req.body.contents,
            date : req.body.date,
            user_id: req.body.user_id,
            board_code:req.body.board_code            
      });
      var result = {'response':'OK'}

      console.log("삽입 성공");
      res.send(result);
})


module.exports=router;