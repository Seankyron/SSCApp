const express = require('express');
const router = express.Router();
const { addMembership, addLostAndFound, addCalagapay, addAgapay, addESSCential, getAgapayEntries } = require('../controllers/adminController');

// Route definitions
router.post('/membership', addMembership);
router.post('/lostandfound', addLostAndFound);
router.post('/calagapay', addCalagapay);
router.post('/projectagapay', addAgapay);
router.post('/esscential', addESSCential);

router.get('/projectagapay', getAgapayEntries);
module.exports = router;
