const express = require("express");
const morgan = require("morgan");
const cookieParser = require('cookie-parser');
const session  = require('express-session');
const dotenv = require('dotenv');
const path = require('path');
const nunjucks = require('nunjucks');
const multer = require('multer');
var bodyParser= require('body-parser');
var http = require('http');
const indexrouter = require("./routes/index");
const models=require("./models");


dotenv.config();
const app = express(); //서버 실행
app.use(session({ secret: 'somevalue' }));
app.set('port', process.env.PORT || 3000);
app.use(morgan('dev')); //서버로 들어온 요청과 응답 기록

app.use('/', express.static(path.join(__dirname, 'public')));
app.use(express.json());
app.use(express.urlencoded({extended:false}));
app.use(cookieParser(process.env.COOKIE_SECRET));
app.use(session({
      resave:false,
      saveUninitialized: false,
      secret: process.env.COOKIE_SECRET,
      cookie:{
            httpOnly:true,
            secure:false,
      },
      name:'sesion-cookie'
}));
app.listen(app.get('port'),()=>{
      console.log(app.get('port'),'번포트에서 대기 중');
})

models.sequelize.sync().then( () => {
      console.log(" DB 연결 성공");
    }).catch(err => {
      console.log("연결 실패");
      console.log(err);
    });

app.use('/index', indexrouter);





app.get('/', (req, res, next) => {
      console.log('안드로이드 연결 완료');
      next();
}, (req, res) => {
      throw new Error('에러는 에러 처리 미들웨어로 갑니다.')
});



app.use((err, req, res, next) => {
      console.error(err);
      res.status(500).send(err.message);
});

