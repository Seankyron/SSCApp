const Announcement = require('../models/Announcement');
const Event = require('../models/Event');

exports.addAnnouncement = async (req, res) => {
  const { tag, eventHeader, eventDetails, uploader } = req.body;
  try {
    const announcement = new Announcement({ tag, eventHeader, eventDetails, uploader });
    await announcement.save();
    res.status(201).json({ message: 'Announcement added successfully' });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

exports.addEvent = async (req, res) => {
  const { eventHeader, eventDetails, eventDate, location, uploader } = req.body;

  // Validate that all fields are provided
  if (!eventHeader || !eventDetails || !eventDate || !location || !uploader) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    // Create a new Event document
    const newEvent = new Event({
      eventHeader,
      eventDetails,
      eventDate,
      location,
      uploader,
    });

    // Save the event to the database
    await newEvent.save();

    // Respond with the saved event
    res.status(201).json({
      message: 'Event added successfully',
      event: newEvent,
    });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};