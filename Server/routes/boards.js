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

router.get('/',async (req,res)=>{
      var arr = await Board.findAll({
            attributes:['id','title','contents','date','view','comment_number','recommends','user_id','board_code','updateAt'],
           raw:true,
      })
      var result ={'articles':arr}

      console.log(arr);
      console.log(result);

      res.send(result);

})


module.exports=router;