const express = require('express');
const router = express.Router();
const { addMembership, addLostAndFound, addCalagapay, addAgapay, addESSCential } = require('../controllers/adminController');

router.post('/membership', addMembership);
router.post('/lostandfound', addLostAndFound);
router.post('/calagapay', addCalagapay);
router.post('/projectagapay', addAgapay);
router.post('/esscential', addESSCential);

module.exports = router;
