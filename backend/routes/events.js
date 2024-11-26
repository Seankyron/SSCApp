const express = require('express');
const router = express.Router();
const { addAnnouncement, addEvent } = require('../controllers/eventController');

// Route definitions
router.post('/announcements', addAnnouncement);
router.post('/events', addEvent);

module.exports = router;
