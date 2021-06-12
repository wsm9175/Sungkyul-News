const express = require('express');
const multer = require('multer');
const router = express.Router();
var bodyParser= require('body-parser');
const Board = require('../models').Board;
const Recommendation = require('../models').Recommendation;

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
            attributes:['id','title','contents','date','view','comment_number','recommends','user_id','board_code','updatedAt'],
           raw:true,
      })
      var result ={'articles':arr}

      console.log(arr);
      console.log(result);
      res.send(result);

})

router.post('/recommendation',async (req,res)=>{
      console.log(req.body);
      var arr = await Recommendation.findAll({
            where:{
                  post_number : req.body.post_number,
                  user_id: req.body.user_id,   
            },
           raw:true,
      })
      if(arr.length != 0){
            var result = {'response':'NO'};
            res.send(result);
            console.log("recommendation : false");
      }else{
            var result = {'response':'YES'};
            res.send(result);
            console.log("recommendation : true");
      }
})

router.post('/recommendation/insert',async (req,res)=>{
      console.log(req.body);
      Board.update({
            recommends: req.body.recommendation,
      }, {
            where: {post_number: req.body.post_number },
      });
      
      Recommendation.create({
            post_number : req.body.post_number,
            user_id: req.body.user_id,    
      });
      var result = {'response':'OK'}

      console.log("삽입 성공");
      res.send(result);
})
module.exports=router;