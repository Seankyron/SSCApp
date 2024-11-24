const mongoose = require('mongoose');

const AnnouncementSchema = new mongoose.Schema({
  tag: { type: String, required: true },
  eventHeader: { type: String, required: true },
  eventDetails: { type: String, required: true },
  uploader: { type: String, required: true },
});

module.exports = mongoose.model('Announcement', AnnouncementSchema);
