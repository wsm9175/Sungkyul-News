const express = require('express');
const router = express.Router();

router.use('/users',require("./users"));
router.use('/boards',require("./boards"));
router.use('/comments',require("./comments"));
router.use('/mail',require("./auth"));

module.exports=router;