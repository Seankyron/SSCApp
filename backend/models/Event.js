const mongoose = require('mongoose');

const eventSchema = new mongoose.Schema({
  eventHeader: {
    type: String,
    required: true,
  },
  eventDetails: {
    type: String,
    required: true,
  },
  eventDate: {
    type: Date,
    required: true,
  },
  location: {
    type: String,
    required: true,
  },
  uploader: {
    type: String,
    required: true,
  },
}, {
  timestamps: true, // Automatically adds createdAt and updatedAt fields
});

const Event = mongoose.model('Event', eventSchema);

module.exports = Event;
