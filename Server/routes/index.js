const express = require('express');
const router = express.Router();

router.use('/users',require("./users"));
router.use('/boards',require("./boards"));
router.use('/comments',require("./commets"));

module.exports=router;